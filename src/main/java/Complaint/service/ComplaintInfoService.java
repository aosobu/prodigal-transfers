package Complaint.service;

import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.TransferRecallType;
import Complaint.model.Branch;
import Complaint.model.BranchStatDateRange;
import Complaint.model.BranchesComplaintsStatistics;
import Complaint.model.InfoComplaintStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ComplaintInfoService {

    private ComplaintServiceImpl complaintService;

    @Value("+${default.statistics.passed.days}")
    private String passedDaysString;

    public InfoComplaintStats computeUserInfoComplaintStatistics(String staffId){
        InfoComplaintStats stats = new InfoComplaintStats();
        stats.setUnassignedInterComplaints(0l);
        stats.setAssignedInterComplaints(0l);
        stats.setResolvedCount(0l);
        stats.setBranchComplaintsHistory(0l);
        stats.setUnassignedIntraComplaints(0l);
        stats.setProcessingInterComplaints(0l);
        stats.setProcessingIntraComplaints(0l);
        stats.setUnassignedInterComplaints(0l);

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

    public InfoComplaintStats computeGroupInfoComplaintStatistics(List<Branch> branches) {
        InfoComplaintStats stats = new InfoComplaintStats();
        stats.setBranchComplaintsHistory(0l);
        stats.setResolvedCount(0l);
        stats.setProcessingIntraComplaints(0l);
        stats.setProcessingInterComplaints(0l);
        stats.setUnassignedInterComplaints(0l);
        stats.setAssignedIntraComplaints(0l);
        stats.setAssignedInterComplaints(0l);
        stats.setUnassignedIntraComplaints(0l);
        stats.setBranchComplaintsHistory(0l);

        for (Branch branch : branches) {
            stats.setResolvedCount(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branch.getBranchCode(),
                    ComplaintProcessingState.RESOLVED.getValue(), TransferRecallType.INTRA.getCode()) +
                    complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branch.getBranchCode(),
                            ComplaintProcessingState.RESOLVED.getValue(), TransferRecallType.INTER.getCode()));

            stats.setAssignedInterComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branch.getBranchCode(),
                    ComplaintProcessingState.ASSIGNED.getValue(), TransferRecallType.INTER.getCode()));

            stats.setAssignedIntraComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branch.getBranchCode(),
                    ComplaintProcessingState.ASSIGNED.getValue(), TransferRecallType.INTRA.getCode()));

            stats.setUnassignedInterComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branch.getBranchCode(),
                    ComplaintProcessingState.NEW.getValue(), TransferRecallType.INTER.getCode()));

            stats.setUnassignedIntraComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branch.getBranchCode(),
                    ComplaintProcessingState.NEW.getValue(), TransferRecallType.INTRA.getCode()));

            stats.setProcessingIntraComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branch.getBranchCode(),
                    ComplaintProcessingState.PROCESSING.getValue(), TransferRecallType.INTRA.getCode()));

            stats.setProcessingInterComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branch.getBranchCode(),
                    ComplaintProcessingState.PROCESSING.getValue(), TransferRecallType.INTER.getCode()));

            stats.setProcessingIntraComplaints(complaintService.getComplaintsByBranchCodeAndProcessingStateAndRecallType(branch.getBranchCode(),
                    ComplaintProcessingState.PROCESSING.getValue(), TransferRecallType.INTRA.getCode()));
        }

        //stats.setBranchComplaintsHistory();
        return stats;
    }

    public BranchesComplaintsStatistics getBranchesComplaintsStatistics(BranchStatDateRange branchStatDateRange) throws ParseException {
        DateFormat formSdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date start;
        Date end = new Date();
        String startDateAsString = "", endDateAsString = "";

        Calendar c = Calendar.getInstance();

        if (branchStatDateRange.getDateRange().equals("")) {
            c.add(Calendar.DATE, -1 * Integer.parseInt(passedDaysString));
            start = c.getTime();
            startDateAsString = dateFormat.format(start);
            endDateAsString = dateFormat.format(end);
        } else {
            // Parse start and end dates
            String dateRange = branchStatDateRange.getDateRange();
            String startDateString = dateRange.split(":")[0].trim();
            String endDateString = dateRange.split(":")[1].trim();

            start = formSdf.parse(startDateString);
            end = formSdf.parse(endDateString);
            c.setTime(end);
            c.set(Calendar.HOUR_OF_DAY, c.getMaximum(Calendar.HOUR_OF_DAY));
            c.set(Calendar.MINUTE, c.getMaximum(Calendar.MINUTE));
            c.set(Calendar.SECOND, c.getMaximum(Calendar.SECOND));
            c.set(Calendar.MILLISECOND, c.getMaximum(Calendar.MILLISECOND));
            end = c.getTime();
            endDateAsString = dateFormat.format(end);
        }

        BranchesComplaintsStatistics statistics = new BranchesComplaintsStatistics();
        statistics.setTotalPendingInter(0l);
        statistics.setTotalPendingIntra(0l);
        statistics.setTotal(0l);
        statistics.setTotalResolvedInter(0l);
        statistics.setTotalResolvedIntra(0l);
        statistics.setTotalUnassignedInter(0l);
        statistics.setTotalUnassignedIntra(0l);


        for (Branch branch : branchStatDateRange.getBranches()) {

            String abranch = branch.getBranchCode();

            statistics.setTotal(statistics.getTotal() +
                    complaintService.getCountByBranchCodeLoggedAndDateRange(
                            abranch,
                            startDateAsString, endDateAsString));

            statistics.setTotalPendingInter(complaintService.getCountByBranchCodeLoggedAndComplaintStateAndRecallTypeAndDateRange(abranch,
                    ComplaintProcessingState.PROCESSING.getValue(), TransferRecallType.INTER.getCode(), startDateAsString, endDateAsString));

            statistics.setTotalPendingIntra(complaintService.getCountByBranchCodeLoggedAndComplaintStateAndRecallTypeAndDateRange(abranch,
                    ComplaintProcessingState.PROCESSING.getValue(), TransferRecallType.INTRA.getCode(), startDateAsString, endDateAsString));

            statistics.setTotalResolvedInter(complaintService.getCountByBranchCodeLoggedAndComplaintStateAndRecallTypeAndDateRange(abranch,
                    ComplaintProcessingState.RESOLVED.getValue(), TransferRecallType.INTER.getCode(), startDateAsString, endDateAsString));

            statistics.setTotalResolvedIntra(complaintService.getCountByBranchCodeLoggedAndComplaintStateAndRecallTypeAndDateRange(abranch,
                    ComplaintProcessingState.RESOLVED.getValue(), TransferRecallType.INTRA.getCode(), startDateAsString, endDateAsString));

            statistics.setTotalResolvedInter(complaintService.getCountByBranchCodeLoggedAndComplaintStateAndRecallTypeAndDateRange(abranch,
                    ComplaintProcessingState.NEW.getValue(), TransferRecallType.INTER.getCode(), startDateAsString, endDateAsString));

            statistics.setTotalResolvedInter(complaintService.getCountByBranchCodeLoggedAndComplaintStateAndRecallTypeAndDateRange(abranch,
                    ComplaintProcessingState.NEW.getValue(), TransferRecallType.INTRA.getCode(), startDateAsString, endDateAsString));
        }

        return statistics;
    }

    @Autowired
    public void setComplaintService(ComplaintServiceImpl complaintService) {
        this.complaintService = complaintService;
    }
}
