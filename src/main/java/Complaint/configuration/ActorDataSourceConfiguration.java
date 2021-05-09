package Complaint.configuration;

import Complaint.model.Actor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "Complaint.repository",
        entityManagerFactoryRef = "actorEntityManagerFactory",
        transactionManagerRef= "actorTransactionManager")
public class ActorDataSourceConfiguration {

    /*actor data source*/
    @Bean
    @ConfigurationProperties("spring.datasource.actor")
    public DataSourceProperties actorDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.actor.configuration")
    public DataSource actorDataSource() {
        return actorDataSourceProperties().initializeDataSourceBuilder()
                .type(BasicDataSource.class).build();
    }

    @Bean(name = "actorEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean actorEntityManagerFactory (
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(actorDataSource())
                .packages(Actor.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager actorTransactionManager(
            final @Qualifier("actorEntityManagerFactory") LocalContainerEntityManagerFactoryBean
                    actorEntityManagerFactory) {
        return new JpaTransactionManager(actorEntityManagerFactory.getObject());
    }
}
