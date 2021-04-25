package Complaint.Service;

import Complaint.DTO.ComplaintTransferQueryModel;
import Complaint.Repository.ComplaintTransferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintTransferService {
    @Autowired
    private ComplaintTransferRepo complaintTransferRepo;

    public ComplaintTransferQueryModel getComplaintTransferDetails(String customer_id, String transaction_id){
        return complaintTransferRepo.ComplaintTransferDetails(customer_id,transaction_id);
    }
}
