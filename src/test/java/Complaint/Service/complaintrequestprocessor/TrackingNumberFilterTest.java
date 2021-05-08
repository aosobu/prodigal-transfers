package Complaint.Service.complaintrequestprocessor;

import Complaint.enums.ApprovalStatus;
import Complaint.model.Complaint;
import Complaint.model.ComplaintTransaction;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class TrackingNumberFilterTest {

    Complaint complaint = new Complaint();

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Generate random string test")
    public void generateRandomStringTest() {

        String trackingNumber = "";
        assertThat(trackingNumber).isNotNull();

        int length = 4;
        String generatedLetters = RandomStringUtils.random(length, true, false);
        String generatedNumbers = RandomStringUtils.random(length, false, true);
        trackingNumber = generatedNumbers + generatedLetters.toUpperCase();

        complaint.setTrackingNumber(trackingNumber);
        assertThat(complaint.getTrackingNumber()).isNotNull();
        assertThat(complaint.getTrackingNumber()).isEqualTo(trackingNumber);
    }

    @Test
    @DisplayName("Update approval status")
    void checkThatApprovalStatusIsDefaultNull() {
        assertThat(complaint.getApprovalStatus()).isNull();
    }

    @Test
    @DisplayName("Update approval status")
    void checkThatApprovalStatusCanBeSetToDeclined() {

        complaint.setApprovalStatus(ApprovalStatus.DECLINED);
        System.out.println(complaint.getApprovalStatus());
        assertThat(complaint.getApprovalStatus()).isEqualTo(ApprovalStatus.DECLINED);
    }

    @Test
    @DisplayName("Update approval status")
    void updateApprovalFromNullOrDeclinedToApproved() {
        System.out.println(complaint.getApprovalStatus());

        if (complaint.getApprovalStatus() == null || complaint.getApprovalStatus().equals(ApprovalStatus.DECLINED)){
            complaint.setApprovalStatus(ApprovalStatus.APPROVED);
        }
        System.out.println(complaint.getApprovalStatus());
    }

    @Test
    @DisplayName("Duplicate check test")
    void checkForDuplicateComplaintInTheList() {
        assertThat(complaint.getTransaction()).isNull();

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

        complaint.setTransaction(complaintTransaction);
        System.out.println(complaint.getTransaction().getRrn());
        assertThat(complaint.getTransaction().getRrn()).isNotNull();
    }
}