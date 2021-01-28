package fi.lassemaatta.temporaljooq.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:configuration/db.properties")
public class DataSourceProperties {
    @Value("#{environment['db.driver']}")
    private String driverClassName;

    @Value("#{environment['db.url']}")
    private String jdbcUrl;

    @Value("#{environment['db.min.connections']}")
    private int minimumIdle;

    @Value("#{environment['db.max.connections']}")
    private int maximumPoolSize;

    @Value("#{environment['db.username']}")
    private String username;

    @Value("#{environment['db.password']}")
    private String password;

    public DataSourceConfigDto config() {
        return ImmutableDataSourceConfigDto
                .builder()
                .driverClassName(driverClassName)
                .jdbcUrl(jdbcUrl)
                .minimumIdle(minimumIdle)
                .maximumPoolSize(maximumPoolSize)
                .username(username)
                .password(password)
                .build();
    }
}
