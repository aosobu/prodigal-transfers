package Complaint.repository.dao;

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

    @Autowired
    public void setDataSource(@Qualifier("infoPoolDS") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
