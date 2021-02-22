package fi.lassemaatta.temporaljooq.feature.company.repository;

import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.dto.PersistableCompanyDto;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ICompanyRepository {
    CompanyDto create(PersistableCompanyDto company);

    CompanyDto update(CompanyDto company);

    List<CompanyDto> find(boolean includeHistory);

    Optional<CompanyDto> findAt(Instant systemTime);
}
