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

    private String Status;

    private Date resolvedTime;

    private Date timeLienPlace;

    private Date timeEmailToBeneficiarySent;

    private Date timeEmailReceivedFromBeneficiaryBankSent;

    private Date timeReceivedFromCustomer;

    private Boolean isMailSentToBeneficiaryBank;

    private Boolean isLienPlaced;

    private Boolean isCustomerAcknowledgmentReceived;

    private Boolean isMailSentToCustomer;

    private Boolean isMailSentToBankStaff;

    private Boolean isBeneficiaryBankAcknowledgmentReceived;

    private Boolean isComplaintResolved;
}
