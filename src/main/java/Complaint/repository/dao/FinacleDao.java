package Complaint.repository.dao;

import Complaint.model.Bank;
import Complaint.model.BranchUser;
import Complaint.model.Customer;
import Complaint.model.Transaction;
import Complaint.model.api.AccountDateRange;
import Complaint.utilities.BankCodesReader;
import com.teamapt.exceptions.CosmosServiceException;
import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * created by Osobu Adewale 2021-05-10
 */
@Repository
public class FinacleDao {
    private Logger logger = LoggerFactory.getLogger(FinacleDao.class);

    private JdbcTemplate jdbcTemplate;
    private BankCodesReader bankCodesReader;

    private String htd;
    private String dtd;
    private String gam;
    private String upr;
    private String get;
    private String gsp;

    public BranchUser getBankUserByStaffId(String staffId) throws Exception {
        try {
            String finacleQuery = "SELECT a.user_id username,a.sol_id branch_code,a.user_emp_id staff_id,b.emp_email_id email,b.emp_name staff_name" +
                    " FROM " + upr + " a " +
                    " INNER JOIN " + get + " b ON (a.user_emp_id = b.emp_id) " +
                    " WHERE UPPER(a.user_emp_id) = ? " +
                    " AND a.entity_cre_flg = 'Y' " +
                    " AND a.del_flg = 'N'";


            List<BranchUser> result = this.jdbcTemplate.query(finacleQuery, new Object[]{staffId.toUpperCase()}, new BeanPropertyRowMapper(BranchUser.class));
            for (BranchUser b : result)
                b.setStaffName(WordUtils.capitalizeFully(b.getStaffName()));

            return result != null && !result.isEmpty() ? result.get(0) : null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Problem getting user information from Finacle.");
        }
    }

    public List<Transaction> findHTDTxnsWithAccDateRange(AccountDateRange accountDateRange) throws ParseException, CosmosServiceException {

        DateFormat formSdf = new SimpleDateFormat("yyyy-MM-dd");

        String dateRange = accountDateRange.getDateRange();

        String startDateString = dateRange.split(":")[0].trim();
        String endDateString = dateRange.split(":")[1].trim();

        Date startDate = formSdf.parse(startDateString);
        Date endDate = formSdf.parse(endDateString);

        return findHTDTxns(accountDateRange.getAccountNumber(), startDate, endDate);
    }

    public List<Transaction> findHTDTxns(String accountNumber, Date startDate, Date endDate) throws CosmosServiceException {
        DateFormat oracleSdf = new SimpleDateFormat("d-MMM-yyyy");

        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.set(Calendar.HOUR_OF_DAY, c.getMaximum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getMaximum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getMaximum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getMaximum(Calendar.MILLISECOND));
        endDate = c.getTime();

        String b = oracleSdf.format(startDate);
        String e = oracleSdf.format(endDate);

        List<Transaction> transactions;

        String finacleQuery = "(SELECT " +
                "g.bacid AS bacid," +
                "g.sol_id AS sol," +
                "g.foracid AS account_number, " +
                "g.acct_name AS account_name," +
                "h.TRAN_DATE AS transaction_date," +
                "h.tran_id AS tran_id," +
                "h.tran_particular AS narration," +
                "h.tran_rmks AS session_id, " +
                "h.tran_amt AS amount," +
                "h.part_tran_type AS tran_type, " +
                "h.entry_date AS entry_date, " +
                "h.value_date AS value_date, " +
                "h.tran_crncy_code AS currency_code, " +
                "h.ref_num AS pan " +
                "FROM " + gam + " g INNER JOIN " + htd + " h ON g.acid=h.acid " +
                "WHERE h.del_flg = 'N' " +
                "AND g.entity_cre_flg = 'Y' " +
                "AND g.foracid = ? " +
                "AND h.part_tran_type = 'D' " +
                "AND h.tran_date BETWEEN ? AND ?)" +
                "UNION" +
                "(SELECT " +
                "g.bacid AS bacid," +
                "g.sol_id AS sol," +
                "g.foracid AS account_number, " +
                "g.acct_name AS account_name," +
                "h.TRAN_DATE AS transaction_date," +
                "h.tran_id AS tran_id," +
                "h.tran_particular AS narration," +
                "h.tran_rmks AS session_id, " +
                "h.tran_amt AS amount," +
                "h.part_tran_type AS tran_type, " +
                "h.entry_date AS entry_date, " +
                "h.value_date AS value_date, " +
                "h.tran_crncy_code AS currency_code, " +
                "h.ref_num AS pan " +
                "FROM " + gam + " g INNER JOIN " + htd + " h ON g.acid=h.acid " +
                "WHERE h.del_flg = 'N' " +
                "AND g.entity_cre_flg = 'Y' " +
                "AND g.foracid = ? " +
                "AND h.part_tran_type = 'C' " +
                "AND h.tran_date > ?)";

        logger.info(" Finacle Query {} " + accountNumber + " acct number " + b + " date range " + e);

        try {
            transactions = jdbcTemplate.query(finacleQuery,
                    new Object[]{accountNumber, b, e, accountNumber, b},
                    new BeanPropertyRowMapper<>(Transaction.class));
        } catch (DataAccessException d) {
            logger.error(d.getMessage());
            throw new CosmosServiceException("PERSISTENCE LAYER ERROR {} " + d.getMessage());
        }

        return formatTransactions(transactions);
    }

