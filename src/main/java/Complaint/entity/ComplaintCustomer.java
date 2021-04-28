package Complaint.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "complaint_customer")
public class ComplaintCustomer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "complaint_customer_id")
    private String customerId;

    private String customerEmail;

    private String customerPhoneNumber;

    private String customerOfficeNumber;

    private String customerAccountName;

    private String customerAccountNumber;

    private String customerFirstName;

    private String customerMiddleName;

    private String customerLastName;

    private String customerConsultantName;

    private String customerAccountBranch;

    @Column(length = 10)
    private String customerClientType;

    @Column(length = 20)
    private String customerAccountType;

    @Column(length = 3)

    private String customerAccountCurrency;

    private String customerAddressLine1;

    private String customerAddressLine2;

    @Column(length = 50)
    private String customerCity;

    @Column(length = 50)
    private String customerState;

    @Column(length = 3)
    private String customerCountry;

    @Column(length = 20)
    private String customerPostalCode;

    @Column(length = 20)

    private String customerComplaintChannel;

    private String customerComplaintLocation;
}
