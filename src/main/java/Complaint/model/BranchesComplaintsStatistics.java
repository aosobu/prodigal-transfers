package Complaint.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchesComplaintsStatistics {

    private Long total;
    private Long totalResolvedInter;
    private Long totalResolvedIntra;
    private Long totalUnassignedInter;
    private Long totalUnassignedIntra;
    private Long totalPendingInter;
    private Long totalPendingIntra;
}
