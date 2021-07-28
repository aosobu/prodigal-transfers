package Complaint.service;

import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.TransferRecallType;
import Complaint.model.Branch;
import Complaint.model.InfoComplaintStats;
import Complaint.service.interfaces.ComplaintInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NonAdminComplaintInfoService implements ComplaintInfo {

    private ComplaintServiceImpl complaintService;

    private ComplaintInfoAbstraction complaintInfoAbstraction;

    private String passedDaysString;

    @Override
    public InfoComplaintStats generateInfoStatistics(String staffId) {
        InfoComplaintStats stats = setInfoComplaintStats();
        return complaintInfoAbstraction.generateInfoStatistics(staffId, stats);
    }

    @Override
    public InfoComplaintStats computeGroupInfoComplaintStatistics(List<Branch> branches) {
        InfoComplaintStats stats = setInfoComplaintStats();

        for (Branch branch : branches) {
            String branchCode = branch.getBranchCode();

            stats.setUnassignedInterComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branchCode,
                    ComplaintProcessingState.NEW.getValue(), TransferRecallType.INTER.getCode()));

            stats.setInProcessingComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branchCode,
                    ComplaintProcessingState.PROCESSING.getValue(), TransferRecallType.INTRA.getCode()) +
                    complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branchCode,
                            ComplaintProcessingState.PROCESSING.getValue(), TransferRecallType.INTER.getCode()));

            stats.setResolvedComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branchCode,
                    ComplaintProcessingState.RESOLVED.getValue(), TransferRecallType.INTRA.getCode()) +
                    complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branchCode,
                            ComplaintProcessingState.RESOLVED.getValue(), TransferRecallType.INTER.getCode()));

            stats.setTotalComplaints(complaintService.countAllComplaintsByBranchCode(branchCode));

            stats.setUnassignedIntraComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branchCode,
                    ComplaintProcessingState.NEW.getValue(), TransferRecallType.INTRA.getCode()));
        }
        return stats;
    }

    @Autowired
    public void setComplaintService(ComplaintServiceImpl complaintService) {
        this.complaintService = complaintService;
    }

    @Autowired
    public void setPassedDaysString(@Value("+${default.statistics.passed.days}") String passedDaysString) {
        this.passedDaysString = passedDaysString;
    }

    @Autowired
    public void setComplaintInfoAbstraction(ComplaintInfoAbstraction complaintInfoAbstraction) {
        this.complaintInfoAbstraction = complaintInfoAbstraction;
    }
}
