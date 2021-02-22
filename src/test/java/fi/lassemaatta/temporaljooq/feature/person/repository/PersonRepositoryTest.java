package fi.lassemaatta.temporaljooq.feature.person.repository;

import fi.lassemaatta.temporaljooq.IntegrationTest;
import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.dto.PersistableCompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.repository.ICompanyRepository;
import fi.lassemaatta.temporaljooq.feature.person.dto.PersistablePersonDto;
import fi.lassemaatta.temporaljooq.feature.person.dto.PersonDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;


public class PersonRepositoryTest extends IntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(PersonRepositoryTest.class);

    private final ICompanyRepository companyRepository;
    private final IPersonRepository personRepository;

    public PersonRepositoryTest(@Autowired final ICompanyRepository companyRepository,
                                @Autowired final IPersonRepository personRepository) {
        this.companyRepository = companyRepository;
        this.personRepository = personRepository;
    }

    @Test
    @Sql("/drop_tables.sql")
    public void foobar() {
        CompanyDto c = companyRepository.create(PersistableCompanyDto.of("first name"));
        PersonDto p = personRepository.create(PersistablePersonDto.of("Ahto", "Simakuutio"));

        LOG.info("Persons: {}", personRepository.find(true));

        personRepository.update(p.withEmployer(c.id()));

        LOG.info("Persons: {}", personRepository.find(true));
    }
}
