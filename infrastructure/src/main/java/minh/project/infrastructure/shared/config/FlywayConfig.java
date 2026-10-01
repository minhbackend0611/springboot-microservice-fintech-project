package minh.project.infrastructure.shared.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    @Primary
    public Flyway authFlyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .schemas("auth")
                .createSchemas(true)
                .table("flyway_schema_history_auth")
                .locations("classpath:db/migration/auth")
                .baselineOnMigrate(true)
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway notiFlyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .schemas("noti")
                .createSchemas(true)
                .table("flyway_schema_history_noti")
                .locations("classpath:db/migration/noti")
                .baselineOnMigrate(true)
                .load();
    }
}
