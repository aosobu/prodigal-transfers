package Complaint.service.interfaces;

import Complaint.model.Complaint;

public interface ComplaintService {

    Complaint saveComplaint(Complaint complaint);
    Long getComplaintsByStaffIdAndProcessingStateAndRecallType(String staffId, Long processingState, String recallType);

}
