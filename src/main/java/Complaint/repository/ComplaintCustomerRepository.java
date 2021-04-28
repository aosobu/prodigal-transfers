package Complaint.repository;

import Complaint.entity.ComplaintCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintCustomerRepository extends JpaRepository<ComplaintCustomer,Long> {

}

