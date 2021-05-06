package Complaint.Service.complaintrequestprocessor;

import Complaint.model.Complaint;
import Complaint.model.ComplaintState;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MailSentToBeneficiaryBankFilterTest {

     Complaint complaint;

     ComplaintState complaintState;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Mail Sent update test")
    void updateMailSentToBankState() {

        if (complaintState == null)
            log.info("NULL");

    }
}