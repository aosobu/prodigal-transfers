package Complaint.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExceptionsMailSendingHandler {

    private Logger logger = LoggerFactory.getLogger(ExceptionsMailSendingHandler.class);

    @Scheduled(fixedRateString = "${send.email.exceptions.interval}")
    public void sendEmailToBeneficiaryBank(){
        logger.info("Sending daily exceptions to Admin and Bank Staff");
    }
}
