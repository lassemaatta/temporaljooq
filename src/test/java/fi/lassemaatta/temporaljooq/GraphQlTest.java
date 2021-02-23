package fi.lassemaatta.temporaljooq;

import com.graphql.spring.boot.test.GraphQLTest;
import fi.lassemaatta.temporaljooq.config.BaseTest;
import fi.lassemaatta.temporaljooq.graphql.GraphQLConfiguration;
import fi.lassemaatta.temporaljooq.graphql.scalar.ScalarConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@GraphQLTest
@Import({
                GraphQLConfiguration.class,
                ScalarConfiguration.class
        })
@ActiveProfiles("testDatabase")
public abstract class GraphQlTest extends BaseTest {
}
