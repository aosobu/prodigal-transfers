package Complaint.repository;

import Complaint.model.ComplaintCustomer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ComplaintCustomerRepositoryTest {

    @Autowired
    private ComplaintCustomerRepository complaintCustomerRepository;

    ComplaintCustomer customer;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Create a customer")
    void createSCustomer_thenSaveToDB() {

        ComplaintCustomer complaintCustomer = new ComplaintCustomer();

        complaintCustomer.setCustomerFirstName("FirstName");
        complaintCustomer.setCustomerLastName("LastName");
        complaintCustomer.setCustomerEmail("mail@mail.com");
        complaintCustomer.setCustomerMiddleName("MiddleName");
        complaintCustomer.setCustomerComplaintLocation("Lagos");
        complaintCustomer.setCustomerAccountBranch("Ikeja");
        complaintCustomer.setCustomerAccountName("AccName");
        complaintCustomer.setCustomerAccountBranch("Lagos");
        complaintCustomer.setCustomerAccountNumber("123456678");
        complaintCustomer.setCustomerAddressLine1("Address Line 1");
        complaintCustomer.setCustomerAddressLine2("Address Line 2");
        complaintCustomer.setCustomerAccountType("Regular");
        complaintCustomer.setCustomerAccountCurrency("USD");
        complaintCustomer.setCustomerConsultantName("Consultant");
        complaintCustomer.setCustomerCity("Lagos");
        complaintCustomer.setCustomerComplaintChannel("Physical");
        complaintCustomer.setCustomerPhoneNumber("08091234567");
        complaintCustomer.setCustomerPostalCode("23401");
        complaintCustomer.setCustomerState("Lagos");

        customer = complaintCustomerRepository.save(complaintCustomer);
        log.info("Customer has been created ---> " + customer);
        assertThat(customer.getId()).isNotNull();
    }

    @Test
    @DisplayName("Customer exists in DB")
    void findCustomerByIdInTheDB(){

        customer = complaintCustomerRepository.findById(1L).orElse(null);
        assertThat(customer).isNotNull();
        log.info("Test customer retrieved from the database ---> " + customer);
    }

    @Test
    @DisplayName("Customer does not exist in DB")
    void findThatCustomerByIdInTheDBIsNotPresent(){

        customer = complaintCustomerRepository.findById((long) 3).orElse(null);
        assertThat(customer).isNull();
        log.info("No test customer retrieved from the database with this ID");
    }

    @Test
    @DisplayName("Get all customers test")
    void whenFindAllCustomersIsCalled_thenReturnAllCustomersListTest(){

        List<ComplaintCustomer> customers = complaintCustomerRepository.findAll();
        assertThat(customers.size()).isEqualTo(1);
        log.info("All customers retrieved from the database --> "+ customers);
    }

    @Test
    @DisplayName("Update customer details test")
    void whenStateDetailsIsUpdated_thenUpdateInDB(){

        customer = complaintCustomerRepository.findById(1L).orElse(null);
        assertThat(customer).isNotNull();
        log.info("The customer exists in the database ---> " + customer);
        customer.setCustomerConsultantName("Other consultant");

        customer = complaintCustomerRepository.save(customer);
        assertThat(customer.getCustomerConsultantName()).isEqualTo("Other consultant");
    }

    @Test
    @DisplayName("Delete Customer Test")
    void whenDeleteIsCalled_thenDeleteCustomerTest(){

        List<ComplaintCustomer> customers = complaintCustomerRepository.findAll();
        assertThat(customers).isNotNull();
        assertThat(customers.size()).isEqualTo(2);

        ComplaintCustomer scapeState = complaintCustomerRepository.findById(2L).orElse(null);
        assertThat(scapeState).isNotNull();
        complaintCustomerRepository.deleteById(2L);

        customers = complaintCustomerRepository.findAll();
        assertThat(customers).isNotNull();
        assertThat(customers.size()).isEqualTo(1);
    }
}