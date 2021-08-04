package Complaint.controller;

import Complaint.model.*;
import Complaint.model.api.DataTableRequest;
import Complaint.service.*;
import com.teamapt.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class AppController {

    private ComplaintInfoService complaintInfoService;
    private BranchUserService branchUserService;
    private ComplaintServiceImpl complaintServiceImpl;
    private ComplaintApprovalService approvalService;
    private ComplaintDeclineHandler complaintDeclineHandler;

    @RequestMapping(value = "user-info-complaint-stats", method = RequestMethod.POST)
    public @ResponseBody InfoComplaintStats getUserComplaintStats(@RequestParam("staffId") String staffId,
                                                                  @RequestParam("roleType") String role) throws ApiException {
        try {
            return complaintInfoService.computeUserInfoComplaintStatistics(staffId, role);
        } catch (Exception e) {
            throw new ApiException("Error Fetching User Details {} ");
        }
    }

    @RequestMapping(value = "user-details", method = RequestMethod.GET)
    public @ResponseBody BranchUser getUserDetails() throws ApiException {
        try {
            //for test purpose but will be gotten via the principal
            String user = "ST0673";
            return branchUserService.getUserDetails(user);
        } catch (Exception e) {
            throw new ApiException("Error Fetching User Details {} ");
        }
    }

    @RequestMapping(value = "group-info-complaint-stats", method = RequestMethod.POST)
    public @ResponseBody InfoComplaintStats getGroupComplaintStats(@RequestParam("role") String role,
                                                                   @RequestParam("branches") String branch) throws ApiException {
        try {
            List<Branch> branches = GroupComplaintStatsRequests.getBranchObjects(branch);
            return complaintInfoService.computeGroupInfoComplaintStatistics(role, branches);
        } catch (Exception e) {
            throw new ApiException("Error Fetching Group Complaint Statistics");
        }
    }

    @RequestMapping(value = "user-branches", method = RequestMethod.GET)
    public @ResponseBody List<Branch> getUserBranches(Principal principal) throws ApiException {
        try {
            String user = "ST0673";
            return branchUserService.getUserBranches(user);
        } catch (Exception e) {
            throw new ApiException("Error Fetching Branch data {}");
        }
    }

    @RequestMapping(value = "complaints-admin", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getAdminOrAuthorizerComplaints(@RequestBody DataTableRequest request) throws ApiException {
        try {
            return complaintServiceImpl.getUnassignedComplaintHistory(request);
        } catch (Exception e) {
            throw new ApiException("Error Fetching Complaints {}");
        }
    }

    @RequestMapping(value = "branch-logged-complaints", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getBranchLoggedComplaints(@RequestBody DataTableRequest request, Principal principal) throws ApiException {
        try {
            String staffId = principal.getName();
            return complaintServiceImpl.getLoggedComplaints(request, staffId);
        } catch (Exception e) {
            throw new ApiException("Error Fetching Complaints {}");
        }
    }

    @RequestMapping(value = "approve", method = RequestMethod.POST)
    public @ResponseBody String approveComplaints(@RequestBody List<String> complaintIds, Principal principal) throws ApiException {
        try {

            return approvalService.approveComplaints(complaintIds, principal.getName());
        } catch (Exception e) {
            throw new ApiException("Error Approving Complaints {}" + e.getMessage());
        }
    }

    @RequestMapping(value = "decline-complaints", method = RequestMethod.POST)
    public @ResponseBody String declineComplaints(@RequestBody List<String> complaintIds, Principal principal) throws ApiException {
        try {

            return complaintDeclineHandler.declineComplaints(complaintIds, principal.getName());
        } catch (Exception e) {
            throw new ApiException("Error Declining Complaints {}" + e.getMessage());
        }
    }

    @RequestMapping(value = "update-complaints", method = RequestMethod.POST)
    public @ResponseBody String updateComplaints(@RequestBody List<String> complaintIds, Principal principal) throws ApiException {
        try {
            return null;
        } catch (Exception e) {
            throw new ApiException("Error Updating Complaints {}");
        }
    }

    @RequestMapping(value = "bulk-upload-update-complaints", method = RequestMethod.POST)
    public @ResponseBody String bulkUploadUpdateComplaints(@RequestParam("bulkUploadFile") MultipartFile bulkUploadFile,
                                                           Principal principal) throws ApiException {
        try {

            return null;
        } catch (Exception e) {
            throw new ApiException("Error Approving Complaints {}");
        }
    }

    @Autowired
    public void setComplaintInforService(ComplaintInfoService complaintInfoService) {
        this.complaintInfoService = complaintInfoService;
    }

    @Autowired
    public void setBranchUserService(BranchUserService branchUserService) {
        this.branchUserService = branchUserService;
    }

    @Autowired
    public void setComplaintServiceImpl(ComplaintServiceImpl complaintServiceImpl) {
        this.complaintServiceImpl = complaintServiceImpl;
    }

    @Autowired
    public void setApprovalService(ComplaintApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @Autowired
    public void setComplaintDeclineHandler(ComplaintDeclineHandler complaintDeclineHandler) {
        this.complaintDeclineHandler = complaintDeclineHandler;
    }
}