    //TODO:: Refactor to use an interface canled TransactionFormatter
    //TODO: Dissolve monster method
    private List<Transaction> formatTransactions(List<Transaction> transactions) {
        for (Transaction txn : transactions) {
            try {

                if (txn.getPan() != null) {
                    if (StringUtils.hasText(txn.getPan())) {
                        int index = txn.getPan().indexOf("****");
                        if (index > 0) {
                            // 6 before + **** + 4 after
                            txn.setPan(txn.getPan().substring(index - 6, index)
                                    + "****"
                                    + txn.getPan().substring(index + 4, index + 8));
                        } else {
                            txn.setPan(null);
                        }
                    }
                }

                if (txn.getTranId() != null)
                    txn.setTranId(txn.getTranId().trim());

                if (txn.getNarration() != null)
                    txn.setNarration(txn.getNarration().trim());

                if (txn.getNarration() != null) {
                    int atIndex = txn.getNarration().indexOf('@');
                    if (atIndex > 0) {
                        String terminalIdStr = txn.getNarration().substring(atIndex);
                        if (terminalIdStr.length() >= 9) {
                            txn.setTerminalId(terminalIdStr.substring(1, 9));
                            if (terminalIdStr.substring(1, 2).equals(" ")) {
                                txn.setTerminalId(terminalIdStr.substring(2, 10));
                            }
                        }
                    }
                }

                txn = checkIfInternationalAndSetSol(txn);

                int days = Days.daysBetween(new DateTime(txn.getTransactionDate()), new DateTime(new Date())).getDays();
                if (days > 120)
                    txn.setPassed120Days(true);

                txn.setAccountName(WordUtils.capitalizeFully(txn.getAccountName()));

                if (txn.getSessionId() != null) {
                    String[] rrnStan = txn.getSessionId().split("/");
                    txn.setRrn(rrnStan[0].trim());
                    if (rrnStan.length > 1) {
                        if (rrnStan[1].length() >= 6)
                            txn.setStan(org.apache.commons.lang.StringUtils.right(rrnStan[1].trim(), 6));
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error("Error Encountered Formatting Transaction with Tran Id: {} " + txn.getTranId());
            }
        }

        return transactions;
    }

    private Transaction checkIfInternationalAndSetSol(Transaction transaction) {
        String narration = transaction.getNarration();
        int atIndex;
        if (narration != null) {
            atIndex = narration.indexOf("@");
            if (atIndex > 0) {
                String terminalIdStr = transaction.getNarration().substring(atIndex);
                List<Bank> banks = bankCodesReader.getBanks();
                if (terminalIdStr.length() >= 9) {
                    narration = narration.substring(atIndex + 1).replace(" ", "");
                    // Check character after '@' sign: if number, local, else international
                    char c = narration.charAt(0);
                    if (Character.isAlphabetic(c)) {
                        transaction.setInternational(true);
                    }

                    for(Bank bank : banks){
                        if(narration.contains(bank.getBankAbbreviation())){
                            transaction.setInternational(false);
                            break;
                        }
                    }
                    // Check if Onus
                    if (narration.length() >= 7) {
                        if (narration.substring(0, 4).equals("1070"))
                            transaction.setOnus(true);
                        // Set SOL (5, 6, 7 of terminal Id in narration)
                        transaction.setSol(narration.substring(4, 7));
                    }
                }
            }
        }
        return transaction;
    }

    public List<Transaction> findDTDTxns(String accountNumber) throws CosmosServiceException {
        List<Transaction> transactions;

        String finacleQuery = "SELECT " +
                "g.bacid AS bacid," +
                "g.sol_id AS sol," +
                "g.foracid AS account_number, " +
                "g.acct_name AS account_name," +
                "h.TRAN_DATE AS transaction_date," +
                "h.tran_id AS tran_id," +
                "h.tran_particular AS narration," +
                "h.tran_rmks AS session_id, " +
                "h.tran_amt AS amount," +
                "h.part_tran_type AS tran_type, " +
                "h.entry_date AS entry_date," +
                "h.ref_num AS pan " +
                "FROM " + gam + " g INNER JOIN " + dtd + " h ON g.acid=h.acid " +
                "WHERE h.del_flg = 'N' " +
                "AND g.entity_cre_flg = 'Y' " +
                "AND g.foracid = ?";

        logger.info(" Dtd Query " + finacleQuery);

        try {
            transactions = jdbcTemplate.query(finacleQuery,
                    new Object[]{accountNumber},
                    new BeanPropertyRowMapper<>(Transaction.class));
        } catch (DataAccessException d) {
            logger.error(d.getMessage());
            throw new CosmosServiceException("PERSISTENCE LAYER ERROR: " + d.getMessage());
        }

        return formatTransactions(transactions);
    }

    public Customer getCustomerFromGam(String accountNumber) throws CosmosServiceException {
        try {
            String sql;

            sql = "SELECT a.cif_id as customer_id, " +
                    "a.sol_id, " +
                    "a.acct_name AS account_name, " +
                    "b.schm_desc as scheme_code, " +
                    "a.acct_crncy_code as account_currency_code, " +
                    "a.schm_type as scheme_type " +
                    "FROM " + gam + " a " +
                    "INNER JOIN " + gsp + " b ON (a.schm_code = b.schm_code) " +
                    "WHERE foracid = ?";

            List<Customer> customers = jdbcTemplate.query(sql, new Object[]{accountNumber}, new BeanPropertyRowMapper<>(Customer.class));

            return customers.get(0);
        } catch (DataAccessException d) {
            logger.error(d.getMessage());
            throw new CosmosServiceException("PERSISTENCE LAYER ERROR: " + d.getMessage());
        }
    }

    public List<BranchUser> getBranchUsers(String branchCodes) throws CosmosServiceException {
        try {
            String finacleQuery = "SELECT a.user_id username,a.sol_id branch_code,a.user_emp_id staff_id,b.emp_email_id email,b.emp_name staff_name\n" +
                    " FROM " + upr + " a " +
                    " INNER JOIN " + get + " b ON (a.user_emp_id = b.emp_id) " +
                    " WHERE a.sol_id IN (" + branchCodes + ")" +
                    " AND a.entity_cre_flg = 'Y' " +
                    " AND a.del_flg = 'N'";

            List<BranchUser> result = jdbcTemplate.query(finacleQuery, new BeanPropertyRowMapper<>(BranchUser.class));
            for (BranchUser b : result)
                b.setStaffName(WordUtils.capitalizeFully(b.getStaffName()));
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CosmosServiceException("Problem getting branch users from Finacle.");
        }
    }


    @Autowired
    public void setDataSource(@Qualifier("finacleDs") DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setHtd(@Value("${finacle.htd.table}") String htd) {
        this.htd = htd;
    }

    @Autowired
    public void setDtd(@Value("${finacle.dtd.table}") String dtd) {
        this.dtd = dtd;
    }

    @Autowired
    public void setGam(@Value("${finacle.gam.table}") String gam) {
        this.gam = gam;
    }

    @Autowired
    public void setUpr(@Value("${finacle.upr.table}") String upr) {
        this.upr = upr;
    }

    @Autowired
    public void setGet(@Value("${finacle.get.table}") String get) {
        this.get = get;
    }

    @Autowired
    public void setGsp(@Value("${finacle.gsp.table}") String gsp) {
        this.gsp = gsp;
    }

    @Autowired
    public void setBankCodesReader(BankCodesReader bankCodesReader) {
        this.bankCodesReader = bankCodesReader;
    }
}
