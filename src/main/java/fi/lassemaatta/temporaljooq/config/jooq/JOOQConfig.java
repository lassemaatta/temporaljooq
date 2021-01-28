package fi.lassemaatta.temporaljooq.config.jooq;

import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:configuration/jooq.properties")
public class JOOQConfig {

    @Bean
    public JOOQToSpringExceptionTransformer jooqToSpringExceptionTransformer() {
        return new JOOQToSpringExceptionTransformer();
    }

    @Bean
    public DefaultConfiguration configuration(final DataSourceConnectionProvider connectionProvider,
                                              final Environment env,
                                              final JOOQToSpringExceptionTransformer jooqToSpringExceptionTransformer) {
        final DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.set(connectionProvider);
        jooqConfiguration.set(new DefaultExecuteListenerProvider(jooqToSpringExceptionTransformer));

        final Settings settings = jooqConfiguration.settings();
        settings.withReturnAllOnUpdatableRecord(true);
        jooqConfiguration.setSettings(settings);


        final String sqlDialectName = env.getRequiredProperty("jooq.sql.dialect");
        final SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfiguration.set(dialect);

        return jooqConfiguration;
    }

    @Bean
    public DefaultDSLContext dsl(final DefaultConfiguration configuration) {
        return new DefaultDSLContext(configuration);
    }
}
