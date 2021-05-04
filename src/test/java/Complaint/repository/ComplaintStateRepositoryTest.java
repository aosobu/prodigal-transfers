package Complaint.repository;

import Complaint.model.Actor;
import Complaint.model.ComplaintState;
import Complaint.model.ComplaintTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ComplaintStateRepositoryTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    private ComplaintStateRepository complaintStateRepository;

    ComplaintState state;

    @Test
    @DisplayName("Create a state")
    void createStae_thenSaveToDB() {
        ComplaintState complaintState = new ComplaintState();

        complaintState.setStatus("Progress");
        complaintState.setIsComplaintResolved(true);
        complaintState.setIsBeneficiaryBankAcknowledgmentReceived(true);
        complaintState.setIsLienPlaced(true);
        complaintState.setIsCustomerAcknowledgmentReceived(true);
        complaintState.setIsMailSentToBankStaff(true);
        complaintState.setIsMailSentToBeneficiaryBank(true);
        complaintState.setResolvedTime(new Date());
        complaintState.setTimeReceivedFromCustomer(new Date());
        complaintState.setIsMailSentToCustomer(true);
        complaintState.setTimeLienPlace(new Date());
        complaintState.setTimeEmailReceivedFromBeneficiaryBankSent(new Date());
        complaintState.setIsCustomerAcknowledgmentReceived(true);
        complaintState.setTimeEmailToBeneficiarySent(new Date());

        state = complaintStateRepository.save(complaintState);
        log.info("State has been created ---> " + state);
        assertThat(state.getId()).isNotNull();
    }

    @Test
    @DisplayName("State exists in DB")
    void findStateByIdInTheDB(){

        state = complaintStateRepository.findById(1L).orElse(null);
        assertThat(state).isNotNull();
        log.info("Test state retrieved from the database ---> " + state);
    }

    @Test
    @DisplayName("State does not exist in DB")
    void findThatStateByIdInTheDBIsNotPresent(){

        state = complaintStateRepository.findById((long) 3).orElse(null);
        assertThat(state).isNull();
        log.info("No test state retrieved from the database with this ID");
    }

    @Test
    @DisplayName("Get all states test")
    void whenFindAllStatesIsCalled_thenReturnAllStatesListTest(){

        List<ComplaintState> states = complaintStateRepository.findAll();
        assertThat(states.size()).isEqualTo(1);
        log.info("All states retrieved from the database --> "+ states);
    }

    @Test
    @DisplayName("Update State details test")
    void whenStateDetailsIsUpdated_thenUpdateInDB(){

        state = complaintStateRepository.findById(1L).orElse(null);
        assertThat(state).isNotNull();
        log.info("The transaction exists in the database ---> " + state);
        state.setStatus("Pending");

        state = complaintStateRepository.save(state);
        assertThat(state.getStatus()).isEqualTo("Pending");
    }

    @Test
    @DisplayName("Delete State Test")
    void whenDeleteIsCalled_thenDeleteStateTest(){

        List<ComplaintState> states = complaintStateRepository.findAll();
        assertThat(states).isNotNull();
        assertThat(states.size()).isEqualTo(2);

        ComplaintState scapeState = complaintStateRepository.findById(2L).orElse(null);
        assertThat(scapeState).isNotNull();
        complaintStateRepository.deleteById(2L);

        states = complaintStateRepository.findAll();
        assertThat(states).isNotNull();
        assertThat(states.size()).isEqualTo(1);
    }
}