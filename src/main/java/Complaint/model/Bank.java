package Complaint.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankName;
    private String bankCode;
    private String bankAbbreviation;
    private String email;

    public Bank(String bankName, String bankCode, String bankAbbreviation) {
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.bankAbbreviation = bankAbbreviation;
    }
}
