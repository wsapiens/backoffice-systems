package com.zappos.backoffice.database.repository;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zappos.backoffice.config.DatasourceProperties;


@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude= {FlywayAutoConfiguration.class})
@EntityScan(basePackages = {"com.zappos.backoffice.database.model"})
@EnableJpaRepositories(basePackages = {"com.zappos.backoffice.database.repository"} )
@PropertySource("classpath:application.properties")
public class RepositoryTestConfig {
    @Bean
    public DatasourceProperties datasourceProperties() {
        return new DatasourceProperties();
    }

    @Bean
    public Flyway flyway() {
        DatasourceProperties properties = datasourceProperties();
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(properties.getUrl(), properties.getUsername(), properties.getPassword());
        return flyway;
    }
}
