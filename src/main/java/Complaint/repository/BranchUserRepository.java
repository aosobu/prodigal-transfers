package Complaint.repository;

import Complaint.model.BranchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchUserRepository extends JpaRepository<BranchUser, Long> {
}
