package fi.lassemaatta.temporaljooq.feature.company.repository;

import fi.lassemaatta.temporaljooq.IntegrationTest;
import fi.lassemaatta.temporaljooq.feature.company.dto.Company;
import fi.lassemaatta.temporaljooq.feature.company.dto.PersistableCompany;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


public class CompanyRepositoryTest extends IntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyRepositoryTest.class);

    private final ICompanyRepository repository;

    public CompanyRepositoryTest(@Autowired final ICompanyRepository repository) {
        this.repository = repository;
    }

    @Test
    @Sql("/drop_tables.sql")
    public void foobar() throws InterruptedException {
        Company c = repository.create(PersistableCompany.of("first name"));

        c = repository.update(c.withName("second name"));

        final Instant t = Instant.now();

        c = repository.update(c.withName("third name"));

        final List<Company> withHistory = repository.find(true);

        LOG.info("withHistory: {}", withHistory);

        final List<Company> onlyActive = repository.find(false);

        LOG.info("onlyActive: {}", onlyActive);

        Optional<Company> foo = repository.findAt(t);
    }
}
