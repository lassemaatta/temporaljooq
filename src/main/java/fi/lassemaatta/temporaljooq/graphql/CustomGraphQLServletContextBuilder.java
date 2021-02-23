package fi.lassemaatta.temporaljooq.graphql;

import graphql.kickstart.execution.context.DefaultGraphQLContext;
import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import org.dataloader.DataLoaderRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

/**
 * This wrapper allows us to supply the dataloader registry when building the context
 * See e.g. https://github.com/sourcelabs-nl/graphql-java-tools-dataloader/blob/master/src/main/kotlin/nl/sourcelabs/graphql/demo/DemoApplication.kt
 */
public class CustomGraphQLServletContextBuilder implements GraphQLServletContextBuilder {

    private final DataLoaderRegistry dataLoaderRegistry;

    public CustomGraphQLServletContextBuilder(final DataLoaderRegistry dataLoaderRegistry) {
        this.dataLoaderRegistry = dataLoaderRegistry;
    }

    @Override
    public GraphQLContext build(final HttpServletRequest request,
                                final HttpServletResponse response) {
        return DefaultGraphQLServletContext.createServletContext(dataLoaderRegistry, null)
                                           .with(request)
                                           .with(response).build();
    }

    @Override
    public GraphQLContext build(final Session session, final HandshakeRequest handshakeRequest) {
        return DefaultGraphQLWebSocketContext.createWebSocketContext(dataLoaderRegistry, null)
                                             .with(session)
                                             .with(handshakeRequest).build();
    }

    @Override
    public GraphQLContext build() {
        return new DefaultGraphQLContext(dataLoaderRegistry, null);
    }
}
