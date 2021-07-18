package Complaint.repository;

import Complaint.model.ComplaintState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintStateRepository extends JpaRepository<ComplaintState, Long> {
}
