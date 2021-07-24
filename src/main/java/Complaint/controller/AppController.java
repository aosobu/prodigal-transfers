package Complaint.controller;

import Complaint.model.*;
import Complaint.model.api.DataTableRequest;
import Complaint.service.BranchUserService;
import Complaint.service.ComplaintInfoService;
import Complaint.service.ComplaintServiceImpl;
import Complaint.service.interfaces.ComplaintService;
import com.teamapt.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "user-info-complaint-stats/{staffId}", method = RequestMethod.GET)
    public @ResponseBody InfoComplaintStats getUserComplaintStats(@PathVariable String staffId) throws ApiException {
        try {
            return complaintInfoService.computeUserInfoComplaintStatistics(staffId);
        } catch (Exception e) {
            throw new ApiException("Error Fetching User Details {} ");
        }
    }


    @RequestMapping(value = "user-details", method = RequestMethod.GET)
    public @ResponseBody BranchUser getUserDetails() throws ApiException {
        try {
            //for test purpose
            String user = "ST0673";
            return branchUserService.getUserDetails(user);
        } catch (Exception e) {
            throw new ApiException("Error Fetching User Details {} ");
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

    @RequestMapping(value = "branch-logged-complaints", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getBranchLoggedComplaints(@RequestBody DataTableRequest request, Principal principal) throws ApiException {
        try {
            return complaintServiceImpl.getBranchLoggedComplaintHistory(request, "P0763");
        } catch (Exception e) {
            throw new ApiException("Error Fetching Branch Logged Complaints");
        }
    }

    @RequestMapping(value = "branch-users", method = RequestMethod.POST)
    public @ResponseBody List<BranchUser> getBranchUsers(@RequestBody @Valid List<String> branchCodes) throws ApiException {
        try {
            return branchUserService.getBranchUsers(branchCodes);
        } catch (Exception e) {
            throw new ApiException("Problem fetching branch user information");
        }
    }

    //untested
    @RequestMapping(value = "group-info-complaint-stats", method = RequestMethod.POST)
    public @ResponseBody InfoComplaintStats getGroupComplaintStats(@RequestBody @Valid List<Branch> branches) throws ApiException {
        try {
            return complaintInfoService.computeGroupInfoComplaintStatistics(branches);
        } catch (Exception e) {
            throw new ApiException("Problem fetching group details");
        }
    }

    //untested
    @RequestMapping(value = "branches-complaints-stats", method = RequestMethod.POST)
    public @ResponseBody
    BranchesComplaintsStatistics getBranchesComplaintsStats(@RequestBody @Valid BranchStatDateRange branchStatDateRange) throws ApiException {
        try {
            return complaintInfoService.getBranchesComplaintsStatistics(branchStatDateRange);
        } catch (Exception e) {
            throw new ApiException("Problem fetching branches complaints statistics");
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
}
