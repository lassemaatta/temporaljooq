package fi.lassemaatta.temporaljooq.graphql.subscription;

import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.graphql.subscription.company.CompanyPublisher;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionType implements GraphQLSubscriptionResolver {

    private final CompanyPublisher companyPublisher;

    public SubscriptionType(final CompanyPublisher companyPublisher) {
        this.companyPublisher = companyPublisher;
    }

    @SuppressWarnings("unused")
    public Publisher<List<CompanyDto>> companies() {
        return companyPublisher.companies();
    }
}
