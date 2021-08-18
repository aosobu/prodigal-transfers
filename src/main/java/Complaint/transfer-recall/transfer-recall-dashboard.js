app.controller('TransferRecallDashboardController', ['$scope', '$sce', '$rootScope', '$stateParams', 'RootService', 'TransferRecallDashboardService', '$filter', '$timeout', 'PNotifyService', 'TRANSFER_RECALL_SERVICE_HOST', 'AuthServerProvider',

    function ($scope, $sce, $rootScope, $stateParams, RootService, TransferRecallDashboardService, $filter, $timeout, PNotifyService, TRANSFER_RECALL_SERVICE_HOST, AuthServerProvider) {

        $rootScope.PageTitle = "Dashboard";

        RootService.decorate($scope);

        $scope.baseExportUrl = TRANSFER_RECALL_SERVICE_HOST;

        $scope.trSuccess = false;
        $scope.trSuccessMessage = "";
        $scope.trError = false;
        $scope.trErrorMessage = "";
        $scope.trLoadCounter = 0;

        $scope.selectedUserBranch = "all";
        $scope.selectedAssigneePersonnel = {};
        $scope.branchStatRange = "";
        $scope.currentUser = [];
        $scope.userBranches = [];
        $scope.currentBranch = [];

        $scope.branchCodesForTable = [];

        $scope.isBranchLoggedComplaintsLoading = true;
        $scope.branchLoggedComplaints = [];
        $scope.branchLoggedComplaintsTableState = [];

        $scope.groupInfoComplaintStatistics = [];
        $scope.currentBranchUsers = [];

        //variables for personal info statistics
        $scope.personalUnassignedInterComplaints = 0;
        $scope.personalBranchHistoryComplaints = 0;
        $scope.personalInProcessingComplaints = 0;
        $scope.personalResolvedComplaints = 0;
        $scope.personalTotalComplaints = 0;
        $scope.personalUnassignedIntraComplaints = 0;

        //variables for personal info statistics
        $scope.groupUnassignedInterComplaints = 0;
        $scope.groupBranchHistoryComplaints = 0;
        $scope.groupInProcessingComplaints = 0;
        $scope.groupResolvedComplaints = 0;
        $scope.groupTotalComplaints = 0;
        $scope.groupUnassignedIntraComplaints = 0;

        $scope.userInfoComplaintStatistics = [];
        $scope.branchesComplaintStatistics = [];


        $scope.multipleBranches = false;
        $scope.noBranches = false;

        $scope.currentTreatComplaint = [];
        $scope.deskMode = false;
        $scope.complaintResolutionReasons = [];

        //data structure for selected ids
        $scope.interRecallIds = {};
        $scope.intraRecallIds = {};


        //show progress modal
        $scope.showProgress = function (action, message) {
            $scope.progressMessage = message;
            if (action) {
                $('#progressModal').modal('show');
            } else {
                $('#progressModal').modal('hide');
            }
        };

        $scope.init = function () {

            $scope.selectedUserBranch = "all";
            $scope.selectedAssigneePersonnel.selected = undefined;

            $scope.branchStatRange = $filter('date')(new Date(moment().day(-7)), "yyyy-MM-dd") + " : " + $filter('date')(new Date(), "yyyy-MM-dd");

            $scope.showProgress(true, "Initializing");
            $scope.trLoadCounter = 1;

            $scope.getUserDetails();
            $scope.fetchUserBranches();
        }; // end of init function

        $scope.getUserDetails = function () {
            if ($scope.trLoadCounter <= 0) {
                $scope.showProgress(true, "Fetching User Details.");
            }
            TransferRecallDashboardService.getUserDetails(
                function (data, status) {
                    if (status == 200 || status == 201) {

                        // Date range
                        $('#dateRangeBranchStats').daterangepicker({
                            ranges: {
                                'Today': [moment(), moment()],
                                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                                'This Month': [moment().startOf('month'), moment().endOf('month')],
                                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                            }
                        });

                        // SlimScroll
                        $('.conversation-inner').slimScroll({
                            height: '352px',
                            alwaysVisible: false,
                            railVisible: true,
                            wheelStep: 10,
                            allowPageScroll: false,
                            start: 'bottom'
                        });

                        $scope.currentUser = data;

                        if ($scope.currentUser != undefined || $scope.currentUser != null) {
                            $scope.trLoadCounter += 1;

                            var formData = new FormData();

                            if($scope.isAdminOrAuthorizer()){

                                formData.append("staffId", $scope.currentUser.staffId);
                                formData.append("roleType", "admin");
                                $scope.fetchPersonalInfoComplaintStatisticsData(formData);

                            }else if($scope.isInputter() || $scope.isViewer()){

                                formData.append("staffId", $scope.currentUser.staffId);
                                formData.append("roleType", "non-admin");
                                $scope.fetchPersonalInfoComplaintStatisticsData(formData);

                            }
                        }else{
                            PNotifyService.error("Failed to get user details.");
                        }
                    } else {
                        PNotifyService.error("Failed to get user details.");
                    }
                    if ($scope.trLoadCounter <= 0) {
                        $scope.showProgress(false);
                    }
                    $scope.trLoadCounter--;
                },
                function (data, status) {
                    PNotifyService.error(data.description);
                    if ($scope.trLoadCounter <= 0) {
                        $scope.showProgress(false);
                    }
                    $scope.trLoadCounter--;
                }
            );
        }; // end of get user details

        // Get User's Statistical Data
        $scope.fetchPersonalInfoComplaintStatisticsData = function (formData) {
            if ($scope.trLoadCounter <= 0) {
                $scope.showProgress(true, "Fetching Personal Complaint Statistics...");
            }

            TransferRecallDashboardService.getPersonalInfoComplaintStatisticsData(
                formData,
                function (data, status) {
                    if (status == 200 || status == 201) {
                        $scope.userInfoComplaintStatistics = data;
                        $scope.setPersonalStatisticsVariables($scope.userInfoComplaintStatistics);
                    } else {
                        $scope.trError = true;
                        $scope.trErrorMessage = "Failed to get personal complaint stats.";
                    }
                    if ($scope.trLoadCounter <= 0) { // Initialization has finished
                        $scope.showProgress(false);
                    }
                    $scope.trLoadCounter--;
                },
                function (data, status) {
                    $scope.trError = true;
                    PNotifyService.error(data.description);
                    if ($scope.trLoadCounter <= 0) { // Initialization has finished
                        $scope.showProgress(false);
                    }
                    $scope.trLoadCounter--;
                }
            );
        };


        /*****1
         * Role Checker Section
         * @returns {*|boolean}
         */
        $scope.isAdminOrAuthorizer = function () {
            return AuthServerProvider.isInRole('as_tr_resolve_complaint') && AuthServerProvider.isInRole('as_tr_approve_complaint');
        };
        $scope.isInputter = function () {
            return AuthServerProvider.isInRole('as_tr_log_complaint');
        };
        $scope.isViewer = function () {
            return AuthServerProvider.isInRole('as_tr_view_all');
        };
        $scope.isBranchAdmin = function () {
            return AuthServerProvider.isInRole('as_tr_branch_admin');
        };


        /**
         * set statistical information
         * @param stats
         */
        $scope.setPersonalStatisticsVariables = function (stats) {
            $scope.personalUnassignedInterComplaints = stats.unassignedInterComplaints;
            $scope.personalBranchHistoryComplaints = stats.branchHistoryComplaints;
            $scope.personalInProcessingComplaints = stats.inProcessingComplaints;
            $scope.personalResolvedComplaints = stats.resolvedComplaints;
            $scope.personalTotalComplaints = stats.totalComplaints;
            $scope.personalUnassignedIntraComplaints = stats.unassignedIntraComplaints;
        };

        $scope.setGroupStatisticsVariables = function (stats) {
            $scope.groupUnassignedInterComplaints = stats.unassignedInterComplaints;
            $scope.groupBranchHistoryComplaints = stats.branchHistoryComplaints;
            $scope.groupInProcessingComplaints = stats.inProcessingComplaints;
            $scope.groupResolvedComplaints = stats.resolvedComplaints;
            $scope.groupTotalComplaints = stats.totalComplaints;
            $scope.groupUnassignedIntraComplaints = stats.unassignedIntraComplaints;
        };


        /**
         * get group info
         */
        // Get User's Branches
        $scope.fetchUserBranches = function () {
            if ($scope.trLoadCounter <= 0) { // Initialization has finished
                $scope.showProgress(true, "Fetching user branches");
            }
            TransferRecallDashboardService.getUserBranches(
                function (data, status) {
                    if (status == 200 || status == 201) {
                        $scope.userBranches = data;

                        $scope.initializeWithBranchData($scope.userBranches, false);
                    } else {
                        PNotifyService.error("Failed to get branches.");
                    }
                    if ($scope.trLoadCounter <= 0) { // Initialization has finished
                        $scope.showProgress(false);
                    }
                    $scope.trLoadCounter--;
                },
                function (data, status) {
                    PNotifyService.error(data.description);
                    if ($scope.trLoadCounter <= 0) { // Initialization has finished
                        $scope.showProgress(false);
                    }
                    $scope.trLoadCounter--;
                }
            );
        };

        // Initialize dashboard data using branches
        $scope.initializeWithBranchData = function (userBranches, showInitModal) {

            if (showInitModal) {
                $scope.showProgress(true, "Initializing");
            }

            var branchCodes = [];
            if (userBranches.length == 1) {
                // Get single branch details
                $scope.multipleBranches = false;
                $scope.noBranches = false;
                $scope.currentBranch = userBranches[0];
                $rootScope.PageTitle = "Dashboard - " + $scope.currentBranch.branchName + " (" + $scope.currentBranch.branchCode + ")";

                branchCodes = [];
                $scope.branchCodesForTable = [];
                branchCodes.push($scope.currentBranch.branchCode);
                $scope.branchCodesForTable.push($scope.currentBranch.branchCode);

                //get Group Infor statistics for Admin, Inputter or branch admin
                $scope.fetchGroupInfoComplaintStatisticsData(userBranches);

                //get complaint data for Admin, Inputter or Branch Admin
                $scope.getComplaints();

            } else if (userBranches.length > 1) {
                // Get multiple branch details
                $scope.multipleBranches = true;
                $scope.noBranches = false;
                $rootScope.PageTitle = "Dashboard";
                $scope.trLoadCounter += 3;

                branchCodes = [];
                $scope.branchCodesForTable = [];
                userBranches.forEach(function (branch) {
                    branchCodes.push(branch.branchCode);
                    $scope.branchCodesForTable.push(branch.branchCode);
                });

                //get Group Infor statistics for Admin, Inputter or branch admin
                $scope.fetchGroupInfoComplaintStatisticsData(userBranches);

                //get complaint data for Admin, Inputter or Branch Admin
                $scope.getComplaints();

            } else {
                // No branches for that user
                $scope.multipleBranches = false;
                $scope.noBranches = true;
                $rootScope.PageTitle = "Dashboard";
            }
        }; //end of initialize dashboard using branch data


        // Get User's Branch's Statistical Data for Assigned Complaints (Info Dashboard Only)
        $scope.fetchGroupInfoComplaintStatisticsData = function (userBranches) {
            if ($scope.trLoadCounter <= 0) {
                $scope.showProgress(true, "Fetching Branches Complaint Statistics...");
            }

            var formData = new FormData();

            if($scope.isAdminOrAuthorizer()){

                formData.append("role", "admin");
                formData.append("branches", userBranches)
            }else if($scope.isInputter() || $scope.isViewer()){

                formData.append("role", "non-admin");
                formData.append("branches", angular.toJson(userBranches))
            }else if($scope.isBranchAdmin()){

                console.log("To be worked on later ::");
                // formData.append("role", "branch-admin");
                // formData.append("branches", userBranches)
            }

            TransferRecallDashboardService.getGroupInfoComplaintStatisticsData(
                formData,
                function (data, status) {
                    if (status == 200 || status == 201) {
                        $scope.groupInfoComplaintStatistics = data;
                        $scope.setGroupStatisticsVariables($scope.groupInfoComplaintStatistics);
                    } else {
                        $scope.trError = true;
                        $scope.trErrorMessage = "Failed to get branches complaint stats.";
                    }
                    if ($scope.trLoadCounter <= 0) { // Initialization has finished
                        $scope.showProgress(false);
                    }
                    $scope.trLoadCounter--;
                },
                function (data, status) {
                    $scope.trError = true;
                    PNotifyService.error(data.description);
                    if ($scope.trLoadCounter <= 0) { // Initialization has finished
                        $scope.showProgress(false);
                    }
                    $scope.trLoadCounter--;
                }
            );
        };

        //Retrieve Complaints
        $scope.getComplaints = function(){

            //check if it is authorizer or admin
            if($scope.isAdminOrAuthorizer()){

                $scope.getInterRecallComplaints();
                $scope.getIntraRecallComplaints();
                $scope.getProcessingRecallComplaints();
                $scope.getResolvedRecallComplaints();
                $scope.getDeclinedRecallComplaints();

            }else if($scope.isInputter() || $scope.isViewer()){

                $scope.getBranchRecallComplaints();
                $scope.getPersonalLoggedComplaints();

            }else if($scope.isBranchAdmin()){

                $scope.getBranchRecallComplaints();
            }

        }; //end of retrieve complaints

        // Inter Recall Complaints
        $scope.isInterRecallComplaintsLoading = true;
        $scope.interRecallComplaints = [];
        $scope.interRecallComplaintsTableState = [];
        $scope.getInterRecallComplaints = function(tableState){
                if (typeof tableState.pagination == "undefined") {
                    tableState.pagination = [];
                    tableState.pagination.start = 0;
                    tableState.pagination.number = 15;
                    tableState.search = [];
                }

                $scope.interRecallComplaintsTableState = tableState;

                var start = tableState.pagination.start || 0;
                var length = tableState.pagination.number || 15;
                var search = $.isEmptyObject(tableState.search) ? null : tableState.search;
                var searchArray = [];
                var role = null;
                var recallType = "inter";
                var processingState = 0;
                var branches = [];
                branches.push($scope.currentUser.branchCode);

                if (search) {
                    search = search.predicateObject;
                    $.each(search, function (key, value) {
                        searchArray.push({columnName: key, value: value});
                    });
                }

                var request = {
                    start: start,
                    length: length,
                    sort: $.isEmptyObject(tableState.sort) ? null : tableState.sort,
                    search: searchArray,
                    branches: branches,
                    role: role,
                    recallType: recallType,
                    processingState: processingState
                };

                TransferRecallDashboardService.fetchAdminOrAuthorizerComplaints(
                    function (data) {
                        $scope.interRecallComplaints = data.data;
                        tableState.pagination.numberOfPages = data.totalPages;
                        $scope.isInterRecallComplaintsLoading  = false;
                    },
                    function (data) {
                    },
                    request
                );
        } //end of get inter-recall complaints

        // Intra Recall Complaints
        $scope.isIntraRecallComplaintsLoading = true;
        $scope.intraRecallComplaints = [];
        $scope.intraRecallComplaintsTableState = [];
        $scope.getIntraRecallComplaints = function(tableState){
            if (typeof tableState.pagination == "undefined") {
                tableState.pagination = [];
                tableState.pagination.start = 0;
                tableState.pagination.number = 15;
                tableState.search = [];
            }

            $scope.intraRecallComplaintsTableState = tableState;

            var start = tableState.pagination.start || 0;
            var length = tableState.pagination.number || 15;
            var search = $.isEmptyObject(tableState.search) ? null : tableState.search;
            var searchArray = [];
            var role = null;
            var recallType = "intra";
            var processingState = 0;
            var branches = [];
            branches.push($scope.currentUser.branchCode);

            if (search) {
                search = search.predicateObject;
                $.each(search, function (key, value) {
                    searchArray.push({columnName: key, value: value});
                });
            }

            var request = {
                start: start,
                length: length,
                sort: $.isEmptyObject(tableState.sort) ? null : tableState.sort,
                search: searchArray,
                branches: branches,
                role: role,
                recallType: recallType,
                processingState: processingState
            };

            TransferRecallDashboardService.fetchAdminOrAuthorizerComplaints(
                function (data) {
                    $scope.intraRecallComplaints = data.data;
                    tableState.pagination.numberOfPages = data.totalPages;
                    $scope.isIntraRecallComplaintsLoading  = false;
                },
                function (data) {
                },
                request
            );
        } //end of get intra recall complaints

        // Processing Recall Complaints
        $scope.processingRecallComplaintsLoading = true;
        $scope.processingRecallComplaints = [];
        $scope.processingRecallComplaintsTableState = [];
        $scope.getProcessingRecallComplaints = function(tableState){
            if (typeof tableState.pagination == "undefined") {
                tableState.pagination = [];
                tableState.pagination.start = 0;
                tableState.pagination.number = 15;
                tableState.search = [];
            }

            $scope.processingRecallComplaintsTableState = tableState;

            var start = tableState.pagination.start || 0;
            var length = tableState.pagination.number || 15;
            var search = $.isEmptyObject(tableState.search) ? null : tableState.search;
            var searchArray = [];
            var role = null;
            var recallType = null;
            var processingState = 1;
            var branches = [];
            branches.push($scope.currentUser.branchCode);

            if (search) {
                search = search.predicateObject;
                $.each(search, function (key, value) {
                    searchArray.push({columnName: key, value: value});
                });
            }

            var request = {
                start: start,
                length: length,
                sort: $.isEmptyObject(tableState.sort) ? null : tableState.sort,
                search: searchArray,
                branches: branches,
                role: role,
                recallType: recallType,
                processingState: processingState
            };

            TransferRecallDashboardService.fetchAdminOrAuthorizerComplaints(
                function (data) {
                    $scope.processingRecallComplaints = data.data;
                    tableState.pagination.numberOfPages = data.totalPages;
                    $scope.processingRecallComplaintsLoading  = false;
                },
                function (data) {
                },
                request
            );
        } //end of get processing recall complaints

        // Resolved Recall Complaints
        $scope.resolvedRecallComplaintsLoading = true;
        $scope.resolvedRecallComplaints = [];
        $scope.resolvedRecallComplaintsTableState = [];
        $scope.getResolvedRecallComplaints = function(tableState){
            if (typeof tableState.pagination == "undefined") {
                tableState.pagination = [];
                tableState.pagination.start = 0;
                tableState.pagination.number = 15;
                tableState.search = [];
            }

            $scope.resolvedRecallComplaintsTableState = tableState;

            var start = tableState.pagination.start || 0;
            var length = tableState.pagination.number || 15;
            var search = $.isEmptyObject(tableState.search) ? null : tableState.search;
            var searchArray = [];
            var role = null;
            var recallType = null;
            var processingState = 2;
            var branches = [];
            branches.push($scope.currentUser.branchCode);

            if (search) {
                search = search.predicateObject;
                $.each(search, function (key, value) {
                    searchArray.push({columnName: key, value: value});
                });
            }

            var request = {
                start: start,
                length: length,
                sort: $.isEmptyObject(tableState.sort) ? null : tableState.sort,
                search: searchArray,
                branches: branches,
                role: role,
                recallType: recallType,
                processingState: processingState
            };

            TransferRecallDashboardService.fetchAdminOrAuthorizerComplaints(
                function (data) {
                    $scope.resolvedRecallComplaints = data.data;
                    tableState.pagination.numberOfPages = data.totalPages;
                    $scope.resolvedRecallComplaintsLoading  = false;
                },
                function (data) {
                },
                request
            );
        } //end of get resolved recall complaints

        // Resolved Recall Complaints
        $scope.declinedRecallComplaintsLoading = true;
        $scope.declinedRecallComplaints = [];
        $scope.declinedRecallComplaintsTableState = [];
        $scope.getDeclinedRecallComplaints = function(tableState){
            if (typeof tableState.pagination == "undefined") {
                tableState.pagination = [];
                tableState.pagination.start = 0;
                tableState.pagination.number = 15;
                tableState.search = [];
            }

            $scope.declinedRecallComplaintsTableState = tableState;

            var start = tableState.pagination.start || 0;
            var length = tableState.pagination.number || 15;
            var search = $.isEmptyObject(tableState.search) ? null : tableState.search;
            var searchArray = [];
            var role = null;
            var recallType = null;
            var processingState = 2;
            var branches = [];
            branches.push($scope.currentUser.branchCode);

            if (search) {
                search = search.predicateObject;
                $.each(search, function (key, value) {
                    searchArray.push({columnName: key, value: value});
                });
            }

            var request = {
                start: start,
                length: length,
                sort: $.isEmptyObject(tableState.sort) ? null : tableState.sort,
                search: searchArray,
                branches: branches,
                role: role,
                recallType: recallType,
                processingState: processingState
            };

            TransferRecallDashboardService.fetchAdminOrAuthorizerComplaints(
                function (data) {
                    $scope.declinedRecallComplaints = data.data;
                    tableState.pagination.numberOfPages = data.totalPages;
                    $scope.declinedRecallComplaintsLoading  = false;
                },
                function (data) {
                },
                request
            );
        } //end of get resolved recall complaints

        // Branch Recall Complaints
        $scope.isBranchRecallComplaintsLoading = true;
        $scope.branchRecallComplaints = [];
        $scope.branchRecallComplaintsTableState = [];
        $scope.getBranchRecallComplaints = function(tableState){
            if (typeof tableState.pagination == "undefined") {
                tableState.pagination = [];
                tableState.pagination.start = 0;
                tableState.pagination.number = 15;
                tableState.search = [];
            }

            $scope.branchRecallComplaintsTableState = tableState;

            var start = tableState.pagination.start || 0;
            var length = tableState.pagination.number || 15;
            var search = $.isEmptyObject(tableState.search) ? null : tableState.search;
            var searchArray = [];
            var role = null;
            var recallType = null;
            var processingState = 0;
            var staffId = null;
            var branches = [];
            branches.push($scope.currentUser.branchCode);

            if (search) {
                search = search.predicateObject;
                $.each(search, function (key, value) {
                    searchArray.push({columnName: key, value: value});
                });
            }

            var request = {
                start: start,
                length: length,
                sort: $.isEmptyObject(tableState.sort) ? null : tableState.sort,
                search: searchArray,
                branches: branches,
                role: role,
                recallType: recallType,
                processingState: processingState,
                staffId: staffId
            };

            TransferRecallDashboardService.fetchBranchLoggedComplaints(
                function (data) {
                    $scope.branchRecallComplaints = data.data;
                    tableState.pagination.numberOfPages = data.totalPages;
                    $scope.isBranchRecallComplaintsLoading = false;
                },
                function (data) {
                },
                request
            );
        } //end of get branch recall complaints

        // Personal Complaints
        $scope.isPersonalRecallComplaintsLoading = true;
        $scope.personalRecallComplaints = [];
        $scope.personalRecallComplaintsTableState = [];
        $scope.getPersonalLoggedComplaints = function(tableState){
            if (typeof tableState.pagination == "undefined") {
                tableState.pagination = [];
                tableState.pagination.start = 0;
                tableState.pagination.number = 15;
                tableState.search = [];
            }

            $scope.personalRecallComplaintsTableState = tableState;

            var start = tableState.pagination.start || 0;
            var length = tableState.pagination.number || 15;
            var search = $.isEmptyObject(tableState.search) ? null : tableState.search;
            var searchArray = [];
            var role = null;
            var recallType = null;
            var processingState = 0;
            var staffId = $scope.currentUser.staffId;
            var branches = [];

            if (search) {
                search = search.predicateObject;
                $.each(search, function (key, value) {
                    searchArray.push({columnName: key, value: value});
                });
            }

            var request = {
                start: start,
                length: length,
                sort: $.isEmptyObject(tableState.sort) ? null : tableState.sort,
                search: searchArray,
                branches: branches,
                role: role,
                recallType: recallType,
                processingState: processingState,
                staffId: staffId
            };

            //var jsonRequest = angular.toJson(request);

            TransferRecallDashboardService.fetchBranchLoggedComplaints(
                function (data) {
                    $scope.personalRecallComplaints = data.data;
                    tableState.pagination.numberOfPages = data.totalPages;
                    $scope.isPersonalRecallComplaintsLoading = false;
                },
                function (data) {
                },
                request
            );
        } //end of get personal recall complaints


        /*
        * SHOW COMPLAINT INFO
        * */
        $scope.openFrom = "";
        $scope.showComplaintInfo = function (complaint, openFrom) {
            $scope.currentTreatComplaint = complaint;
            $scope.openFrom = openFrom;

            if (openFrom != "none") {
                $('#' + openFrom).modal('show');
            }
        };

        $scope.dismiss = function () {
            $scope.openFrom = "";
            $('#' + $scope.openFrom).modal('hide');
        };
        /* END SHOW COMPLAINT INFO */

    }
]);

