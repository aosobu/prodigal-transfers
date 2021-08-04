package Complaint.service;

import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.TransferRecallType;
import Complaint.model.InfoComplaintStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ComplaintInfoAbstraction {

    private ComplaintServiceImpl complaintService;

    public InfoComplaintStats generateInfoStatistics(String staffId, InfoComplaintStats stats) {

        stats.setUnassignedIntraComplaints(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId, ComplaintProcessingState.NEW.getValue(),
                TransferRecallType.INTRA.getCode()));

        stats.setInProcessingComplaints(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId, ComplaintProcessingState.PROCESSING.getValue(),
                TransferRecallType.INTER.getCode()) + complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId, ComplaintProcessingState.PROCESSING.getValue(),
                TransferRecallType.INTRA.getCode()));

        stats.setResolvedComplaints(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId, ComplaintProcessingState.RESOLVED.getValue(),
                TransferRecallType.INTER.getCode()) + complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId, ComplaintProcessingState.RESOLVED.getValue(),
                TransferRecallType.INTRA.getCode()));

        stats.setUnassignedInterComplaints(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId, ComplaintProcessingState.NEW.getValue(),
                TransferRecallType.INTER.getCode()));

        stats.setTotalComplaints(complaintService.countAllComplaintsByStaffId(staffId));

        return stats;
    }

    @Autowired
    public void setComplaintService(ComplaintServiceImpl complaintService) {
        this.complaintService = complaintService;
    }
}
