package Complaint.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Osobu Adewale on 2021/05/10
 */
@Configuration
@EnableJpaRepositories(
    basePackages = {"Complaint"},
    entityManagerFactoryRef = "transferRecallEntityManagerFactory",
    transactionManagerRef = "transferRecallTransactionManager")
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "transferRecallDS")
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource complaintDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "finacleDs")
    @ConfigurationProperties(prefix = "datasource.finacle")
    public DataSource finacleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "infoPoolDS")
    @ConfigurationProperties(prefix = "datasource.infopool")
    public DataSource infopoolDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Value("${hibernate.dialect:org.hibernate.dialect.SQLServer2012Dialect}")
    String hibernateDialect;

    @Bean
    LocalContainerEntityManagerFactoryBean transferRecallEntityManagerFactory() {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        Map<String, String> prop = new HashMap<>();
        prop.put("hibernate.ejb.naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy");
        prop.put("hibernate.dialect", hibernateDialect);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(primaryDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setJpaPropertyMap(prop);

        factoryBean.setPackagesToScan("Complaint", "Complaint");

        return factoryBean;
    }

    @Bean
    @Primary
    PlatformTransactionManager transferRecallTransactionManager() {
        return new JpaTransactionManager(transferRecallEntityManagerFactory().getObject());
    }

}
