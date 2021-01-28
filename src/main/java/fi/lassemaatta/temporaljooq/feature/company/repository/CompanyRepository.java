package fi.lassemaatta.temporaljooq.feature.company.repository;

import fi.lassemaatta.jooq.tables.CompanyWithHistoryView;
import fi.lassemaatta.jooq.tables.records.CompanyRecord;
import fi.lassemaatta.temporaljooq.feature.company.dto.Company;
import fi.lassemaatta.temporaljooq.feature.company.dto.ModifiableFields;
import fi.lassemaatta.temporaljooq.feature.company.dto.PersistableCompany;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CompanyRepository implements ICompanyRepository {

    private static final fi.lassemaatta.jooq.tables.Company COMPANY =
            fi.lassemaatta.jooq.tables.Company.COMPANY;
    private static final CompanyWithHistoryView HISTORY_VIEW =
            CompanyWithHistoryView.COMPANY_WITH_HISTORY_VIEW;

    private final DSLContext db;

    @Autowired
    public CompanyRepository(final DSLContext db) {
        this.db = db;
    }

    private void write(final CompanyRecord record,
                       final ModifiableFields source) {
        record.setName(source.name());
    }

    @Override
    @Transactional
    public Company create(final PersistableCompany company) {
        final CompanyRecord r = db.newRecord(COMPANY);

        write(r, company);

        r.store();
        return Company.from(r);
    }

    @Override
    @Transactional
    public Company update(final Company company) {
        final CompanyRecord r =
                db.fetchOne(COMPANY,
                            COMPANY.ID.eq(company.id()));

        write(r, company);

        r.update();

        return Company.from(r);
    }

    @Override
    public List<Company> find(final boolean includeHistory) {
        return includeHistory ?
                db.selectFrom(HISTORY_VIEW).fetch(Company::from) :
                db.selectFrom(COMPANY).fetch(Company::from);
    }
}
