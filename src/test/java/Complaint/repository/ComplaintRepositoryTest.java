//package Complaint.repository;
//
//import Complaint.enums.ApprovalStatus;
//import Complaint.enums.TransferRecallType;
//import Complaint.model.*;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Slf4j
//class ComplaintRepositoryTest {
//
//    @Autowired
//    private ComplaintRepository complaintRepository;
//
//    ComplaintRepository aComplaint;
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    @DisplayName("Create a customer")
//    void createComplaint_thenSaveToDB() {
//        Complaint complaint = new Complaint();
//
//        complaint.setComplaintReason("Complaint Reason");
//        complaint.setApprovalStatus(ApprovalStatus.APPROVED);
//        complaint.setAuthorisationDate(new Date());
//        complaint.setAuthoriserBranch("Branch A");
//        complaint.setAuthoriserName("Admin");
//        complaint.setAuthoriserStaffId("ID001");
//        complaint.setBeneficiaryBank("Bank B");
//        complaint.setCreatedTime("12:00");
//        complaint.setBeneficiaryAccountNumber("0987654321");
//        complaint.setCustomerInstruction("Instruction");
//        complaint.setRecallType(TransferRecallType.INTRA);
//        complaint.setTrackingNumber("AAA111");
//        complaint.setTransferringBank("Bank A");
//
//        Actor actor = new Actor();
//        actor.setId(1L);
//        actor.setDesk("333");
//        actor.setBranchCode("XYZ987");
//        actor.setStaffName("TestnameII");
//        actor.setStaffType("Contract");
//        actor.setStaffId("B321");
//
//        complaint.setActor(actor);
//
//        ComplaintTransaction complaintTransaction = new ComplaintTransaction();
//        complaintTransaction.setTransactionDate(new Date());
//        complaintTransaction.setAlat(true);
//        complaintTransaction.setAmount(BigDecimal.valueOf(12000));
//        complaintTransaction.setCurrencyCode("NGB");
//        complaintTransaction.setInternational(false);
//        complaintTransaction.setNarration("Bills payment");
//        complaintTransaction.setPan("PAN");
//        complaintTransaction.setRrn("RRN001");
//        complaintTransaction.setSchemeCode("SCH009");
//        complaintTransaction.setSol("SOL");
//        complaintTransaction.setSessionId("SID");
//        complaintTransaction.setStan("Stan");
//        complaintTransaction.setTerminalId("001");
//        complaintTransaction.setTranId("X12");
//        complaintTransaction.setValueDate(new Date());
//
//        complaint.setTransaction(complaintTransaction);
//
//        ComplaintCustomer complaintCustomer = new ComplaintCustomer();
//        complaintCustomer.setCustomerFirstName("FirstName");
//        complaintCustomer.setCustomerLastName("LastName");
//        complaintCustomer.setCustomerEmail("mail@mail.com");
//        complaintCustomer.setCustomerMiddleName("MiddleName");
//        complaintCustomer.setCustomerComplaintLocation("Lagos");
//        complaintCustomer.setCustomerAccountBranch("Ikeja");
//        complaintCustomer.setCustomerAccountName("AccName");
//        complaintCustomer.setCustomerAccountBranch("Lagos");
//        complaintCustomer.setCustomerAccountNumber("123456678");
//        complaintCustomer.setCustomerAddressLine1("Address Line 1");
//        complaintCustomer.setCustomerAddressLine2("Address Line 2");
//        complaintCustomer.setCustomerAccountType("Regular");
//        complaintCustomer.setCustomerAccountCurrency("USD");
//        complaintCustomer.setCustomerConsultantName("Consultant");
//        complaintCustomer.setCustomerCity("Lagos");
//        complaintCustomer.setCustomerComplaintChannel("Physical");
//        complaintCustomer.setCustomerPhoneNumber("08091234567");
//        complaintCustomer.setCustomerPostalCode("23401");
//        complaintCustomer.setCustomerState("Lagos");
//
//        complaint.setCustomer(complaintCustomer);
//
//        ComplaintState complaintState = new ComplaintState();
//        complaintState.setStatus("Progress");
//        complaintState.setIsComplaintResolved(true);
//        complaintState.setIsBeneficiaryBankAcknowledgmentReceived(true);
//        complaintState.setIsLienPlaced(true);
//        complaintState.setIsCustomerAcknowledgmentReceived(true);
//        complaintState.setIsMailSentToBankStaff(true);
//        complaintState.setIsMailSentToBeneficiaryBank(true);
//        complaintState.setResolvedTime(new Date());
//        complaintState.setTimeReceivedFromCustomer(new Date());
//        complaintState.setIsMailSentToCustomer(true);
//        complaintState.setTimeLienPlace(new Date());
//        complaintState.setTimeEmailReceivedFromBeneficiaryBankSent(new Date());
//        complaintState.setIsCustomerAcknowledgmentReceived(true);
//        complaintState.setTimeEmailToBeneficiarySent(new Date());
//
//        complaint.setComplaintState(complaintState);
//
//        complaint = complaintRepository.save(complaint);
//        log.info("Complaint has been created ---> " + complaint);
//        assertThat(complaint.getId()).isNotNull();
//    }
//}