package Complaint.service;

import Complaint.enums.TransferRecallType;
import Complaint.model.BranchUser;
import Complaint.model.Complaint;
import Complaint.service.interfaces.ComplaintApproval;
import com.teamapt.exceptions.CosmosServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ComplaintApprovalService {

    private ComplaintServiceImpl complaintService;
    private Map<String, ComplaintApproval> approvalMap;
    private BranchUserService branchUserService;
    StringBuffer trackingNumber = new StringBuffer();

    public String approveComplaints(List<String> ids, String staffId) throws CosmosServiceException{
        List<Complaint> pendingApprovals = new ArrayList();
        boolean status = false;

        BranchUser authorizer = branchUserService.getUserDetails(staffId);

        if( authorizer == null){
            throw new CosmosServiceException("Authorizer details not found on infopool {}, Kindly Contact Admin and Re-initate Approvals after resolution");
        }

        for(String id : ids){
          Optional<Complaint> complaint = complaintService.getComplaint(Long.parseLong(id));
            complaint.ifPresent(pendingApprovals::add);
        }

        for(Complaint complaint: pendingApprovals){
            String interRecallType = TransferRecallType.INTER.getCode();
            if(complaint.getRecallType().equalsIgnoreCase(interRecallType)){
                initiateApproval(interRecallType, authorizer, complaint);
                break;
            }

            String intraRecallType  = TransferRecallType.INTRA.getCode();
            if(complaint.getRecallType().equalsIgnoreCase(intraRecallType)){
                initiateApproval(intraRecallType, authorizer, complaint);
                break;
            }
        }

        return "Error encountered while approving complaints with the following tracking number {} " + trackingNumber;
    }

    private void initiateApproval(String recallType, BranchUser authoriser, Complaint complaint){
        if(approvalMap.get(recallType).approve(complaint,  authoriser)) {
            complaintService.saveComplaint(complaint);
        }else{
            trackingNumber.append(trackingNumber + " ,");
        }
    }

    @Autowired
    public void setComplaintService(ComplaintServiceImpl complaintService) {
        this.complaintService = complaintService;
    }

    @Resource(name = "complaintApprovalMap")
    public void setApprovalMap(Map<String, ComplaintApproval> approvalMap) {
        this.approvalMap = approvalMap;
    }

    @Autowired
    public void setBranchUserService(BranchUserService branchUserService) {
        this.branchUserService = branchUserService;
    }
}
