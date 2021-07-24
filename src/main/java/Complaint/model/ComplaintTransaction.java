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
public class ComplaintTransaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
