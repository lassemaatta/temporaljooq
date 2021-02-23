package fi.lassemaatta.temporaljooq.graphql.scalar;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScalarConfiguration {
    @Bean
    public GraphQLScalarType uuid() {
        return GraphQLScalarType.newScalar()
                                .name("UUID")
                                .description("UUID v4")
                                .coercing(new UUIDCoercing())
                                .build();
    }
}
