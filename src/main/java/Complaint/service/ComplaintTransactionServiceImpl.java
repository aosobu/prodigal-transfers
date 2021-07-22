package Complaint.service;

import Complaint.model.ComplaintTransaction;
import Complaint.model.Transaction;
import Complaint.repository.ComplaintTransactionRepository;
import Complaint.service.interfaces.ComplaintTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintTransactionServiceImpl implements ComplaintTransactionService {

    private ComplaintTransactionRepository complaintTransactionRepository;

    public List<Transaction> findAlreadyLoggedTxns(List<Transaction> transactions) {
        List<ComplaintTransaction> matchingComplaints;

        for (Transaction txn : transactions) {
            matchingComplaints =  complaintTransactionRepository.findByAccountNumberAndTranIdAndAmountAndTransactionDate(
                    txn.getAccountNumber(),
                    txn.getTranId(),
                    txn.getAmount(),
                    txn.getTransactionDate()
            );

            if (!matchingComplaints.isEmpty())
                txn.setAlreadyLoggedComplaint(matchingComplaints.get(0));
        }

        return transactions;
    }

    @Autowired
    public void setComplaintTransactionRepository(ComplaintTransactionRepository complaintTransactionRepository) {
        this.complaintTransactionRepository = complaintTransactionRepository;
    }
}
