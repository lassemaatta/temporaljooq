package fi.lassemaatta.temporaljooq.graphql;


import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import org.dataloader.DataLoaderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collection;
import java.util.List;

@Configuration
public class GraphQLConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(GraphQLConfiguration.class);

    public static final String SCHEMA_PATH = "api.graphqls";

    @Bean
    public GraphQLSchema schema(final List<GraphQLScalarType> scalars,
                                final List<GraphQLResolver<?>> resolvers) {
        LOG.info("Registering {} scalars", scalars.size());
        LOG.info("Registering {} resolvers", resolvers.size());
        return SchemaParser.newParser()
                           .file(SCHEMA_PATH)
                           .resolvers(resolvers)
                           .scalars(scalars)
                           .build()
                           .makeExecutableSchema();
    }

    @Bean
    public DataLoaderRegistry dataLoaderRegistry(final Collection<NamedDataLoader<?, ?>> loaderList) {
        LOG.info("Registering {} data loaders..", loaderList.size());
        final DataLoaderRegistry registry = new DataLoaderRegistry();
        for (final NamedDataLoader<?, ?> loader : loaderList) {
            final String name = loader.name();
            LOG.info("Registering dataloader for name {}", name);
            registry.register(name, loader.dataloader());
        }
        return registry;
    }

    @Bean
    @Primary
    public GraphQLServletContextBuilder servletContextBuilder(final DataLoaderRegistry registry) {
        return new CustomGraphQLServletContextBuilder(registry);
    }
}
