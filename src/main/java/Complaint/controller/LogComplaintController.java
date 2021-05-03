package Complaint.controller;

import Complaint.Service.ComplaintLoggingService;
import Complaint.model.Complaint;
import Complaint.model.api.ComplaintLoggingRequest;
import com.teamapt.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class LogComplaintController {

    private ComplaintLoggingService complaintLoggingService;

    // another endpoint to get complaint transaction
//    @RequestMapping(value = "get-complaint-transactions", method = RequestMethod.POST)
//    public @ResponseBody
//    List<Transaction> getComplaintTransactions(@RequestBody @Valid AccountDateRange accountDateRange) throws ApiException {
//        try {
//            return transactionService.getComplaintTransactions(accountDateRange);
//        } catch (Exception e) {
//            throw new ApiException(e);
//        }
//    }

    // another endpoint to get customer details
//    @RequestMapping(value = "get-customer-details/{accNo}", method = RequestMethod.GET)
//    public @ResponseBody
//    Customer getCustomerComplaints(@PathVariable String accNo) throws ApiException {
//        try {
//            return customerService.getCustomerDetails(accNo);
//        } catch (Exception e) {
//            throw new ApiException(e);
//        }
//    }


    //PreAuthorize("hasAuthority('as_cs_log_complaint')")
    @RequestMapping(value = "log-complaints", method = RequestMethod.POST)
    public List<Complaint> logComplaint(@RequestBody Complaint complaint,
                                        @RequestParam("staffId") String staffId,
                                        Principal principal) throws ApiException {
        try {
            ComplaintLoggingRequest complaintLoggingRequest = ComplaintLoggingRequest.with(complaint);
            complaintLoggingRequest.setInitiator(complaintLoggingRequest.getInitiatorOrDefault(principal.getName()));
            return complaintLoggingService.logComplaint(complaintLoggingRequest);
        } catch (Exception e) {
            throw new ApiException(e);
        }
    }

    @Autowired
    public void setComplaintService(ComplaintLoggingService complaintLoggingService) {
        this.complaintLoggingService = complaintLoggingService;
    }
}
