package fi.lassemaatta.temporaljooq.graphql.query.company;

import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.repository.ICompanyRepository;
import org.dataloader.MappedBatchLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static com.google.common.collect.ImmutableMap.toImmutableMap;

@Component
public class CompanyBatchLoader implements MappedBatchLoader<UUID, CompanyDto> {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyBatchLoader.class);

    private final ICompanyRepository companyRepository;

    @Autowired
    public CompanyBatchLoader(final ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompletionStage<Map<UUID, CompanyDto>> load(final Set<UUID> keys) {
        LOG.info("Scheduling fetch of {} keys", keys.size());
        return CompletableFuture.supplyAsync(() -> companyRepository.findByIds(keys)
                                                                    .stream()
                                                                    .collect(toImmutableMap(CompanyDto::id,
                                                                                            Function.identity())));
    }
}
