package Complaint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintTransaction {

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
