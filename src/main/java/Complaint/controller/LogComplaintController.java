package Complaint.controller;

import Complaint.model.*;
import Complaint.model.api.AccountDateRange;
import Complaint.model.api.ComplaintLoggingRequest;
import Complaint.service.*;
import com.teamapt.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class LogComplaintController {

    private ComplaintLoggingService complaintLoggingService;
    private ComplaintTransactionServiceImpl complaintTransactionService;
    private TransactionService transactionService;
    private CustomerService customerService;
    private BankServiceImpl bankServiceImpl;

    @RequestMapping(value = "complaint-transactions", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Transaction> getComplaintTransactions(@RequestBody @Valid AccountDateRange accountDateRange) throws ApiException {
        try {
            return transactionService.getComplaintTransactions(accountDateRange);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @RequestMapping(value = "check-for-logged", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Transaction> checkIfAlreadyLogged(@RequestBody @Valid List<Transaction> transactions) throws ApiException {
        try {
            return complaintTransactionService.findAlreadyLoggedTxns(transactions);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @RequestMapping(value = "customer-details/{accNo}", method = RequestMethod.GET)
    public
    @ResponseBody
    Customer getCustomerComplaints(@PathVariable String accNo) throws ApiException {
        try {
            return customerService.getCustomerDetails(accNo);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @RequestMapping(value = "banks", method = RequestMethod.GET)
    public @ResponseBody List<Bank> getBankList() throws ApiException{
        try{
            return bankServiceImpl.getAllBanks();
        }catch(Exception e){
            throw new ApiException(e.getMessage());
        }
    }

    @RequestMapping(value = "log-complaints", method = RequestMethod.POST)
    public List<Complaint> logComplaint(@RequestParam("complaints") String complaint,
                                        @RequestParam("customerInstruction") MultipartFile file,
                                        @RequestParam("customerInstructionName") String fileName
                                        ) throws ApiException {
        ComplaintLoggingRequest complaintLoggingRequest = null;

        try {
            complaintLoggingRequest = ComplaintLoggingRequest.with(complaint, file, fileName, "");
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }

        try {
            return complaintLoggingService.logComplaint(complaintLoggingRequest);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Autowired
    public void setComplaintLoggingService(ComplaintLoggingService complaintLoggingService) {
        this.complaintLoggingService = complaintLoggingService;
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setBankServiceImpl(BankServiceImpl bankServiceImpl) {
        this.bankServiceImpl = bankServiceImpl;
    }

    @Autowired
    public void setComplaintTransactionService(ComplaintTransactionServiceImpl complaintTransactionService) {
        this.complaintTransactionService = complaintTransactionService;
    }
}
