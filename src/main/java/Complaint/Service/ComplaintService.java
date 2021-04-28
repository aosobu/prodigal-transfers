package Complaint.service;

import Complaint.entity.Complaint;
import Complaint.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Complaint> logComplaint(List<Complaint> complaintList){
             for(Complaint complaint: complaintList) {
                 complaintRepository.save(complaint);
             }
        return null;
    }
}
