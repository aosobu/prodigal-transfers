package Complaint.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="complaint")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "complaint_customer_id")
    private ComplaintCustomer complaintCustomer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "complaint_transaction_id")
    private ComplaintTransaction complaintTransaction;
}
