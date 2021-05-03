package Complaint.model;

import Complaint.enums.ApprovalStatus;
import Complaint.enums.TransferRecallType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String BeneficiaryBank;

    private String BeneficiaryAccountNumber;

    private String TransferringBank;

    private TransferRecallType recallType;

    private String complaintReason;

    private String CustomerInstruction;

    private ApprovalStatus approvalStatus;

    private String TrackingNumber;

    private Date authorisationDate;

    private String authoriserBranch;

    private String authoriserName;

    private String authoriserStaffId;

    private String createdTime;

    private String updatedTime;

    @OneToOne
    private ComplaintCustomer customer;

    @OneToOne
    private ComplaintTransaction transaction;

    @OneToOne
    private Actor actor;

    @OneToOne
    private ComplaintState complaintState;
}
