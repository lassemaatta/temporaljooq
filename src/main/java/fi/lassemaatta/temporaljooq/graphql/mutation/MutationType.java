package fi.lassemaatta.temporaljooq.graphql.mutation;

import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.dto.PersistableCompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.repository.ICompanyRepository;
import fi.lassemaatta.temporaljooq.feature.person.dto.PersistablePersonDto;
import fi.lassemaatta.temporaljooq.feature.person.dto.PersonDto;
import fi.lassemaatta.temporaljooq.feature.person.repository.IPersonRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

@Component
public class MutationType implements GraphQLMutationResolver {

    private final ICompanyRepository companyRepository;
    private final IPersonRepository personRepository;

    @Autowired
    public MutationType(final ICompanyRepository companyRepository,
                        final IPersonRepository personRepository) {
        this.companyRepository = companyRepository;
        this.personRepository = personRepository;
    }

    public CompanyDto createCompany(final String name) {
        return companyRepository.create(PersistableCompanyDto.of(name));
    }

    public PersonDto createPerson(final String firstName,
                                  final String lastName,
                                  @Nullable final UUID employerId) {
        final Optional<UUID> employer = Optional.ofNullable(employerId);
        return personRepository.create(PersistablePersonDto.of(firstName,
                                                               lastName,
                                                               employer));
    }
}
