package Complaint.repository;

import Complaint.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long>, JpaSpecificationExecutor {
    Bank findByBankCode(String bankCode);
}
