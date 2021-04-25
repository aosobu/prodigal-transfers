package Complaint.Repository;

import Complaint.Entity.CustomerComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface CustomerComplaintRepo extends JpaRepository<CustomerComplaint,Long> {
    @Modifying
    @Query(
            value = "INSERT INTO customer_complaint (customer_id, complaint_message, created_by, created_date, complaint_state, updated_by) values (?1, ?2, ?3, ?4, ?5, ?6)",
            nativeQuery = true)
    public void saveComplaint(@Param("customer_id") String custid, @Param("complaint_message") String complaint_message, @Param("created_by") String created_by, @Param("created_date") Date created_date, @Param("complaint_state") String complaint_state, @Param("updated_by") String updated_by);
    @Query(value = "select cp.complaint_message, ct.transfer_type FROM customer_complaint cp join complaint_transaction ct on cp.customer_id = ct.customer_id and ct.transaction_id = ?1 limit 1",nativeQuery = true)
    public String getTransComplaintMessageAndTransferTypeByTid(@Param("transaction_id") String tid);
}

