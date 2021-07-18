package Complaint.repository;

import Complaint.model.ComplaintInstruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerInstructionRepository  extends JpaRepository<ComplaintInstruction, Long>, JpaSpecificationExecutor {
}
