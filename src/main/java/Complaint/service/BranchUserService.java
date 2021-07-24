package Complaint.service;

import Complaint.model.Branch;
import Complaint.model.BranchUser;
import Complaint.model.Staff;
import Complaint.repository.dao.FinacleDao;
import Complaint.repository.dao.InfoPoolDao;
import com.teamapt.exceptions.CosmosServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BranchUserService {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private FinacleDao finacleDao;

    private InfoPoolDao infoPoolDao;

    private StaffServiceImpl staffServiceImpl;

    public BranchUser getUserDetails(String staffId) throws CosmosServiceException {
        try {
            logger.debug("Fetching user detils from finalce");
            BranchUser branchUser = finacleDao.getBankUserByStaffId(staffId);
            if(branchUser == null) {
                throw new CosmosServiceException("Problem Getting User Information.");
            }
            logger.debug("Stop fetching user deatils");
            return branchUser;
        } catch (Exception e){
            e.printStackTrace();
            throw new CosmosServiceException("Problem Getting User Information.");
        }
    }

    public List<Branch> getUserBranches(String staffId) throws CosmosServiceException {
        BranchUser branchUser;
        try {
            logger.debug("Start fetching user deatils from finalce");
            branchUser = finacleDao.getBankUserByStaffId(staffId);
            logger.debug("Stop fetching user deatils");
        } catch (Exception e){
            e.printStackTrace();
            throw new CosmosServiceException("Problem Getting User Information.");
        }

        Branch branch;
        try {
            logger.debug("Start fetching branch info from info pool");
            branch = infoPoolDao.getBranchInformation(branchUser.getBranchCode());
            logger.debug("End fetching branch info from info pool");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CosmosServiceException("Problem Getting Branch info from Infopool.");
        }

        if (branch.getBranchCode().equals("000")) {
            branch.setBranchName("Issuer Dispute");
            branch.setBranchClassification("Back Office");
        }
        List<Branch> branchList = new ArrayList<>();
        branchList.add(branch);
        return branchList;
    }

    public List<BranchUser> getBranchUsers(List<String> branchCodes) throws CosmosServiceException {

        String branchCodesForQuery = "";
        for (String branchCode : branchCodes) {
            branchCodesForQuery += "'" + branchCode + "',";
        }

        branchCodesForQuery = branchCodesForQuery.substring(0, branchCodesForQuery.length() - 1);
        List<BranchUser> branchUsers = finacleDao.getBranchUsers(branchCodesForQuery);

        // Remove TrueServe Staff
        List<Staff> staffs = staffServiceImpl.getAllStaff();
        HashMap<String, String> staffMap = new HashMap<>();
        for (Staff staff : staffs)
            staffMap.put(staff.getStaffId(), staff.getBranchCode());

        // Create new branch list; exclude true serve officials
        List<BranchUser> newBranchUsers = new ArrayList<>();
        for (BranchUser branchUser : branchUsers) {
            if (staffMap.get(branchUser.getStaffId()) == null)
                newBranchUsers.add(branchUser);
        }

        return newBranchUsers;
    }

    @Autowired
    public void setFinacleDao(FinacleDao finacleDao) {
        this.finacleDao = finacleDao;
    }

    @Autowired
    public void setInfoPoolDao(InfoPoolDao infoPoolDao) {
        this.infoPoolDao = infoPoolDao;
    }

    @Autowired
    public void setStaffServiceImpl(StaffServiceImpl staffServiceImpl) {
        this.staffServiceImpl = staffServiceImpl;
    }
}
