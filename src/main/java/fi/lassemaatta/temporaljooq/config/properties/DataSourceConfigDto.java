package fi.lassemaatta.temporaljooq.config.properties;

import com.zaxxer.hikari.HikariConfig;
import org.immutables.value.Value;


@Value.Immutable
public interface DataSourceConfigDto {
    String getDriverClassName();

    String getJdbcUrl();

    int getMinimumIdle();

    int getMaximumPoolSize();

    String getUsername();

    String getPassword();

    default HikariConfig buildHikariConfig() {
        final HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(getDriverClassName());
        hikariConfig.setJdbcUrl(getJdbcUrl());
        hikariConfig.setMinimumIdle(getMinimumIdle());
        hikariConfig.setMaximumPoolSize(getMaximumPoolSize());
        hikariConfig.setUsername(getUsername());
        hikariConfig.setPassword(getPassword());

        return hikariConfig;
    }
}
