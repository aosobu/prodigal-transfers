package Complaint.repository.dao;

import Complaint.model.BranchUser;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * created by Osobu Adewale 2021-05-10
 */
@Repository
public class FinacleDao {
    private Logger logger = LoggerFactory.getLogger(FinacleDao.class);

    private JdbcTemplate jdbcTemplate;

    private String htd;
    private String dtd;
    private String gam;
    private String upr;
    private String get;
    private String gsp;

    @Autowired
    public void setDataSource(@Qualifier("finacleDs") DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

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
}
