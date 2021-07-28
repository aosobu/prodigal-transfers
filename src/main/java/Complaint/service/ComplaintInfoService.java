package Complaint.service;

import Complaint.enums.ComplaintProcessingState;
import Complaint.enums.Role;
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

    AdminComplaintInfoService adminComplaintInfoService;
    NonAdminComplaintInfoService nonAdminComplaintInfoService;

    public InfoComplaintStats computeUserInfoComplaintStatistics(String staffId, String role){
        if(role.equalsIgnoreCase(Role.ADMIN.getRole())){
            return adminComplaintInfoService.generateInfoStatistics(staffId);
        }else {
            return nonAdminComplaintInfoService.generateInfoStatistics(staffId);
        }
    }

    public InfoComplaintStats computeGroupInfoComplaintStatistics(String role, List<Branch> branches) {
        if(role.equalsIgnoreCase(Role.ADMIN.getRole())){
            return adminComplaintInfoService.computeGroupInfoComplaintStatistics(branches);
        }else {
            return nonAdminComplaintInfoService.computeGroupInfoComplaintStatistics(branches);
        }
    }

    @Autowired
    public void setAdminComplaintInfoService(AdminComplaintInfoService adminComplaintInfoService) {
        this.adminComplaintInfoService = adminComplaintInfoService;
    }

    @Autowired
    public void setNonAdminComplaintInfoService(NonAdminComplaintInfoService nonAdminComplaintInfoService) {
        this.nonAdminComplaintInfoService = nonAdminComplaintInfoService;
    }
}
