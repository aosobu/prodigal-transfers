package Complaint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private String customerEmail;
    private String customerPhoneNumber;
    private String customerAccountName;
    private String customerAccountNumber;
    private String customerFirstName;
    private String customerMiddleName;
    private String customerLastName;
    private String customerConsultantName;
    private String customerAccountBranch;
    private String customerClientType;
    private String customerAccountType;
    private String customerAccountCurrency;
    private String customerAddressLine1;
    private String customerAddressLine2;
    private String customerCity;
    private String customerState;
    private String customerCountry;
    private String customerPostalCode;
    private String customerComplaintChannel;
    private String customerComplaintLocation;
}
