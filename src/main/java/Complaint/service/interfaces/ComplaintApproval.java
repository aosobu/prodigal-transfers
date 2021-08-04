package Complaint.service.interfaces;

import Complaint.model.BranchUser;
import Complaint.model.Complaint;

public interface ComplaintApproval {
    String getRecallType();
    boolean approve(Complaint complaint, BranchUser branchUser);
}
