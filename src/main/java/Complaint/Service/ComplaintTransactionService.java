package Complaint.service;

import Complaint.entity.ComplaintTransaction;
import Complaint.repository.ComplaintTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ComplaintTransactionService {

    private ComplaintTransactionRepository complaintTransactionRepository;

    @Autowired
    public ComplaintTransactionService(ComplaintTransactionRepository complaintTransactionRepository){
        this.complaintTransactionRepository = complaintTransactionRepository;
    }
    public void saveTransactionComplaint(ComplaintTransaction complaintTransaction){
        complaintTransactionRepository.save(complaintTransaction);
    }
}
