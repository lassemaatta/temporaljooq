package fi.lassemaatta.temporaljooq.feature.company.repository;

import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.dto.PersistableCompanyDto;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICompanyRepository {
    CompanyDto create(PersistableCompanyDto company);

    CompanyDto update(CompanyDto company);

    List<CompanyDto> findAll(boolean includeHistory);

    Optional<CompanyDto> find(UUID id);

    List<CompanyDto> findByIds(Collection<UUID> ids);

    Optional<CompanyDto> findAt(UUID id,
                                Instant systemTime);


}
