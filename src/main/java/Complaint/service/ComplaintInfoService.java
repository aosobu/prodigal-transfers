package Complaint.service;

import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.TransferRecallType;
import Complaint.model.InfoComplaintStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintInfoService {

    private ComplaintServiceImpl complaintService;

    public InfoComplaintStats computeUserInfoComplaintStatistics(String staffId){
        InfoComplaintStats stats = new InfoComplaintStats();

        stats.setAssignedInterComplaints(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId,
                                 ComplaintProcessingState.ASSIGNED.getValue(), TransferRecallType.INTER.getCode()));

        stats.setAssignedIntraComplaints(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId,
                 ComplaintProcessingState.ASSIGNED.getValue(), TransferRecallType.INTRA.getCode()));

        stats.setUnassignedInterComplaints(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId,
                  ComplaintProcessingState.NEW.getValue(), TransferRecallType.INTER.getCode()));

        stats.setUnassignedIntraComplaints(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId,
                  ComplaintProcessingState.NEW.getValue(), TransferRecallType.INTRA.getCode()));

        stats.setProcessingInterComplaints(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId,
                  ComplaintProcessingState.PROCESSING.getValue(), TransferRecallType.INTER.getCode()));

        stats.setProcessingIntraComplaints(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId,
                  ComplaintProcessingState.PROCESSING.getValue(), TransferRecallType.INTRA.getCode()));

        stats.setResolvedCount(complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId,
                                ComplaintProcessingState.RESOLVED.getValue(), TransferRecallType.INTRA.getCode()) +
                                    complaintService.getComplaintsByStaffIdAndProcessingStateAndRecallType(staffId,
                                        ComplaintProcessingState.RESOLVED.getValue(), TransferRecallType.INTER.getCode()) );

        //stats.setBranchComplaintsHistory();
        return stats;
    }

    @Autowired
    public void setComplaintService(ComplaintServiceImpl complaintService) {
        this.complaintService = complaintService;
    }
}
