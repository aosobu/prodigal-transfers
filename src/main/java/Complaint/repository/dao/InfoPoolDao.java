package Complaint.repository.dao;

import Complaint.model.Branch;
import Complaint.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class InfoPoolDao {

    private JdbcTemplate jdbcTemplate;

    public Customer getCustomerWithCustomerId(String customerId) {
        String sql;

        sql = "SELECT " +
                "email, " +
                "phone, " +
                "customer_permanent_address as address_line1, " +
                "customer_type_extended as client_type " +
                "FROM [bx_customer_eproduct_base] " +
                "WHERE customer_id = '" + customerId + "' ";

        List<Customer> customers = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));

        return customers.get(0);
    }

    public Branch getBranchInformation(String branchCode) {
        String sql =
                "SELECT DISTINCT branch_code, branch_name, branch_classification\n" +
                        "FROM infopool.dbo.bx_branch_operations_base\n" +
                        "WHERE branch_code = '" + branchCode + "'";

        List<Branch> branches = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(Branch.class));
        if (!branches.isEmpty())
            return branches.get(0);
        else
            return null;
    }

    @Autowired
    public void setDataSource(@Qualifier("infoPoolDS") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
