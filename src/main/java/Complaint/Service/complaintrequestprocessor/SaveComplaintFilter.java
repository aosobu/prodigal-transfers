package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;
import Complaint.repository.ComplaintRepository;

import java.util.List;

public class SaveComplaintFilter implements ComplaintRequestFilterProcessor{

    ComplaintRepository complaintRepository;

    @Override
    public Complaint processTwo(Complaint complaint) throws Exception {

        try {
            complaintRepository.save(complaint);
        }
        catch (Exception e) {
            throw new Exception("Could not save the complaint");
        }

        return complaint;
    }
}
