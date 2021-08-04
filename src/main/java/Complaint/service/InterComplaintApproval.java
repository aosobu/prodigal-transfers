package Complaint.service;

import Complaint.enums.ApprovalStatus;
import Complaint.enums.TransferRecallType;
import Complaint.model.BranchUser;
import Complaint.model.Complaint;
import Complaint.service.interfaces.ComplaintApproval;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InterComplaintApproval implements ComplaintApproval{

    @Override
    public String getRecallType() {
        return TransferRecallType.INTER.getCode();
    }

    @Override
    public boolean approve(Complaint complaint, BranchUser branchUser) {
        return ComplaintApprovalSetter.approve(complaint, branchUser);
    }
}
