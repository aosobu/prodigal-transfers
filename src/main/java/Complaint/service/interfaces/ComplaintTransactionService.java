package Complaint.service.interfaces;

import Complaint.model.Transaction;

import java.util.List;

public interface ComplaintTransactionService {
    List<Transaction> findAlreadyLoggedTxns(List<Transaction> transactions);
}
