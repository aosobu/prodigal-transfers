package Complaint.repository;

import Complaint.model.ComplaintTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ComplaintTransactionRepository extends JpaRepository<ComplaintTransaction, Long>, JpaSpecificationExecutor{
    List<ComplaintTransaction> findByAccountNumberAndTranIdAndAmountAndTransactionDate(String accountNumber, String tranId, BigDecimal amount, Date transactionDate);
}
