package Complaint.repository;

import Complaint.model.Complaint;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ComplaintRepository extends JpaRepository<Complaint, Long>, JpaSpecificationExecutor {
    Long countAllByBranchUserStaffIdAndComplaintStateProcessingStateAndRecallType(String staffId, Long processingState, String recallType);
}
