app.controller('NIPRecallNewComplaintController', ['$scope', '$rootScope', '$stateParams', 'RootService', '$timeout', 'TransferRecallNewComplaintService',

    function($scope, $rootScope, $stateParams, RootService, $timeout, TransferRecallNewComplaintService){

        $rootScope.PageTitle = "Transfer Recall Complaint";

        RootService.decorate($scope); // ask meaning of code line

        $scope.trSuccess = false;
        $scope.trSuccessMessage = "";
        $scope.trError = false;
        $scope.trErrorMessage = "";

        $scope.trLogSuccess = false;
        $scope.trLogSuccessMessage = "";
        $scope.trLogError = false;
        $scope.trLogErrorMessage = "";

        $scope.wizardProgress = 1;

        // Step 1 Variables
        $scope.step1AccountNumber = "";
        $scope.step1DateRange = "";

        // Step 2 Variables
        $scope.complaintTransactionsStep2 = {};
        $scope.complaintTransactionsStep2HasDebit = true;
        $scope.filteredComplaintTransactionsStep2 = {};

        //step 3 variable
        $scope.complaintTransactionsStep3 = {};
        $scope.logComplaintsStep3 = [];
        $scope.customerStep3 = [];

        $scope.selectComplaintReason = {};
        $scope.banks = [];

        $scope.fileCustomerInstruction = {};
        $scope.fileName = "";
        $scope.fileObject = "";

        /** hide alert **/
        $scope.hideAlert = function (){
            $scope.trError = false;
            $scope.trSuccess = false;
        };

        $scope.showProgress = function (action, message) {
            $scope.progressMessage = message;
            if (action) {
                $('#progressModal').modal({backdrop: 'static'});
            } else {
                $('#progressModal').modal('hide');
            }
        };

        $scope.init = function () {

            $('#datepickerDateRange').daterangepicker({
                ranges: {
                    'Today': [moment(), moment()],
                    'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                    'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                    'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                    'This Month': [moment().startOf('month'), moment().endOf('month')],
                    'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                }
            });

            $scope.getBeneficiaryBanks();
        };

        /** handle form submission for step 1 **/
        $scope.nextButtonEnabled = function () {
            switch ($scope.wizardProgress) {
                case 1:
                    return $scope.step1AccountNumber != "" && $('#datepickerDateRange').val() != "";

                case 2:
                    for(var j = 0; j < $scope.complaintTransactionsStep2.length; j++) {
                        if($scope.complaintTransactionsStep2[j].selected){
                            return true;
                        }
                    }
                    return false;

                case 3:
                    return !$scope.createComplaintForm.$invalid && $scope.fileName!= "";

                default:
                    return true;
            }
        };

        /** controls what happens next during the data collection steps **/
        $scope.advanceWizard = function () {
            switch ($scope.wizardProgress) {
                case 1:
                    $scope.getDisputeTransactions();
                    break;
                case 2:
                    $scope.checkIfAlreadyLogged();
                    $scope.getCustomerDetails();
                    break;
                case 3:
                    $scope.logComplaints();
                    break;
            }
            $scope.wizardProgress++;
        };

        /** handle form progress **/
        $scope.reverseWizard = function () {
            switch ($scope.wizardProgress) {
                case 2:
                    $scope.resetStep1();
                    break;
                case 3:
                    $scope.resetStep2();
                    break;
            }
        };

        $scope.advanceWizardClick = function () {
            $timeout(function() {
                angular.element('#cs-next').trigger('click');
            }, 100);
        };

        /** reset steps **/
        $scope.resetStep1 = function(){
            if($scope.wizardProgress > 1) {
                $scope.wizardProgress = 1;
            }
            $('#datepickerDateRange').val($scope.step1DateRange);
            $scope.complaintTransactionsStep2 = {};
            $scope.filteredComplaintTransactionsStep2 = {};
            $scope.resetStep2();
        };

        $scope.resetStep2 = function(){
            if($scope.wizardProgress > 2) {
                $scope.wizardProgress = 2;
            }
           $scope.complaintTransactionsStep3 = {};
           $scope.logComplaintsStep3 = [];
           $scope.customerStep3 = null;
           $scope.selectComplaintReason = {};
           $scope.resetCustomerInstructionElement();
           $scope.resetStep3();
        };

        $scope.resetStep3 = function(){
            if($scope.wizardProgress > 3) {
                $scope.wizardProgress = 3;
            }

            $scope.trLogSuccess = false;
            $scope.trLogSuccessMessage = "";
            $scope.trLogError = false;
            $scope.trLogErrorMessage = "";

            $scope.complaintTransactionsStep3 = {};
            $scope.logComplaintsStep3 = [];
            $scope.resetCustomerInstructionElement();

            if ($scope.customerStep3 != null) {
                var accountName = $scope.customerStep3.accountName;
                var accountNumber = $scope.customerStep3.accountName;
                $scope.customerStep3 = {
                    accountName: accountName,
                    accountNumber: accountNumber
                };
            }

            // Hide all alerts
            $scope.hideAlert();
        };

        /** get recall transactions **/
        $scope.getDisputeTransactions = function(){
            console.log("get dispute transactions");

            $scope.step1DateRange = $('#datepickerDateRange').val();

            var requestBody = {
                accountNumber: $scope.step1AccountNumber,
                dateRange: $scope.step1DateRange
            };

            console.log("Request Body {} " + requestBody);

            if(requestBody.accountNumber == "" || requestBody.dateRange == "" || requestBody.dateRange.indexOf(":") < 0){
                $scope.trError = true;
                $scope.trErrorMessage = "Invalid Arguments";
                return;
            }

            $scope.showProgress(true, "Loading Transactions...");

            TransferRecallNewComplaintService.getComplaintTransactions(
                requestBody,
                function (data, status) {
                    if (status == 200 || status == 201) {
                        $scope.complaintTransactionsStep2 = data;
                        $scope.complaintTransactionsStep2HasDebit = true;
                        var seenDebit = false;
                        for (var i = 0; i < $scope.complaintTransactionsStep2.length; i++) {
                            if ($scope.complaintTransactionsStep2[i].tranType == "D"){
                                $scope.complaintTransactionsStep2HasDebit = true;
                                seenDebit = true;
                                break;
                            }
                        }
                        if (!seenDebit){
                            $scope.complaintTransactionsStep2HasDebit = false;
                        }
                        $scope.filteredComplaintTransactionsStep2 = data;
                    } else {
                        $scope.trError = true;
                        $scope.trErrorMessage = "Failed to get transactions";
                    }
                    $scope.showProgress(false);
                },
                function (data, status) {
                    $scope.trError = true;
                    $scope.trErrorMessage = data.description;
                    $scope.showProgress(false);transaction.selected
                }
            );
        }//end of get dispute transactions

        $scope.checkForReversalAndPassedDays = function(transaction) {
            if (!transaction.selected){
                $scope.filterTransactions();
                return;
            }

            var reversed = false;

            for (var j = 0; j < $scope.complaintTransactionsStep2.length; j++) {
                var txn = $scope.complaintTransactionsStep2[j];
                // Only credits
                if(txn.tranType == "C"){

                    // Get '@' positions to get terminal ids
                    var atIndexTxn = txn.narration.indexOf("@");
                    var atIndexSel = transaction.narration.indexOf("@");

                    // If they both have '@' in their narrations
                    if ((atIndexTxn > 0) && (atIndexSel > 0)) {
                        // If they both have the same narration after terminal id's
                        var strAfterAtTxn = txn.narration.substr(atIndexTxn+1).trim();
                        var strAfterAtSel = transaction.narration.substr(atIndexSel+1).trim();

                        if (strAfterAtTxn.substring(0, 12) == strAfterAtSel.substring(0, 12)
                            && transaction.amount == txn.amount){
                            // Check Session ID
                            if (transaction.sessionId != null && txn.sessionId != null) {
                                if (txn.sessionId == transaction.sessionId) {
                                    reversed = true;
                                    $scope.infoTransaction = txn;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if(reversed || transaction.passed120Days){
                transaction.selected = false;
                if(reversed) {
                    $scope.infoMessage = "This transaction has been reversed.";
                }
                if(transaction.passed120Days) {
                    $scope.infoMessage = "This transaction has passed the 120 day limit.";
                    $scope.infoTransaction = null;
                }
                $('#messageModal').modal('show');
            } else {
                $scope.filterTransactions();
            }
        }; // end of checkForReversalAndPassedDays

        $scope.filterTransactions = function() {
            // Get 1st Selected
            var selected = null;
            for (var i = 0; i < $scope.filteredComplaintTransactionsStep2.length; i++){
                if($scope.filteredComplaintTransactionsStep2[i].selected){
                    selected = $scope.filteredComplaintTransactionsStep2[i];
                    break;
                }
            }

            // Set filteredComplaintTransactionsStep2 based on selected transaction properties
            if(selected != null){

                $scope.searchTranGroup = selected.tranGroup;
                $scope.searchTicketGroup = selected.ticketGroup;
                $scope.searchIntl = selected.international;

                $scope.filteredComplaintTransactionsStep2 = [];

                for (var j = 0; j < $scope.complaintTransactionsStep2.length; j++){
                    // If selected has no ticket group, only select null ticket groups
                    if($scope.searchTranGroup != null) {
                        if ($scope.complaintTransactionsStep2[j].tranGroup != null || $scope.complaintTransactionsStep2[j].ticketGroup != null) {
                            if ($scope.complaintTransactionsStep2[j].tranGroup.name == $scope.searchTranGroup.name &&
                                $scope.complaintTransactionsStep2[j].ticketGroup.name == $scope.searchTicketGroup.name &&
                                $scope.complaintTransactionsStep2[j].international == $scope.searchIntl) {
                                $scope.filteredComplaintTransactionsStep2.push($scope.complaintTransactionsStep2[j]);
                            }
                        }
                    } else {
                        if($scope.complaintTransactionsStep2[j].tranGroup == null || $scope.complaintTransactionsStep2[j].ticketGroup == null) {
                            $scope.filteredComplaintTransactionsStep2.push($scope.complaintTransactionsStep2[j]);
                        }
                    }
                }
            } else {
                $scope.filteredComplaintTransactionsStep2 = $scope.complaintTransactionsStep2;
            }
        }; // end of filter transaction

        $scope.checkIfAlreadyLogged = function () {
            var selectedTxns = [];
            for(var j = 0; j < $scope.complaintTransactionsStep2.length; j++) {
                if($scope.complaintTransactionsStep2[j].selected){
                    selectedTxns.push($scope.complaintTransactionsStep2[j]);
                }
            }

            if(selectedTxns.length > 0){
                $scope.showProgress(true, "Checking Complaints...");

                TransferRecallNewComplaintService.checkIfAlreadyLogged(
                    selectedTxns,
                    function (data, status) {
                        if (status == 200 || status == 201) {
                            $scope.complaintTransactionsStep3 = data;
                        } else {
                            $scope.trError = true;
                            $scope.trErrorMessage = "Failed to check transactions";
                        }
                        $scope.showProgress(false);
                    },
                    function (data, status) {
                        $scope.trError = true;
                        $scope.trErrorMessage = data.description;
                        $scope.showProgress(false);
                    }
                );
            }
        }; // end of check if already logged

        $scope.getCustomerDetails = function () {
            var customerAccountNumber = $scope.filteredComplaintTransactionsStep2[0].accountNumber;

            $scope.showProgress(true, "Loading Customer Details...");

            TransferRecallNewComplaintService.getCustomerDetails(
                customerAccountNumber,
                function (data, status) {
                    if (status == 200 || status == 201) {
                        $scope.customerStep3 = data;
                    } else {
                        $scope.trError = true;
                        $scope.trErrorMessage = "Failed to get Customer Details";
                    }
                    $scope.showProgress(false);
                },
                function (data, status) {
                    $scope.trError = true;
                    $scope.trErrorMessage = "Failed to get Customer Details";
                    $scope.showProgress(false);
                }
            );
        }; // end of get customer details

        /** init calls **/
        $scope.getBeneficiaryBanks = function () {
            var dummy = "";
            TransferRecallNewComplaintService.getBanks(
                dummy,
                function (data, status) {
                    if(status == 200 || status == 201){
                        $scope.banks = data;
                        $scope.showProgress(false);
                    }else {
                        $scope.trError = true;
                        $scope.trErrorMessage = "Failed to get beneficiary banks";
                    }
                },
                function (data, status) {
                    $scope.showProgress(false);
                    PNotifyService.error("Failed to retrieve beneficiary banks");
                }
            );
        };

        //log complaints
        $scope.logComplaints = function () {
            console.log("About to log complaints {} ");
            // Check Customer Phone and Email
            if ($scope.customerStep3.email == ""){
                $scope.trLogError = true;
                $scope.trLogErrorMessage = "Set Customer Email!";
                return;
            }
            if ($scope.customerStep3.phone == ""){
                $scope.trLogError = true;
                $scope.trLogErrorMessage = "Set Customer Phone Number!";
                return;
            }

            $scope.trLogError = false;

            var count = 0;
            for (var i = 0; i < $scope.complaintTransactionsStep3.length; i++){
                if(!$scope.complaintTransactionsStep3[i].alreadyLoggedComplaint){

                    var txn = $scope.complaintTransactionsStep3[i];

                    var complaint = {

                        beneficiaryBank : $scope.customerStep3.beneficiaryFI.bankAbbreviation ,
                        beneficiaryAccountNumber : $scope.customerStep3.beneficiaryAccountNumber ,
                        beneficiaryName : $scope.customerStep3.beneficiaryName ,
                        beneficiaryBankCode : $scope.customerStep3.beneficiaryFI.bankCode ,
                        transferringBankCode : $scope.getTransferringBankCode() ,
                        complaintReason : $scope.customerStep3.complainantPrayer ,
                        amountTransferred :  txn.amount,
                        amountToBeRecalled: txn.amount,
                        complaintCustomer : {
                            customerEmail: $scope.customerStep3.email,
                            customerPhoneNumber: $scope.customerStep3.phone ,
                            customerAccountName: $scope.customerStep3.accountName,
                            customerAccountNumber: $scope.customerStep3.accountNumber,
                            customerAccountBranch: $scope.customerStep3.solId ,
                            customerClientType: $scope.customerStep3.schemeCode ,
                            customerAccountType: $scope.customerStep3.accountType ,
                            customerAccountCurrency: $scope.customerStep3.accountCurrencyCode ,
                            customerAddressLine1: $scope.customerStep3.addressLine1 ,
                            customerAddressLine2: $scope.customerStep3.addressLine2 ,
                            customerCity: $scope.customerStep3.city ,
                            customerState: $scope.customerStep3.state ,
                            customerCountry: $scope.customerStep3.country.name ,
                            customerPostalCode: $scope.customerStep3.postalCode ,
                            customerComplaintChannel: $scope.customerStep3.channel.code ,
                            customerComplaintLocation: $scope.customerStep3.city
                        },
                        complaintTransaction : {
                            accountNumber:  $scope.customerStep3.accountNumber ,
                            accountName:  $scope.customerStep3.accountName,
                            amount:  txn.amount,
                            narration:  txn.narration ,
                            sessionId:  txn.sessionId ,
                            tranType:  "transfers" ,
                            tranId:  txn.tranId ,
                            sol:  txn.sol ,
                            pan:  txn.pan ,
                            terminalId:  txn.terminalId ,
                            description:  $scope.getDescription() ,
                            rrn:  txn.rrn ,
                            stan:  txn.stan ,
                            currencyCode:  txn.currencyCode ,
                            transactionDate:  txn.transactionDate ,
                            valueDate:  txn.valueDate ,
                            international:  txn.international ,
                        },
                        branchUser: {
                            staffId: "" ,
                            staffName: ""  ,
                            branchCode: "" ,
                        },
                        complaintState:{},
                        complaintInstruction: {}
                    } // end of complaint payload
                }

                $scope.logComplaintsStep3.push(complaint);
                count++;
            } // end of for statement

            var formData = new FormData();

            //stop form submission if file objects and file names are empty

            if($scope.fileObject)
                formData.append("customerInstruction", $scope.fileObject);
            if($scope.fileName)
                formData.append("customerInstructionName", $scope.fileName);
            formData.append("complaints", angular.toJson($scope.logComplaintsStep3));

            if(count > 0) {
                $scope.showProgress(true, "Logging Recall Complaint(s)...");
                TransferRecallNewComplaintService.logComplaints(
                    formData,
                    function (data, status) {
                        if (status == 200 || status == 201) {
                            if(data[0].errorMessage == null){
                                $scope.trLogSuccess = true;
                                $scope.trLogSuccessMessage = "Complaint(s) Logged Successfully!";
                                $scope.showProgress(false);
                            }else{
                                $scope.trLogError = true;
                                $scope.trLogErrorMessage = data[0].errorMessage;
                                $scope.showProgress(false);
                            }
                        }
                    },
                    function (data, status) {
                        $scope.trLogError = true;
                        $scope.trLogErrorMessage = "Failed to Log Complaint(s): " + data.description;
                        $scope.showProgress(false);
                    }
                );
            } else {
                $scope.trLogError = true;
                $scope.trLogErrorMessage = "Complaint(s) Already Logged";
            }


        }; //end of log complaints

        $scope.getTransferringBankCode = function(){
            var bankSize = $scope.banks.length;
            for(i=0; i<bankSize; i++){
                if($scope.banks[i].bankName.toLowerCase() == "FBL".toLowerCase())
                    return $scope.banks[i].bankCode;
            }
        }

        $scope.getDescription = function(){
            if($scope.customerStep3.description != null)
                $scope.customerStep3.description.replace(/\n/g, '              ');
            else
                return "";
        }

        $scope.fileEvent = function() {
            console.log("Is event triggered {} ");
            $scope.fileObject = document.getElementById("customerInstruction").files[0];
            $scope.fileName = document.getElementById("customerInstruction").files[0].name;
        }

        $scope.resetCustomerInstructionElement = function(){
            $scope.fileCustomerInstruction.Name = document.getElementById("customerInstruction").value = null;
        }


        /************* static data ****************/
        $scope.countries = [
            {
                id: 1,
                name: "Nigeria",
                code: "NG"
            }
        ];

        $scope.states = [
            "Lagos",
            "FCT Abuja",
            "Abia",
            "Adamawa",
            "Akwa",
            "Anambra",
            "Bauchi",
            "Bayelsa",
            "Benue",
            "Borno",
            "Cross",
            "Delta",
            "Ebonyi",
            "Edo",
            "Ekiti",
            "Enugu",
            "Gombe",
            "Imo",
            "Jigawa",
            "Kaduna",
            "Kano",
            "Katsina",
            "Kebbi",
            "Kogi",
            "Kwara",
            "Nasarawa",
            "Niger",
            "Ogun State",
            "Ondo State",
            "Osun State",
            "Oyo State",
            "Plateau",
            "Rivers",
            "Sokoto",
            "Taraba",
            "Yobe",
            "Zamfara"
        ];

        $scope.channels = [
            {
                id: 1,
                name: "Letter",
                code: "Letter"
            },
            {
                id: 2,
                name: "Email",
                code: "Email"
            },
            {
                id: 3,
                name: "Walk-In",
                code: "Walk_in"
            },
            {
                id: 4,
                name: "Website",
                code: "Website"
            },
            {
                id: 5,
                name: "Mobile App",
                code: "Mobile_app"
            },
            {
                id: 6,
                name: "Social Media",
                code: "Social_media"
            },
            {
                id: 7,
                name: "Phone",
                code: "Phone"
            },
            {
                id: 8,
                name: "Chat",
                code: "Chat"
            },
            {
                id: 9,
                name: "Others",
                code: "Others"
            }
        ];

    } // end of controller function
]);

/** service **/
app.service('TransferRecallNewComplaintService', ['$http', '$stateParams', 'APIServiceHandler', 'TRANSFER_RECALL_SERVICE_HOST',

    function ($http, $stateParams, APIServiceHandler, TRANSFER_RECALL_SERVICE_HOST) {
        this.getComplaintTransactions = function (data, successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.post(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/complaint-transactions", data), successHandler, errorHandler);
        };

        this.checkIfAlreadyLogged = function (data, successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.post(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/check-for-logged", data), successHandler, errorHandler);
        };

        this.getCustomerDetails = function (accountNumber, successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.get(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/customer-details/" + accountNumber), successHandler, errorHandler);
        };

        this.getBanks = function (data, successHandler, errorHandler) {
            APIServiceHandler.handleAPICall($http.get(TRANSFER_RECALL_SERVICE_HOST + "/api/v1/banks" + data), successHandler, errorHandler);
        };

        this.logComplaints = function (data, successHandler, errorHandler) {

            var httpVar = $http({
                method: 'POST',
                url: TRANSFER_RECALL_SERVICE_HOST + "/api/v1/log-complaints",
                data: data,
                headers: {'Content-Type': undefined}
            });

            APIServiceHandler.handleAPICall(httpVar, successHandler, errorHandler);
        };
    }
]);
