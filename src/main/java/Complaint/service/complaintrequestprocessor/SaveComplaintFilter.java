package Complaint.service.complaintrequestprocessor;

import Complaint.model.Complaint;
import Complaint.repository.ComplaintRepository;
import Complaint.service.ComplaintServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaveComplaintFilter{

    ComplaintServiceImpl complaintServiceImpl;

    public List<Complaint> process(List<Complaint> complaint) throws Exception {
        List<Complaint> complaintList = new ArrayList<>();
        Complaint savedComplaint = new Complaint();

        for (Complaint aComplaint : complaint) {
            try {
                savedComplaint = complaintServiceImpl.saveComplaint(aComplaint);
                if(savedComplaint.getId() > 0){
                    complaintList.add(savedComplaint);
                }
            }
            catch (Exception e) {
                throw new Exception("Error saving complaint {} ");
            }
        }
        return complaintList;
    }

    @Autowired
    public void setComplaintServiceImpl(ComplaintServiceImpl complaintServiceImpl) {
        this.complaintServiceImpl = complaintServiceImpl;
    }
}
