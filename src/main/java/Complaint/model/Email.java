package Complaint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
public class Email {

    private Long id;
    private List<String> emailList;
    private String bankName;
    private String bankCode;
}
