package Complaint.service;

import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.TransferRecallType;
import Complaint.model.Branch;
import Complaint.model.InfoComplaintStats;
import Complaint.service.interfaces.ComplaintInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminComplaintInfoService implements ComplaintInfo {

    private ComplaintServiceImpl complaintService;

    private ComplaintInfoAbstraction complaintInfoAbstraction;

    @Override
    public InfoComplaintStats generateInfoStatistics(String staffId) {
        InfoComplaintStats stats = setInfoComplaintStats();
        return complaintInfoAbstraction.generateInfoStatistics(staffId, stats);
    }

    @Override
    public InfoComplaintStats computeGroupInfoComplaintStatistics(List<Branch> branches) {
        InfoComplaintStats stats = setInfoComplaintStats();

        stats.setTotalComplaints(complaintService.countAllComplaints());
        stats.setResolvedComplaints(complaintService.countAllByProcessingState(ComplaintProcessingState.RESOLVED.getValue()));
        stats.setInProcessingComplaints(complaintService.countAllByProcessingState(ComplaintProcessingState.PROCESSING.getValue()));
        stats.setUnassignedInterComplaints(complaintService.countAllByProcessingStateAndRecallType(ComplaintProcessingState.NEW.getValue(), TransferRecallType.INTER.getCode()));
        stats.setUnassignedIntraComplaints(complaintService.countAllByProcessingStateAndRecallType(ComplaintProcessingState.NEW.getValue(), TransferRecallType.INTRA.getCode()));

        return stats;
    }

    @Autowired
    public void setComplaintService(ComplaintServiceImpl complaintService) {
        this.complaintService = complaintService;
    }

    @Autowired
    public void setComplaintInfoAbstraction(ComplaintInfoAbstraction complaintInfoAbstraction) {
        this.complaintInfoAbstraction = complaintInfoAbstraction;
    }
}
