package Complaint.repository;

import Complaint.model.Actor;
import Complaint.model.ComplaintTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ComplaintTransactionRepositoryTest {

    @Autowired
    private ComplaintTransactionRepository complaintTransactionRepository;

    ComplaintTransaction transaction;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Create an transaction")
    void createTransaction_thenSaveToDB() {

        ComplaintTransaction complaintTransaction = new ComplaintTransaction();
        complaintTransaction.setTransactionDate(new Date());
        complaintTransaction.setAlat(true);
        complaintTransaction.setAmount(BigDecimal.valueOf(12000));
        complaintTransaction.setCurrencyCode("NGB");
        complaintTransaction.setInternational(false);
        complaintTransaction.setNarration("Bills payment");
        complaintTransaction.setPan("PAN");
        complaintTransaction.setRrn("RRN001");
        complaintTransaction.setSchemeCode("SCH009");
        complaintTransaction.setSol("SOL");
        complaintTransaction.setSessionId("SID");
        complaintTransaction.setStan("Stan");
        complaintTransaction.setTerminalId("001");
        complaintTransaction.setTranId("X12");
        complaintTransaction.setValueDate(new Date());

        transaction = complaintTransactionRepository.save(complaintTransaction);
        log.info("Transaction has been created ---> " + transaction);
        assertThat(transaction.getId()).isNotNull();
    }

    @Test
    @DisplayName("Transaction exists in DB")
    void findTransactionByIdInTheDB(){

        transaction = complaintTransactionRepository.findById(1L).orElse(null);
        assertThat(transaction).isNotNull();
        log.info("Test transaction retrieved from the database ---> " + transaction);
    }

    @Test
    @DisplayName("Transaction does not exist in DB")
    void findThatTransactionByIdInTheDBIsNotPresent(){

        transaction = complaintTransactionRepository.findById((long) 4).orElse(null);
        assertThat(transaction).isNull();
        log.info("No test transaction retrieved from the database with this ID");
    }

    @Test
    @DisplayName("Get all transactions test")
    void whenFindAllTransactionssIsCalled_thenReturnAllTransactionsListTest(){

        List<ComplaintTransaction> transactions = complaintTransactionRepository.findAll();
        assertThat(transactions.size()).isEqualTo(2);
        log.info("All transactions retrieved from the database --> "+ transactions);
    }

    @Test
    @DisplayName("Update transaction details test")
    void whenTransactionDetailsIsUpdated_thenUpdateInDB(){

        transaction = complaintTransactionRepository.findById(2L).orElse(null);
        assertThat(transaction).isNotNull();
        log.info("The transaction exists in the database ---> " + transaction);
        transaction.setRrn("String RRN");

        transaction = complaintTransactionRepository.save(transaction);
        assertThat(transaction.getRrn()).isEqualTo("String RRN");
    }

    @Test
    @DisplayName("Delete Transaction Test")
    void whenDeleteIsCalled_thenDeleteTransactionTest(){

        List<ComplaintTransaction> transactions = complaintTransactionRepository.findAll();
        assertThat(transactions).isNotNull();
        assertThat(transactions.size()).isEqualTo(2);

        ComplaintTransaction scapeTransaction = complaintTransactionRepository.findById(2L).orElse(null);
        assertThat(scapeTransaction).isNotNull();
        complaintTransactionRepository.deleteById(2L);

        transactions = complaintTransactionRepository.findAll();
        assertThat(transactions).isNotNull();
        assertThat(transactions.size()).isEqualTo(1);
    }
}