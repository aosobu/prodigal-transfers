package Complaint.service;

import Complaint.enums.ApprovalStatus;
import Complaint.model.BranchUser;
import Complaint.model.Complaint;
import com.teamapt.exceptions.CosmosServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ComplaintDeclineHandler {

    private ComplaintServiceImpl complaintService;
    private BranchUserService branchUserService;
    StringBuffer trackingNumber = new StringBuffer();


    public String declineComplaints(List<String> ids, String staffId) throws CosmosServiceException{
        List<Complaint> pendingDeclineApprovals = new ArrayList();
        BranchUser authorizer = branchUserService.getUserDetails(staffId);

        if( authorizer == null){
            throw new CosmosServiceException("Authorizer details not found on infopool {}, Kindly Contact Admin and Re-initate Approvals after resolution");
        }

        for(String id : ids){
            Optional<Complaint> complaint = complaintService.getComplaint(Long.parseLong(id));
            complaint.ifPresent(pendingDeclineApprovals::add);
        }

        for(Complaint complaint: pendingDeclineApprovals) {
            if(declineComplaints(complaint, authorizer)){
                complaintService.saveComplaint(complaint);
            }
        }

        return "Error encountered while declining complaints with the following tracking number {} " + trackingNumber;
    }

    private boolean declineComplaints(Complaint complaint, BranchUser branchUser){
        try{
            complaint.setApproved(false);
            complaint.setApprovalStatus(ApprovalStatus.DECLINED.getApprovalStatus());
            complaint.setAuthorisationOrDeclineDate(new Date());
            complaint.setAuthoriserOrDeclinerName(branchUser.getStaffName());
            complaint.setAuthoriserOrDeclinerStaffId(branchUser.getStaffId());
            complaint.setAuthoriserOrDeclinerBranch(branchUser.getBranchCode());
        }catch(Exception ex){
            trackingNumber.append(trackingNumber + " ,");
            return false;
        }
        return true;
    }

    @Autowired
    public void setComplaintService(ComplaintServiceImpl complaintService) {
        this.complaintService = complaintService;
    }

    @Autowired
    public void setBranchUserService(BranchUserService branchUserService) {
        this.branchUserService = branchUserService;
    }
}
