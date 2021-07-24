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

        $scope.groupUnassignedIntraComplaints = 0;
        $scope.groupAssignedIntraComplaints = 0;
        $scope.groupUnassignedInterComplaints = 0;
        $scope.groupAssignedInterComplaints = 0;
        $scope.groupProcessingInterComplaints = 0;
        $scope.groupProcessingIntraComplaints = 0;
        $scope.groupBranchComplaintsHistory = 0;
        $scope.groupResolvedCount = 0;

        $scope.personalUnassignedIntraComplaints = 0;
        $scope.personalAssignedIntraComplaints = 0;
        $scope.personalUnassignedInterComplaints = 0;
        $scope.personalAssignedInterComplaints = 0;
        $scope.personalProcessingInterComplaints = 0;
        $scope.personalProcessingIntraComplaints = 0;
        $scope.personalBranchComplaintsHistory = 0;
        $scope.personalResolvedCount = 0;

        $scope.userInfoComplaintStatistics = [];
        $scope.branchesComplaintStatistics = [];

        $scope.branchesComplaintsTotal = 0;
        $scope.branchesComplaintsTotalResolvedInter = 0;
        $scope.branchesComplaintsTotalResolvedIntra = 0;
        $scope.branchesComplaintsTotalUnassignedInter = 0;
        $scope.branchesComplaintsTotalUnassignedIntra = 0;
        $scope.branchesComplaintsTotalPendingInter = 0;
        $scope.branchesComplaintsTotalPendingIntra = 0;

        $scope.branchesComplaintsBarChart = {};
        $scope.branchesComplaintsBarChart.data = [[0, 0, 0, 0, 0]];
        $scope.branchesComplaintsBarChart.colours = [{fillColor: ['#46BFBD', '#F1C40F', '#F7464A', '#2ECC71', '#292929']}];
        $scope.branchesComplaintsBarChart.series = ['Series A'];
        $scope.branchesComplaintsBarChart.labels = ['Pending', 'Approaching TAT', 'Passed TAT', 'Closed Within TAT', 'Closed Passed TAT'];
        $scope.branchesComplaintsBarChart.legend = false;
        $scope.branchesComplaintsBarChart.options = {
            animation: true
        };

        $scope.multipleBranches = false;
        $scope.noBranches = false;

        $scope.init = function () {

            $scope.selectedUserBranch = "all";
            $scope.selectedAssigneePersonnel.selected = undefined;

            $scope.branchStatRange = $filter('date')(new Date(moment().day(-7)), "yyyy-MM-dd") + " : " + $filter('date')(new Date(), "yyyy-MM-dd");

            $scope.showProgress(true, "Initializing");
            $scope.trLoadCounter = 2;
            $scope.getUserDetails();
            $scope.getUserBranches();

        }; // end of init function

        $scope.showProgress = function (action, message) {
            $scope.progressMessage = message;
            if (action) {
                $('#progressModal').modal('show');
            } else {
                $('#progressModal').modal('hide');
            }
        };

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
                        if ($scope.isHeadOffice()) {
                            $scope.trLoadCounter += 1;
                            $scope.getPersonalInfoComplaintStatisticsData($scope.currentUser.staffId);
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
        $scope.getPersonalInfoComplaintStatisticsData = function (staffId) {
            if ($scope.trLoadCounter <= 0) {
                $scope.showProgress(true, "Fetching Personal Complaint Statistics...");
            }
            TransferRecallDashboardService.getPersonalInfoComplaintStatisticsData(
                staffId,
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

        $scope.getUserBranches = function () {
            if ($scope.trLoadCounter <= 0) {
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
        }; //end of get user branches


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
                $scope.trLoadCounter += 3;

                branchCodes = [];
                $scope.branchCodesForTable = [];
                branchCodes.push($scope.currentBranch.branchCode);
                $scope.branchCodesForTable.push($scope.currentBranch.branchCode);
                $scope.fetchBranchLoggedComplaintsManual();

                // Load Personnel in User's Branch
                $scope.getBranchUsers(branchCodes);
                // Load User's Group's Info Complaint Data
                $scope.getGroupInfoComplaintStatisticsData(userBranches);
                // Get Branch(es) statistics
                $scope.getBranchesComplaintsStatistics(userBranches, "");

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
                $scope.fetchBranchLoggedComplaintsManual();

                // Load Personnel in User's Branches
                $scope.getBranchUsers(branchCodes);
                // Load User's Group's Info Complaint Data
                $scope.getGroupInfoComplaintStatisticsData(userBranches);
                //Get Branch(es) statistics
                $scope.getBranchesComplaintsStatistics(userBranches, "");

            } else {
                // No branches for that user
                $scope.multipleBranches = false;
                $scope.noBranches = true;
                $rootScope.PageTitle = "Dashboard";
            }
        }; // end of initialize with branch data

        $scope.fetchBranchLoggedComplaintsManual = function () {
            if ($scope.branchLoggedComplaintsTableState != []) {
                $scope.fetchBranchLoggedComplaints($scope.branchLoggedComplaintsTableState);
            }
        };

        $scope.fetchBranchLoggedComplaints = function (tableState) {
            if (typeof tableState.pagination == "undefined") {
                tableState.pagination = [];
                tableState.pagination.start = 0;
                tableState.pagination.number = 10;
                tableState.search = [];
            }
            $scope.branchLoggedComplaintsTableState = tableState;
            var start = tableState.pagination.start || 0;
            var length = tableState.pagination.number || 10;
            var search = $.isEmptyObject(tableState.search) ? null : tableState.search;
            var searchArray = [];
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
                branches: $scope.branchCodesForTable
            };

            TransferRecallDashboardService.fetchBranchLoggedComplaints(
                function (data) {
                    $scope.branchLoggedComplaints = data.data;
                    tableState.pagination.numberOfPages = data.totalPages;
                    $scope.isBranchLoggedComplaintsLoading = false;
                },
                function (data) {
                },
                request
            );
        }; // end of fetch branch logged complaints


        // Get Personnel at User's Branch
        $scope.getBranchUsers = function (branchCodes) {

            if ($scope.trLoadCounter <= 0) { // Initialization has finished
                $scope.showProgress(true, "Fetching Branch Users.");
            }

            TransferRecallDashboardService.getBranchUsers(
                    branchCodes,
                    function (data, status) {
                        if (status == 200 || status == 201) {
                            $scope.currentBranchUsers = data;
                        } else {
                            PNotifyService.error("Failed to get branch users.");
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
                    });
        };

        // Get User's Branch's Statistical Data for Assigned Complaints (Info Dashboard Only)
        $scope.getGroupInfoComplaintStatisticsData = function (staffId) {
            if ($scope.trLoadCounter <= 0) {
                $scope.showProgress(true, "Fetching Branches Complaint Statistics...");
            }
            TransferRecallDashboardService.getGroupInfoComplaintStatisticsData(
                staffId,
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

        //Get Statistics for Branches Complaints (Across Branches)
        $scope.getBranchesComplaintsStatistics = function (branches, dateRange) {

            var requestBody = {
                branches: branches,
                dateRange: dateRange
            };

            if ($scope.trLoadCounter <= 0) { // Initialization has finished
                $scope.showProgress(true, "Fetching Branch Stats.");
            }
            TransferRecallDashboardService.getBranchesComplaintsStatistics(
                requestBody,
                function (data, status) {
                    if (status == 200 || status == 201) {
                        $scope.branchesComplaintStatistics = data;
                        $scope.setBranchesComplaintsBarChartStatistics($scope.branchesComplaintStatistics);
                    } else {
                        $scope.trError = true;
                        $scope.trErrorMessage = "Failed to get branch stats.";
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

        $scope.setGroupStatisticsVariables = function (stats) {
            $scope.groupUnassignedIntraComplaints = stats.unassignedIntraComplaints;
            $scope.groupAssignedIntraComplaints = stats.assignedIntraComplaints;
            $scope.groupUnassignedInterComplaints = stats.unassignedInterComplaints;
            $scope.groupAssignedInterComplaints = stats.assignedInterComplaints;
            $scope.groupProcessingInterComplaints = stats.processingInterComplaints;
            $scope.groupProcessingIntraComplaints = stats.processingIntraComplaints;
            $scope.groupBranchComplaintsHistory = stats.branchComplaintsHistory;
            $scope.groupResolvedCount = stats.resolvedCount;
        };

        $scope.setPersonalStatisticsVariables = function (stats) {
            $scope.personalUnassignedIntraComplaints = stats.unassignedIntraComplaints;
            $scope.personalAssignedIntraComplaints = stats.assignedIntraComplaints;
            $scope.personalUnassignedInterComplaints = stats.unassignedInterComplaints;
            $scope.personalAssignedInterComplaints = stats.assignedInterComplaints;
            $scope.personalProcessingInterComplaints = stats.processingInterComplaints;
            $scope.personalProcessingIntraComplaints = stats.processingIntraComplaints;
            $scope.personalBranchComplaintsHistory = stats.branchComplaintsHistory;
            $scope.personalResolvedCount = stats.resolvedCount;
        };

        $scope.setBranchesComplaintsBarChartStatistics = function (statistics) {

            $scope.branchesComplaintsTotal = statistics.total;
            $scope.branchesComplaintsTotalResolvedInter = statistics.totalResolvedInter;
            $scope.branchesComplaintsTotalResolvedIntra = statistics.totalResolvedIntra;
            $scope.branchesComplaintsTotalUnassignedInter = statistics.totalUnassignedInter;
            $scope.branchesComplaintsTotalUnassignedIntra = statistics.totalUnassignedIntra;
            $scope.branchesComplaintsTotalPendingInter = statistics.totalPendingInter;
            $scope.branchesComplaintsTotalPendingIntra = statistics.totalPendingIntra;

            $scope.branchesComplaintsBarChart.data = [
                [
                    $scope.branchesComplaintsTotalResolvedInter,
                    $scope.branchesComplaintsTotalResolvedIntra,
                    $scope.branchesComplaintsTotalUnassignedInter,
                    $scope.branchesComplaintsTotalUnassignedIntra,
                    $scope.branchesComplaintsTotalPendingInter,
                    $scope.branchesComplaintsTotalPendingIntra
                ]
            ];
        };

        $scope.isHeadOffice = function () {
            return AuthServerProvider.isInRole('as_tr_view_all') || AuthServerProvider.isInRole('as_tr_view_module')
        };
    }
]);

app.service('TransferRecallDashboardService', ['$http', '$stateParams', 'APIServiceHandler', 'TRANSFER_RECALL_SERVICE_HOST',

    function ($http, $stateParams, APIServiceHandler, TRANSFER_RECALL_SERVICE_HOST) {

        this.getUserDetails = function (successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.get(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/user-details"), successHandler, errorHandler);
        };
        this.getPersonalInfoComplaintStatisticsData = function (staffId, successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.get(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/user-info-complaint-stats/" + staffId), successHandler, errorHandler);
        };
        this.getUserBranches = function (successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.get(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/user-branches"), successHandler, errorHandler);
        };
        this.fetchBranchLoggedComplaints = function (successHandler, errorHandler, data) {
            APIServiceHandler.handleAPICall($http.post(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/branch-logged-complaints", data), successHandler, errorHandler);
        };
        this.getBranchUsers = function (data, successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.post(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/branch-users", data), successHandler, errorHandler);
        };
        this.getGroupInfoComplaintStatisticsData = function (data, successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.post(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/group-info-complaint-stats", data), successHandler, errorHandler);
        };
        this.getBranchesComplaintsStatistics = function (data, successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.post(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/branches-complaints-stats", data), successHandler, errorHandler);
        };
    }
]);

app.filter('encodeBase64', function(){
    return function(text){
        return btoa(text);
    }
});