package Complaint.configuration;

import Complaint.model.Complaint;
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

public class ComplaintDataSourceConfiguration {

    /*complaint data source*/
    @Bean
    @ConfigurationProperties("spring.datasource.complaint")
    public DataSourceProperties complaintDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.complaint.configuration")
    public DataSource complaintDataSource() {
        return complaintDataSourceProperties().initializeDataSourceBuilder()
                .type(BasicDataSource.class).build();
    }

    @Bean(name = "complaintEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean complaintEntityManagerFactory (
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(complaintDataSource())
                .packages(Complaint.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager complaintTransactionManager(
            final @Qualifier("complaintEntityManagerFactory") LocalContainerEntityManagerFactoryBean
                    complaintEntityManagerFactory) {
        return new JpaTransactionManager(complaintEntityManagerFactory.getObject());
    }
}