app.service('TransferRecallDashboardService', ['$http', '$stateParams', 'APIServiceHandler', 'TRANSFER_RECALL_SERVICE_HOST',

    function ($http, $stateParams, APIServiceHandler, TRANSFER_RECALL_SERVICE_HOST) {

        this.getUserDetails = function (successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.get(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/user-details"), successHandler, errorHandler);
        };
        this.getUserBranches = function (successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.get(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/user-branches"), successHandler, errorHandler);
        };
        this.getBranchUsers = function (data, successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.post(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/branch-users", data), successHandler, errorHandler);
        };
        this.getBranchesComplaintsStatistics = function (data, successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.post(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/branches-complaints-stats", data), successHandler, errorHandler);
        };
        this.fetchBranchLoggedComplaints = function (successHandler, errorHandler, data) {
            APIServiceHandler.handleAPICall($http.post(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/branch-logged-complaints", data), successHandler, errorHandler);
        };
        this.fetchAdminOrAuthorizerComplaints = function (successHandler, errorHandler, data) {
            APIServiceHandler.handleAPICall($http.post(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/complaints-admin", data), successHandler, errorHandler);
        };

        this.getPersonalInfoComplaintStatisticsData = function (data, successHandler, errorHandler) {

            var httpVar = $http({
                method: 'POST',
                url: TRANSFER_RECALL_SERVICE_HOST + "/api/v1/user-info-complaint-stats",
                data: data,
                headers: {'Content-Type': undefined}
            });

            APIServiceHandler.handleAPICall(httpVar, successHandler, errorHandler);
        };

        this.getGroupInfoComplaintStatisticsData = function (data, successHandler, errorHandler) {

            var httpVar = $http({
                method: 'POST',
                url: TRANSFER_RECALL_SERVICE_HOST + "/api/v1/group-info-complaint-stats",
                data: data,
                headers: {'Content-Type': undefined}
            });

            APIServiceHandler.handleAPICall(httpVar, successHandler, errorHandler);

        };

    }
]);

app.filter('encodeBase64', function(){
    return function(text){
        return btoa(text);
    }
});