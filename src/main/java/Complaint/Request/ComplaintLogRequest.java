package Complaint.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintLogRequest {
    private String customer_id;
    private String transactionId;
    private String complaint_message;
    private String createdBy;
    private String updatedBy;
    private String state;
}
