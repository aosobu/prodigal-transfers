package Complaint.Repository;

import Complaint.Entity.TransactionComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintTransactionRepo extends JpaRepository<TransactionComplaint, Long> {

}
