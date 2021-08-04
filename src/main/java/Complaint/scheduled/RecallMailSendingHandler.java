package Complaint.scheduled;


import Complaint.enums.ApprovalStatus;
import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.TransferRecallType;
import Complaint.model.Bank;
import Complaint.model.Complaint;
import Complaint.model.Exceptions;
import Complaint.service.BankServiceImpl;
import Complaint.service.ComplaintServiceImpl;
import Complaint.service.EmailService;
import Complaint.service.ExceptionsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
public class RecallMailSendingHandler {

    private Logger logger = LoggerFactory.getLogger(RecallMailSendingHandler.class);
    private boolean isDev;
    private EmailService emailService;
    private BankServiceImpl bankService;
    private ComplaintServiceImpl complaintService;
    private String bankStaff;
    private ExceptionsServiceImpl exceptionsService;


    @Scheduled(fixedRateString = "${send.email.recall.interval}")
    public void sendEmailToBeneficiaryBank(){

        if(isDev)
            return;

        List<Complaint> interRecallComplaintList = complaintService.
                                                        getComplaintByProcessingStateAndRecallTypeAndApprovalStatus(
                                                                ComplaintProcessingState.NEW.getValue(),
                                                                TransferRecallType.INTER.getCode(),
                                                                ApprovalStatus.APPROVED.getApprovalStatus());

        final Stream<Complaint> complaintStream = interRecallComplaintList
                                                                        .stream()
                                                                        .filter(e -> e.getComplaintState().getRetrySendEmailCount() > 16)
                                                                        .filter(e -> !e.getComplaintState().getMessageSentToBeneficiaryBank().isEmpty() &&
                                                                                e.getComplaintState().getIsMailSentToBeneficiaryBank());


        complaintStream.forEach(complaint -> {
            Set<String> recipients = getRecipients(complaint);
            try {
                if(emailService.sendEmailMessages(complaint, recipients)){
                    try{
                        updateComplaint(complaint);
                        complaintService.saveComplaint(complaint);
                    }catch(Exception ex){
                        logger.info("Error Saving Complaint to Database {} ");
                    }
                }
            } catch (Exception e) {
                logger.info("Error Sending Email To Bank " + complaint.getBeneficiaryBankCode() + " for " + complaint.getTrackingNumber());
                exceptionsService.saveException(new Exceptions(0l, String.format("%s%s%s%s","Error Sending Email To Bank ",
                                                                complaint.getBeneficiaryBankCode(), " for ",complaint.getTrackingNumber()),
                                                                "RecallMailSendingHandler", new Date()));
            }
        });

    }

    private Set<String> getRecipients(Complaint complaint){
        Set<String> recipients = new HashSet<>();
        Bank bank = new Bank();

        if(complaint.getBeneficiaryBankCode() != null)
            bank = bankService.getBankByBankCode(complaint.getBeneficiaryBankCode());

        recipients.add(bank.getEmail());
        recipients.addAll(Arrays.asList(bankStaff.split(",")));

        return recipients;
    }

    private Complaint updateComplaint(Complaint complaint){
        try {
            complaint.getComplaintState().setMessageSentToBeneficiaryBank("Email has been sent to beneficiary bank");
            complaint.getComplaintState().setIsMailSentToBeneficiaryBank(true);
            complaint.getComplaintState().setIsMailSentToBankStaff(true);
            complaint.getComplaintState().setTimeEmailToBeneficiaryBankSent(new Date());
            complaint.getComplaintState().setProcessingState(ComplaintProcessingState.PROCESSING.getValue());
        }catch(Exception ex){
            logger.info("Error Updating Complaint With Tracking Number {} " + complaint.getTrackingNumber());
        }
        return complaint;
    }

    @Autowired
    public void setDev(@Value("${app.is.dev}") boolean dev) {
        isDev = dev;
    }

    @Autowired
    public void setBankStaff(@Value("${bank.email.staff}")  String bankStaff) {
        this.bankStaff = bankStaff;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setComplaintService(ComplaintServiceImpl complaintService) {
        this.complaintService = complaintService;
    }

    @Autowired
    public void setBankService(BankServiceImpl bankService) {
        this.bankService = bankService;
    }

    @Autowired
    public void setExceptionsService(ExceptionsServiceImpl exceptionsService) {
        this.exceptionsService = exceptionsService;
    }
}
