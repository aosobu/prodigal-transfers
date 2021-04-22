package Complaint.ApiRequestModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintLogRequest {
    private String customer_id;
    private String transactionId;
    private String transferType;
    private String complaint_message;
    private String createdBy;
    private String updatedBy;
    private String state;
}