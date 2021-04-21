package Complaint.Repository;

import Complaint.Entity.TransactionComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintTransactionRepo extends JpaRepository<TransactionComplaint, Long> {
@Query(value = "SELECT * FROM complaint_transaction ct where ct.tid = :tid", nativeQuery = true)
    public TransactionComplaint findByTransID(@Param("tid") String tid);
}
