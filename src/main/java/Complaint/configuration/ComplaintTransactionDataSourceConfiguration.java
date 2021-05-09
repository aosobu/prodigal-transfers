package Complaint.configuration;

import Complaint.model.ComplaintTransaction;
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

public class ComplaintTransactionDataSourceConfiguration {

    /*transaction data source*/
    @Bean
    @ConfigurationProperties("spring.datasource.transaction")
    public DataSourceProperties transactionDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.transaction.configuration")
    public DataSource transactionDataSource() {
        return transactionDataSourceProperties().initializeDataSourceBuilder()
                .type(BasicDataSource.class).build();
    }

    @Bean(name = "transactionEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean transactionEntityManagerFactory (
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(transactionDataSource())
                .packages(ComplaintTransaction.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionTransactionManager(
            final @Qualifier("transactionEntityManagerFactory") LocalContainerEntityManagerFactoryBean
                    transactionEntityManagerFactory) {
        return new JpaTransactionManager(transactionEntityManagerFactory.getObject());
    }
}
