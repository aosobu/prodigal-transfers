package Complaint.service;

import Complaint.model.BranchUser;
import Complaint.repository.dao.FinacleDao;
import com.teamapt.exceptions.CosmosServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchUserService {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private FinacleDao finacleDao;

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

    @Autowired
    public void setFinacleDao(FinacleDao finacleDao) {
        this.finacleDao = finacleDao;
    }
}
