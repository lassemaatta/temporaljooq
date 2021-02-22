package fi.lassemaatta.temporaljooq.feature.company.repository;

import fi.lassemaatta.jooq.tables.Company;
import fi.lassemaatta.jooq.tables.CompanyWithHistoryView;
import fi.lassemaatta.jooq.tables.records.CompanyRecord;
import fi.lassemaatta.temporaljooq.feature.company.dto.CompanyDto;
import fi.lassemaatta.temporaljooq.feature.company.dto.ModifiableFields;
import fi.lassemaatta.temporaljooq.feature.company.dto.PersistableCompanyDto;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static fi.lassemaatta.temporaljooq.feature.common.repository.Conditions.rangeContainsValue;

@Repository
public class CompanyRepository implements ICompanyRepository {

    private static final Company COMPANY = Company.COMPANY;
    private static final CompanyWithHistoryView HISTORY_VIEW = CompanyWithHistoryView.COMPANY_WITH_HISTORY_VIEW;

    private final DSLContext db;

    @Autowired
    public CompanyRepository(final DSLContext db) {
        this.db = db;
    }

    private void write(final CompanyRecord record,
                       final ModifiableFields<?> source) {
        record.setName(source.name());
    }

    @Override
    @Transactional
    public CompanyDto create(final PersistableCompanyDto company) {
        final CompanyRecord r = db.newRecord(COMPANY);

        write(r, company);

        r.store();
        return CompanyDto.from(r);
    }

    @Override
    @Transactional
    public CompanyDto update(final CompanyDto company) {
        final CompanyRecord r =
                db.fetchOne(COMPANY,
                            COMPANY.ID.eq(company.id()));

        write(r, company);

        r.update();

        return CompanyDto.from(r);
    }

    @Override
    public List<CompanyDto> find(final boolean includeHistory) {
        return includeHistory ?
                db.selectFrom(HISTORY_VIEW).fetch(CompanyDto::from) :
                db.selectFrom(COMPANY).fetch(CompanyDto::from);
    }

    @Override
    public Optional<CompanyDto> findAt(final Instant systemTime) {
        return db.selectFrom(HISTORY_VIEW)
                 .where(rangeContainsValue(HISTORY_VIEW.SYS_PERIOD,
                                           systemTime))
                 .fetchOptional()
                 .map(CompanyDto::from);
    }
}
