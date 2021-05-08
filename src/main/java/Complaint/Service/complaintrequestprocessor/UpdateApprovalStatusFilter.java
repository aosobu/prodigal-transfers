package Complaint.Service.complaintrequestprocessor;

import Complaint.enums.ApprovalStatus;
import Complaint.model.Complaint;

import java.util.List;

public class UpdateApprovalStatusFilter implements ComplaintRequestFilterProcessor{

    @Override
    public List<Complaint> process(List<Complaint> complaint) throws Exception {

        for (Complaint aComplaint : complaint) {
            try {
                if (aComplaint.getApprovalStatus() == null || aComplaint.getApprovalStatus().equals(ApprovalStatus.DECLINED)) {
                    aComplaint.setApprovalStatus(ApprovalStatus.APPROVED);
                }
            }
            catch (Exception e) {
                throw new Exception("Approval status cannot be set");
            }
        }

        return complaint;
    }
}
