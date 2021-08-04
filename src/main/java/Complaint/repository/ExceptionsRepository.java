package Complaint.repository;

import Complaint.model.Exceptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionsRepository extends JpaRepository<Exceptions, Long> {
}
