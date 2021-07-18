package Complaint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int processingState;
    private Date timeEmailToBeneficiaryBankSent;
    private Date timeEmailReceivedFromBeneficiaryBankSent;
    private String messageSentToBeneficiaryBank;
    private String messageReceivedFromBeneficiaryBank;
    private Date timeEmailToCustomerSent;
    private Date timeAcknowledgementFromCustomerReceived;
    private Date resolvedTime;
    private Date timeLienPlace;
    private Boolean isLienPlaced;
    private Boolean isMailSentToBeneficiaryBank;
    private Boolean isBeneficiaryBankAcknowledgmentReceived;
    private Boolean isMailSentToCustomer;
    private Boolean isCustomerAcknowledgmentReceived;
    private Boolean isMailSentToBankStaff;
    private Boolean isComplaintResolved;
}
