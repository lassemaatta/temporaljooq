package fi.lassemaatta.temporaljooq.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:configuration/testdb.properties")
public class TestDataSourceProperties {
    @Value("#{environment['db.test.driver']}")
    private String driverClassName;

    @Value("#{environment['db.test.url']}")
    private String jdbcUrl;

    @Value("#{environment['db.test.min.connections']}")
    private int minimumIdle;

    @Value("#{environment['db.test.max.connections']}")
    private int maximumPoolSize;

    @Value("#{environment['db.test.username']}")
    private String username;

    @Value("#{environment['db.test.password']}")
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
