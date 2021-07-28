package Complaint.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoComplaintStats {

    private Long totalComplaints;
    private Long unassignedInterComplaints;
    private Long unassignedIntraComplaints;
    private Long inProcessingComplaints;
    private Long resolvedComplaints;
    private Long totalLoggedInCurrentDay;
}
