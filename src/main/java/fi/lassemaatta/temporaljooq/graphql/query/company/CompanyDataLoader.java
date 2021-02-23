package fi.lassemaatta.temporaljooq.graphql.query.company;

import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.graphql.NamedDataLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CompanyDataLoader implements NamedDataLoader<UUID, CompanyDto> {

    public static final String NAME = "companyDataLoader";
    private static final DataLoaderOptions OPTIONS =
            new DataLoaderOptions()
                    // Control the batch size when fetching N companies
                    .setMaxBatchSize(50)
                    // By default the dataloader will cache all results _forever_
                    // Alternatively we could implement org.dataloader.CacheMap over a Guava cache with expiration
                    .setCachingEnabled(false);

    private final CompanyBatchLoader batchLoader;

    @Autowired
    public CompanyDataLoader(final CompanyBatchLoader batchLoader) {
        this.batchLoader = batchLoader;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public DataLoader<UUID, CompanyDto> dataloader() {
        return DataLoader.newMappedDataLoader(batchLoader,
                                              OPTIONS);
    }
}
