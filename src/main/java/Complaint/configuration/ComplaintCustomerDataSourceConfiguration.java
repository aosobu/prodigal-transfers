package Complaint.configuration;

import Complaint.model.ComplaintCustomer;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

public class ComplaintCustomerDataSourceConfiguration {

    /*customer data source*/
    @Bean
    @ConfigurationProperties("spring.datasource.customer")
    public DataSourceProperties customerDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.customer.configuration")
    public DataSource customerDataSource() {
        return customerDataSourceProperties().initializeDataSourceBuilder()
                .type(BasicDataSource.class).build();
    }

    @Bean(name = "customerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory (
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(customerDataSource())
                .packages(ComplaintCustomer.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager customerTransactionManager(
            final @Qualifier("customerEntityManagerFactory") LocalContainerEntityManagerFactoryBean
                    customerEntityManagerFactory) {
        return new JpaTransactionManager(customerEntityManagerFactory.getObject());
    }
}
