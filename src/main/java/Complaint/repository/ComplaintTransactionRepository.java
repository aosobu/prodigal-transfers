package Complaint.repository;

import Complaint.entity.ComplaintTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintTransactionRepository extends JpaRepository<ComplaintTransaction,Long> {
}
