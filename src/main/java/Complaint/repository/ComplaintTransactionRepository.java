package Complaint.repository;

import Complaint.model.ComplaintTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintTransactionRepository extends JpaRepository<ComplaintTransaction, Long> {
}