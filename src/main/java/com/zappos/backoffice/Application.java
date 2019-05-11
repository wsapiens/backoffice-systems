package com.zappos.backoffice;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.scheduling.annotation.EnableAsync;

import com.zappos.backoffice.config.DatasourceProperties;

@SpringBootApplication
@EnableMBeanExport
@EnableAsync
@EnableAspectJAutoProxy
public class Application {

    @Bean
    public DatasourceProperties datasourceProperties() {
        return new DatasourceProperties();
    }

    @Bean
    public Flyway flyway() {
        DatasourceProperties properties = datasourceProperties();
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setBaselineVersionAsString("0");
        flyway.setDataSource(properties.getUrl(), properties.getUsername(), properties.getPassword());
        flyway.migrate();
        return flyway;
    }

    public static void main( String[] args ){
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }
}
