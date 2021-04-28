package Complaint.apimodel.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintTransactionLogRequest {
    private String transactionId;
    private String transfer_type;
    private String recall_reason;
}
