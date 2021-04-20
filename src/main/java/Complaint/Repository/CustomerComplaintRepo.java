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
            value = "INSERT INTO customer_complaint (cust_id, complaint_message, created_by, created_date, complaint_state, updated_by) values (:cust_id, :complaint_message, :created_by, :created_date, :complaint_state, :updated_by)",
            nativeQuery = true)
    public void saveComplaint(@Param("cust_id") String custid, @Param("complaint_message") String complaint_message, @Param("created_by") String created_by, @Param("created_date") Date created_date, @Param("complaint_state") String complaint_state, @Param("updated_by") String updated_by);
}
