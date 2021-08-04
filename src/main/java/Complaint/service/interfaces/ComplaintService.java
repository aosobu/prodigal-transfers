package Complaint.service.interfaces;

import Complaint.model.Complaint;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ComplaintService {

    Complaint saveComplaint(Complaint complaint);
    Long getComplaintsByStaffIdAndProcessingStateAndRecallType(String staffId, Long processingState, String recallType);
    Long getComplaintsByBranchCodeAndProcessingStateAndRecallType(String branchCode, Long processingState, String rcallType);
    Long getCountByBranchCodeLoggedAndDateRange(String branchCodeLogged, String start, String end);
    Long getCountByBranchCodeLoggedAndComplaintStateAndRecallTypeAndDateRange(String branchCodeLogged, Long complaintProcessingState, String recallType, String start, String end);
    void deleteComplaint(Complaint complaint);
    Long countAllComplaintsByStaffId(String staffId);
    Long countAllComplaintsByBranchCode(String branchCode);
    Long countAllByProcessingState(Long processingState);
    Long countAllComplaints();
    Long countAllByProcessingStateAndRecallType(Long processingstate, String recallType);
    Optional<Complaint> getComplaint(Long id);
    List<Complaint> getComplaintByProcessingStateAndRecallTypeAndApprovalStatus(Long processingState, String recallType, int approvalStatus);
}
