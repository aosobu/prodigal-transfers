package Complaint.repository;

import Complaint.model.ComplaintState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintStateRepository extends JpaRepository<ComplaintState, Long> {
}
