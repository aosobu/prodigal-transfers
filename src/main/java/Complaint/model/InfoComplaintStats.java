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

    private Long unassignedIntraComplaints;
    private Long assignedIntraComplaints;
    private Long unassignedInterComplaints;
    private Long assignedInterComplaints;
    private Long processingInterComplaints;
    private Long processingIntraComplaints;
    private Long branchComplaintsHistory;
    private Long resolvedCount;
}
