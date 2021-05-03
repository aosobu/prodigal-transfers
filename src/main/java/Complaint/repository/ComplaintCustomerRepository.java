package Complaint.repository;

import Complaint.model.ComplaintCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintCustomerRepository extends JpaRepository<ComplaintCustomer, Long> {
}
