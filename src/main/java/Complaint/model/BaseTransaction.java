package Complaint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseTransaction {
    private String accountNumber;
    private String accountName;
    private BigDecimal amount;
    private String narration;
    private String sessionId;
    private String tranType;
    private String tranId;
    private String sol;
    private String pan;
    private String terminalId;
    private String description;
    private String rrn;
    private String stan;
    private String currencyCode;

    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Temporal(TemporalType.DATE)
    private Date valueDate;

    private boolean international;
}
