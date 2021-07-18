package Complaint.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties
public class Transaction {

    private boolean selected;
    private ComplaintTransaction alreadyLoggedComplaint;
    private boolean passed120Days;
    private boolean onus;
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
    private Date transactionDate;
    private Date valueDate;
    private boolean international;
}
