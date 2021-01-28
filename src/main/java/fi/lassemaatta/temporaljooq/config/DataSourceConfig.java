package fi.lassemaatta.temporaljooq.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fi.lassemaatta.temporaljooq.config.profile.StandardDatabase;
import fi.lassemaatta.temporaljooq.config.profile.TestDatabase;
import fi.lassemaatta.temporaljooq.config.properties.DataSourceConfigDto;
import fi.lassemaatta.temporaljooq.config.properties.DataSourceProperties;
import fi.lassemaatta.temporaljooq.config.properties.TestDataSourceProperties;
import org.jooq.impl.DataSourceConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Configuration
    @StandardDatabase
    @Import(DataSourceProperties.class)
    static class StandardDatabaseConfiguration {
        @Resource
        private DataSourceProperties dataSourceProperties;

        @Bean
        public DataSourceConfigDto dataSourceConfigDto() {
            return dataSourceProperties.config();
        }
    }

    @Configuration
    @TestDatabase
    @Import(TestDataSourceProperties.class)
    static class TestDatabaseConfiguration {
        @Resource
        private TestDataSourceProperties testDataSourceProperties;

        @Bean
        public DataSourceConfigDto dataSourceConfigDto() {
            return testDataSourceProperties.config();
        }
    }

    @Bean(destroyMethod = "close")
    @Qualifier("dataSourcePool")
    public HikariDataSource dataSourcePool(final DataSourceConfigDto dataSourceConfigDto) {
        final HikariConfig hikariConfig = dataSourceConfigDto.buildHikariConfig();

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LazyConnectionDataSourceProxy lazyConnectionDataSource(@Qualifier("dataSourcePool") final DataSource dataSource) {
        return new LazyConnectionDataSourceProxy(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("lazyConnectionDataSource") final LazyConnectionDataSourceProxy lazyConnectionDataSource) {
        return new DataSourceTransactionManager(lazyConnectionDataSource);
    }

    @Bean
    @Qualifier("dataSource")
    public TransactionAwareDataSourceProxy transactionAwareDataSource(@Qualifier("lazyConnectionDataSource") final LazyConnectionDataSourceProxy lazyConnectionDataSource) {
        return new TransactionAwareDataSourceProxy(lazyConnectionDataSource);
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider(final TransactionAwareDataSourceProxy transactionAwareDataSource) {
        return new DataSourceConnectionProvider(transactionAwareDataSource);
    }
}
