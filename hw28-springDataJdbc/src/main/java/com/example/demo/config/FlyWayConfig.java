//package com.example.demo.config;
//
//import org.flywaydb.core.Flyway;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class FlyWayConfig {
//
//    @Bean
//    public Flyway flyway(DataSource dataSource) {
//        Flyway flyway = Flyway.configure()
//                .dataSource(dataSource)
//                .load();
//        flyway.migrate();
//        return flyway;
//    }
//}
