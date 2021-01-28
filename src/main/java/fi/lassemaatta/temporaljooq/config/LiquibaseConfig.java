package fi.lassemaatta.temporaljooq.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {
    private static final String CHANGELOG_LOCATION = "classpath:migrations/db.changelog.xml";

    @Bean
    public SpringLiquibase liquibase(@Nonnull @Qualifier("dataSource") final DataSource dataSource,
                                     @Nonnull final ResourceLoader resourceLoader) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(CHANGELOG_LOCATION);
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
