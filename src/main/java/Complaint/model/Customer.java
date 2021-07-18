package Complaint.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private String customerId;
    private String accountNumber;
    private String accountName;
    private String consultantName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String email;
    private String phone;
    private String officePhone;
    private String addressLine1;
    private String addressLine2;
    private String clientType;
    private String channel;
    private String subject;
    private String description;
    private String petitionerPrayer;
    private String solId;
    private String schemeCode;
    private String schemeType;
    private String accountCurrencyCode;
    private String accountType;
}
