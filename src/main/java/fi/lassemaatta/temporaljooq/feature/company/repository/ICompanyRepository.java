package fi.lassemaatta.temporaljooq.feature.company.repository;

import fi.lassemaatta.temporaljooq.feature.company.dto.Company;
import fi.lassemaatta.temporaljooq.feature.company.dto.PersistableCompany;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ICompanyRepository {
    Company create(PersistableCompany company);

    Company update(Company company);

    List<Company> find(boolean includeHistory);

    Optional<Company> findAt(Instant systemTime);
}
