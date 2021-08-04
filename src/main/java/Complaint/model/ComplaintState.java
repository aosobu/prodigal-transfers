package Complaint.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComplaintState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long processingState = 0l;
    private Date timeEmailToBeneficiaryBankSent;
    private Date timeEmailReceivedFromBeneficiaryBankSent;
    private Date timeEmailToCustomerSent;
    private Date timeAcknowledgementFromCustomerReceived;
    private Date resolvedTime;
    private Date timeLienPlace;
    private String messageSentToBeneficiaryBank = "";
    private String messageReceivedFromBeneficiaryBank = "";
    private Boolean isLienPlaced = false;
    private Boolean isMailSentToBeneficiaryBank = false;
    private Boolean isBeneficiaryBankAcknowledgmentReceived = false;
    private Boolean isMailSentToCustomer = false;
    private Boolean isCustomerAcknowledgmentReceived = false;
    private Boolean isMailSentToBankStaff = false;
    private Boolean isComplaintResolved = false;
    private int retrySendEmailCount = 0;
    private int retryDebitAccountCount = 0;
    private Boolean isComplainantAccountCredited = false;
    private Boolean isDefendantAccountDebited = false;
    private BigDecimal amountCreditedToCustomer = new BigDecimal(0.0);
    private BigDecimal amountDebitedFromDefendat = new BigDecimal(0.0);
}
