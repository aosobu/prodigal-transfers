package Complaint.service.transaction;

import Complaint.model.ComplaintTransaction;
import java.util.List;

public interface ComplaintTransactionService {

    List<ComplaintTransaction> getAllTransactions();

    ComplaintTransaction getATransaction(Long complaintTransactionId);

    ComplaintTransaction createOrUpdateATransaction(ComplaintTransaction complaintTransactionId);

    void deleteATransaction(Long complainStateId);
}
