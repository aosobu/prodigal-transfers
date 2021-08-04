package Complaint.service;

import Complaint.enums.ApprovalStatus;
import Complaint.model.BranchUser;
import Complaint.model.Complaint;

import java.util.Date;

public class ComplaintApprovalSetter {

    public static Boolean approve(Complaint complaint, BranchUser branchUser) {

        try{
            complaint.setApprovalStatus(ApprovalStatus.APPROVED.getApprovalStatus());
            complaint.setApproved(true);
            complaint.setAuthorisationOrDeclineDate(new Date());
            complaint.setAuthoriserOrDeclinerName(branchUser.getStaffName());
            complaint.setAuthoriserOrDeclinerStaffId(branchUser.getStaffId());
            complaint.setAuthoriserOrDeclinerBranch(branchUser.getBranchCode());
        }catch(Exception ex){
            return false;
        }
        return true;
    }
}
