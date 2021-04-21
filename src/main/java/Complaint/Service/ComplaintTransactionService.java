package Complaint.Service;

import Complaint.Entity.TransactionComplaint;
import Complaint.Repository.ComplaintTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ComplaintTransactionService {

    private ComplaintTransactionRepo complaintTransactionRepo;

    @Autowired
    public ComplaintTransactionService(ComplaintTransactionRepo complaintTransactionRepo){
        this.complaintTransactionRepo = complaintTransactionRepo;
    }
    public void saveTransactionComplaint(TransactionComplaint transactionComplaint){
        complaintTransactionRepo.save(transactionComplaint);
    }
    public TransactionComplaint findByTransID(String trans_Id){
        return complaintTransactionRepo.findByTransID(trans_Id);
    }
}
