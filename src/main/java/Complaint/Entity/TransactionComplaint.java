package Complaint.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complaint_transaction")
public class TransactionComplaint implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="cust_id", nullable = false)
    private String customer_id;
    @Column(name="transaction_id", unique = true)
    private String transaction_id;
    @Column(name="transfer_type")
    private String transfer_type;
}
