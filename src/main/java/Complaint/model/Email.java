package Complaint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Email {

    private Long id;
    private String emailList;
    private String bankName;
    private String bankCode;
}
