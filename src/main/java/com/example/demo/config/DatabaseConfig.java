package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@ComponentScan("com.example.demo.config")
public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${SPRING_DATASOURCE_USERNAME}")
    private String username;

    @Value("${SPRING_DATASOURCE_PASSWORD}")
    private String password;

    public DatabaseConfig() {
        logger.info("DatabaseConfig instance created.");
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
