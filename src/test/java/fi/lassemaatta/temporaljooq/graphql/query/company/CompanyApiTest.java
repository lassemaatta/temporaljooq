package fi.lassemaatta.temporaljooq.graphql.query.company;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import fi.lassemaatta.temporaljooq.GraphQlTest;
import fi.lassemaatta.temporaljooq.config.jooq.converter.TimeRange;
import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.dto.ImmutableCompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.repository.ICompanyRepository;
import fi.lassemaatta.temporaljooq.feature.person.repository.IPersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.graphql.spring.boot.test.helper.GraphQLTestConstantsHelper.DATA_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

public class CompanyApiTest extends GraphQlTest {

    private final GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private ICompanyRepository companyRepository;

    @MockBean
    private IPersonRepository personRepository;

    public CompanyApiTest(@Autowired final GraphQLTestTemplate graphQLTestTemplate) {
        this.graphQLTestTemplate = graphQLTestTemplate;
    }

    @Test
    public void readCompanies() throws IOException {
        final List<CompanyDto> companies = List.of(
                ImmutableCompanyDto.builder()
                                   .id(UUID.randomUUID())
                                   .name("Some company")
                                   .createdAt(OffsetDateTime.now())
                                   .systemTime(TimeRange.between(Optional.of(Instant.now()),
                                                                 Optional.empty()))
                                   .build()
        );


        doReturn(companies)
                .when(companyRepository)
                .findAll(true);

        final GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/query/company/get_companies.graphql");

        assertThat(response.isOk()).isTrue();

        response.assertThatDataField()
                .isNotNull()
                .and()
                .assertThatField(DATA_PATH + ".companies")
                .asListOf(ImmutableCompanyDto.class)
                .hasSize(1)
                .allMatch(company -> company.name().equals(companies.get(0).name()));
    }
}
