package fi.lassemaatta.temporaljooq.graphql.query.person;

import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.feature.person.dto.PersonDto;
import fi.lassemaatta.temporaljooq.graphql.query.company.CompanyDataLoader;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class PersonResolver implements GraphQLResolver<PersonDto> {

    private static final Logger LOG = LoggerFactory.getLogger(PersonResolver.class);

    @Nullable
    public CompletableFuture<CompanyDto> employerDto(final PersonDto person,
                                                     final DataFetchingEnvironment env) {
        LOG.info("Resolving employer for person: {}", person);
        final DataLoader<UUID, CompanyDto> loader = env.getDataLoader(CompanyDataLoader.NAME);
        return person.employer()
                     .map(loader::load)
                     .orElse(CompletableFuture.completedFuture(null));
    }
}
