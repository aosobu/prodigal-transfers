package Complaint.service.interfaces;

import Complaint.model.Branch;
import Complaint.model.InfoComplaintStats;

import java.util.List;

public interface ComplaintInfo {

    InfoComplaintStats generateInfoStatistics(String staffId);

    InfoComplaintStats computeGroupInfoComplaintStatistics(List<Branch> branches);

    default InfoComplaintStats setInfoComplaintStats(){
        InfoComplaintStats stats = new InfoComplaintStats();
        stats.setUnassignedInterComplaints(0l);
        stats.setInProcessingComplaints(0l);
        stats.setResolvedComplaints(0l);
        stats.setTotalComplaints(0l);
        stats.setUnassignedIntraComplaints(0l);
        stats.setTotalLoggedInCurrentDay(0l);
        return stats;
    }
}
