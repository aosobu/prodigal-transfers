package Complaint.service.interfaces;

import Complaint.model.Complaint;

import java.util.Date;

public interface ComplaintService {

    Complaint saveComplaint(Complaint complaint);
    Long getComplaintsByStaffIdAndProcessingStateAndRecallType(String staffId, Long processingState, String recallType);
    Long getComplaintsByBranchCodeAndProcessingStateAndRecallType(String branchCode, Long processingState, String rcallType);
    Long getCountByBranchCodeLoggedAndDateRange(String branchCodeLogged, String start, String end);
    Long getCountByBranchCodeLoggedAndComplaintStateAndRecallTypeAndDateRange(String branchCodeLogged, Long complaintProcessingState, String recallType, String start, String end);
}
