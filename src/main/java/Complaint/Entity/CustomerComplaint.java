package Complaint.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_complaint")
public class CustomerComplaint implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="customer_id", nullable = false)
    private String customer_id;
    @Column(name = "complaint_message")
    private String complaint_message;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "complaint_state")
    private String complaintState;

    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name ="customer_complaint_transaction", joinColumns = @JoinColumn(name="cust_id"),
            inverseJoinColumns = @JoinColumn(name="tid"))
    private Set<TransactionComplaint> complaint_transaction = new HashSet<>();*/
}
