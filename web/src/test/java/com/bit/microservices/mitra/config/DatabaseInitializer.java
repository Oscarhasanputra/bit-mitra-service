package com.bit.microservices.mitra.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
@Component
public class DatabaseInitializer {
    private final PostgreSQLContainer<?> postgresContainer;

    public DatabaseInitializer(PostgreSQLContainer<?> postgresContainer) {
        this.postgresContainer = postgresContainer;
    }

    private void flywayMigrate(){
        Flyway flyway = Flyway
                .configure()
                .locations("classpath:sql_schema_test")
                .dataSource(this.postgresContainer.getJdbcUrl()
                        ,this.postgresContainer.getUsername()
                        ,this.postgresContainer.getPassword())
                .load();

        flyway.migrate();
    }

    @EventListener(ContextRefreshedEvent.class)
    public void setupDatabaseSchema() {
        System.out.println("migrate database schema");
        flywayMigrate();
    }
}
