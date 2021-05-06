package Complaint.Service.complaintrequestprocessor;

import Complaint.enums.ApprovalStatus;
import Complaint.model.Complaint;

import java.util.List;

public class UpdateApprovalStatusFilter implements ComplaintRequestFilterProcessor{

    @Override
    public Complaint processTwo(Complaint complaint) throws Exception {

        try {
            if (complaint.getApprovalStatus() == null || complaint.getApprovalStatus().equals(ApprovalStatus.DECLINED)) {
                complaint.setApprovalStatus(ApprovalStatus.APPROVED);
            }
        }
        catch (Exception e) {
            throw new Exception("Approval status cannot be set");
        }

        return complaint;
    }
}
