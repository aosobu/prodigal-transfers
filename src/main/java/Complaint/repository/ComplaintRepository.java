package Complaint.repository;

import Complaint.model.Complaint;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Repository
@Transactional
public interface ComplaintRepository extends JpaRepository<Complaint, Long>, JpaSpecificationExecutor {
    Long countAllByBranchUserStaffIdAndComplaintStateProcessingStateAndRecallType(String staffId, Long processingState, String recallType);
    Long countAllByBranchCodeLoggedAndComplaintStateProcessingStateAndRecallType(String staffId, Long processingState, String recallType);
    Long countAllByBranchCodeLoggedAndCreatedTimeBetween(String branchCodeLogged, String start, String end);
    Long countAllByBranchCodeLoggedAndComplaintStateProcessingStateAndRecallTypeAndCreatedTimeBetween(String branchCodeLogged,
                                                                                                      Long complaintProcessingState,
                                                                                                      String recallType,
                                                                                                      String start, String end);
    Long countAllByBranchUserStaffId(String staffId);
    Long countAllByBranchUserBranchCode(String branchCode);
    Long countAllByComplaintStateProcessingState(Long processingState);
    Long countAllByComplaintStateProcessingStateAndRecallType(Long processingState, String recallType);
}
