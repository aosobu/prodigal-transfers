package Complaint.service;

import Complaint.model.Branch;
import Complaint.model.BranchUser;
import Complaint.repository.dao.FinacleDao;
import Complaint.repository.dao.InfoPoolDao;
import com.teamapt.exceptions.CosmosServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchUserService {

    Logger logger = LoggerFactory.getLogger(getClass().getName());
    private FinacleDao finacleDao;
    private InfoPoolDao infoPoolDao;

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

    @Autowired
    public void setFinacleDao(FinacleDao finacleDao) {
        this.finacleDao = finacleDao;
    }

    @Autowired
    public void setInfoPoolDao(InfoPoolDao infoPoolDao) {
        this.infoPoolDao = infoPoolDao;
    }
}
