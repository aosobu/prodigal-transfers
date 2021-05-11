package Complaint.controller;

import Complaint.model.BranchUser;
import Complaint.model.Complaint;
import Complaint.model.api.ComplaintLoggingRequest;
import Complaint.service.BankService;
import Complaint.service.ComplaintLoggingService;
import com.teamapt.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class LogComplaintController {

    private BankService bankService;
    private ComplaintLoggingService complaintLoggingService;

    //PreAuthorize("hasAuthority('as_tr_log_complaint')")
    @RequestMapping(value = "log-complaints", method = RequestMethod.POST)
    public List<Complaint> logComplaint(@RequestBody Complaint complaint,
                                        @RequestParam("filesListArr[]") MultipartFile[] files,
                                        Principal principal) throws ApiException {
        try {
            BranchUser branchUser = bankService.getUserDetails(principal.getName());
            ComplaintLoggingRequest complaintLoggingRequest = ComplaintLoggingRequest.with(complaint, branchUser, principal.getName());
            return complaintLoggingService.logComplaint(complaintLoggingRequest);
        } catch (Exception e) {
            throw new ApiException(e);
        }
    }

    @Autowired
    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }

    @Autowired
    public void setComplaintLoggingService(ComplaintLoggingService complaintLoggingService) {
        this.complaintLoggingService = complaintLoggingService;
    }
}
