package Complaint.service.complaint;

import Complaint.model.Complaint;
import Complaint.repository.ComplaintRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ComplaintServiceImpl implements ComplaintService{

//    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    @Override
    public Complaint getAComplaint(Long complaintId) {
        return complaintRepository.findById(complaintId).orElse(null);
    }

    @Override
    public Complaint createOrUpdateAComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public void deleteAComplaint(Long complaintId) {
        complaintRepository.deleteById(complaintId);
    }
}
