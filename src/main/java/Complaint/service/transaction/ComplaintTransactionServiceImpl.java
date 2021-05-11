package Complaint.service.transaction;

import Complaint.model.ComplaintTransaction;
import Complaint.repository.ComplaintTransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ComplaintTransactionServiceImpl implements ComplaintTransactionService{

//    @Autowired
    private ComplaintTransactionRepository complaintTransactionRepository;

    @Override
    public List<ComplaintTransaction> getAllTransactions() {
        return complaintTransactionRepository.findAll();
    }

    @Override
    public ComplaintTransaction getATransaction(Long complaintTransactionId) {
        return complaintTransactionRepository.findById(complaintTransactionId).orElse(null);
    }

    @Override
    public ComplaintTransaction createOrUpdateATransaction(ComplaintTransaction complaintTransaction) {
        return complaintTransactionRepository.save(complaintTransaction);
    }

    @Override
    public void deleteATransaction(Long complainTransactionId) {
        complaintTransactionRepository.deleteById(complainTransactionId);
    }
}
