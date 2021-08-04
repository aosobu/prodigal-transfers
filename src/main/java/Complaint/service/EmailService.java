package Complaint.service;

import Complaint.model.Bank;
import Complaint.model.Complaint;
import com.teamapt.email.model.EmailNotification;
import com.teamapt.email.model.EmailNotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.teamapt.email.service.EmailNotificationService;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class EmailService {

    private Logger logger = LoggerFactory.getLogger(EmailService.class);
    private EmailNotificationService emailNotificationService = new EmailNotificationService("");

    public boolean sendEmailMessages(Complaint complaint, Set<String> recipients) throws Exception {
        return sendEmail(complaint, recipients);
    }

    private boolean sendEmail(Complaint complaint, Set<String> recipients) throws Exception {
        boolean status;
        try {

            String emailMessage = ""; // create email message

            if (complaint != null) {
                if (!StringUtils.hasText(complaint.getComplaintState().getMessageSentToBeneficiaryBank())) {
                        File emailTemplateFile = new File("email/request-transfer-recall.html");

                        StringBuilder emailTemplateBuilder = new StringBuilder("");

                        try (Scanner scanner = new Scanner(emailTemplateFile)) {
                            while (scanner.hasNextLine()) {
                                String line = scanner.nextLine();
                                emailTemplateBuilder.append(line).append("\n");
                            }
                            scanner.close();
                        } catch (IOException e) {
                            logger.error(e.getMessage());
                        }

                        String emailTemplateString = emailTemplateBuilder.toString();
                        emailTemplateString = emailTemplateString.replace("<email-body>", emailMessage);

                        EmailNotification notification = new EmailNotification();
                        notification.setSender("info@fidelitybank.ng");
                        notification.setNotificationType(EmailNotificationType.Html);
                        notification.setBody(emailTemplateString);
                        notification.setSubject("Fidelity Customer Support");

                        notification.setRecipients(new ArrayList<>(recipients));
                        emailNotificationService.sendEmailNotification(notification);
                }
            }
            status = true;

        } catch (Exception e) {
            logger.error("Error sending Email to " + recipients.toString() + ": " + e.getMessage());
            status = false;
        }
        return status;
    }

//    @Autowired
//    public void setEmailNotificationService(EmailNotificationService emailNotificationService) {
//        this.emailNotificationService = emailNotificationService;
//    }
}
