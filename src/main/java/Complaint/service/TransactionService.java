package Complaint.service;

import Complaint.model.Transaction;
import Complaint.model.api.AccountDateRange;
import Complaint.repository.dao.FinacleDao;
import com.teamapt.exceptions.CosmosServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    private FinacleDao finacleDao;

    public List<Transaction> getComplaintTransactions(AccountDateRange accountDateRange) throws CosmosServiceException {
        try {
            List<Transaction> complaintTransactions = getHTDTransactions(accountDateRange);
            complaintTransactions.addAll(getDTDTransactions(accountDateRange.getAccountNumber()));
            complaintTransactions = removeSameDayTransactions(complaintTransactions);

            return complaintTransactions;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CosmosServiceException(e.getMessage());
        }
    }

    private List<Transaction> getHTDTransactions(AccountDateRange accountDateRange) throws CosmosServiceException {
        try {
            return finacleDao.findHTDTxnsWithAccDateRange(accountDateRange);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CosmosServiceException("Problem fetching transactions (Daily)");
        }
    }

    private List<Transaction> getDTDTransactions(String accountNumber) throws CosmosServiceException {
        try {
            return finacleDao.findDTDTxns(accountNumber);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CosmosServiceException("Problem fetching transactions (Daily)");
        }
    }

    private List<Transaction> removeSameDayTransactions(List<Transaction> complaintTransactions) {
        Date now = new Date();

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);

        Date today = todayCalendar.getTime();

        List<Transaction> nonSameDayTransactions = new ArrayList<>();

        for (Transaction txn : complaintTransactions) {
            if (txn.getTransactionDate() != null) {
                if (txn.getTransactionDate().before(today)) {
                    nonSameDayTransactions.add(txn);
                }
            }
        }

        return nonSameDayTransactions;
    }

    @Autowired
    public void setFinacleDao(FinacleDao finacleDao) {
        this.finacleDao = finacleDao;
    }
}
