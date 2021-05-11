package Complaint.service.complaintrequestprocessor;

import Complaint.model.Complaint;
import Complaint.repository.ComplaintRepository;

import java.util.List;

public class SaveComplaintFilter implements ComplaintRequestFilterProcessor{

    ComplaintRepository complaintRepository;

    @Override
    public List<Complaint> process(List<Complaint> complaint) throws Exception {

        for (Complaint aComplaint : complaint) {
            try {
                complaintRepository.save(aComplaint);
            }
            catch (Exception e) {
                throw new Exception("Could not save the complaint");
            }
        }

        return complaint;
    }
}