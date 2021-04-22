package Complaint.ApiRequestModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintTransLogRequest {
    private String transactionId;
    private String transfer_type;
    private String recall_reason;
}
