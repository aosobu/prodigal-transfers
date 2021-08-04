package Complaint.model;

import Complaint.enums.ApprovalStatus;
import Complaint.enums.TransferRecallType;
import com.teamapt.makerchecker.model.MakerCheckerEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Complaint implements MakerCheckerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String beneficiaryBank;
    private String beneficiaryAccountNumber;
    private String beneficiaryName;
    private String beneficiaryBankCode;
    private String transferringBankCode;
    private String complaintReason;
    private BigDecimal amountToBeRecalled;
    private BigDecimal amountTransferred;
    private int approvalStatus;

    @Column(unique = true)
    private String trackingNumber;

    private Date authorisationOrDeclineDate;
    private String authoriserOrDeclinerBranch;
    private String authoriserOrDeclinerName;
    private String authoriserOrDeclinerStaffId;
    private String createdTime;
    private String updatedTime;

    // ASSIGNER & ASSIGNEE
    private String assignerStaffId;
    private String assignerName;
    private String assigneeStaffId;
    private String assigneeName;
    private Date assignedDate;

    // Resolution data
    private Date resolutionDate;
    private String resolvedBy;
    private String resolverStaffId;
    private String resolutionMessage;

    private boolean dirty = false;
    private boolean approved = false;

    private String recallType;
    private String branchCodeLogged;

    @Transient
    private String errorMessage;

    @OneToOne(fetch = FetchType.EAGER,
            orphanRemoval = true,
                cascade = CascadeType.PERSIST)
    private ComplaintCustomer complaintCustomer;

    @OneToOne(fetch = FetchType.EAGER,
            orphanRemoval = true,
             cascade = CascadeType.PERSIST)
    private ComplaintTransaction complaintTransaction;

    @OneToOne(fetch = FetchType.EAGER,
                orphanRemoval = true,
              cascade = CascadeType.PERSIST)
    private BranchUser branchUser;

    @OneToOne(fetch = FetchType.EAGER,
                orphanRemoval = true,
                cascade = CascadeType.PERSIST)
    private ComplaintState complaintState;

    @OneToOne(fetch = FetchType.EAGER,
             orphanRemoval = true,
             cascade = CascadeType.PERSIST)
    private ComplaintInstruction complaintInstruction;

    @Override
    public Boolean getDirty() {
        return dirty;
    }

    @Override
    public Boolean getApproved() {
        return approved;
    }
}
