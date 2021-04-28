package Complaint.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "complaint_transaction")
public class ComplaintTransaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pan;
    private String tranId;
    private String sessionId;
    private String narration;
    private String rrn;
    private String stan;
    private String terminalId;
    private String schemeCode;
    private boolean alat;
    private String currencyCode;
    private String sol;
    private boolean international;
    private BigDecimal amount;
    private Date transactionDate;
    private Date valueDate;

}
