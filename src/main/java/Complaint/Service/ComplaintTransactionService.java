package Complaint.Service;

import Complaint.Entity.TransactionComplaint;
import Complaint.Repository.ComplaintTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ComplaintTransactionService {
    @Autowired
    private ComplaintTransactionRepo complaintTransactionRepo;
    public void saveTransactionComolaint(TransactionComplaint transactionComplaint){
        complaintTransactionRepo.save(transactionComplaint);
    }
}
