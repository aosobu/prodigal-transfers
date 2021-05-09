package Complaint.configuration;

import Complaint.model.ComplaintState;
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

public class ComplaintStateDataSourceConfiguration {

    /*state data source*/
    @Bean
    @ConfigurationProperties("spring.datasource.state")
    public DataSourceProperties stateDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.state.configuration")
    public DataSource stateDataSource() {
        return stateDataSourceProperties().initializeDataSourceBuilder()
                .type(BasicDataSource.class).build();
    }

    @Bean(name = "stateEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean stateEntityManagerFactory (
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(stateDataSource())
                .packages(ComplaintState.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager stateTransactionManager(
            final @Qualifier("stateEntityManagerFactory") LocalContainerEntityManagerFactoryBean
                    stateEntityManagerFactory) {
        return new JpaTransactionManager(stateEntityManagerFactory.getObject());
    }
}
