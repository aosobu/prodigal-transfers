package Complaint.service.complaint;

import Complaint.model.Complaint;
import java.util.List;

public interface ComplaintService {

    List<Complaint> getAllComplaints();
    Complaint getAComplaint(Long complaintId);
    Complaint createOrUpdateAComplaint(Complaint complaint);
    void deleteAComplaint(Long complaintId);
}