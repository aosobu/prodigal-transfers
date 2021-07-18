package Complaint.model.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AccountDateRange {
    private String accountNumber;
    private String dateRange;
}
