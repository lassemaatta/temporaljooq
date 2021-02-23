package fi.lassemaatta.temporaljooq.graphql.query;

import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.repository.ICompanyRepository;
import fi.lassemaatta.temporaljooq.feature.person.dto.PersonDto;
import fi.lassemaatta.temporaljooq.feature.person.repository.IPersonRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryType implements GraphQLQueryResolver {

    private static final Logger LOG = LoggerFactory.getLogger(QueryType.class);

    private final ICompanyRepository companyRepository;
    private final IPersonRepository personRepository;

    @Autowired
    public QueryType(final ICompanyRepository companyRepository,
                     final IPersonRepository personRepository) {
        this.companyRepository = companyRepository;
        this.personRepository = personRepository;
    }

    public List<CompanyDto> companies(final DataFetchingEnvironment env) {
        LOG.info("Resolving companies");
        return companyRepository.findAll(true);
    }

    public List<PersonDto> persons(final DataFetchingEnvironment env) {
        LOG.info("Resolving persons");
        return personRepository.find(true);
    }
}
