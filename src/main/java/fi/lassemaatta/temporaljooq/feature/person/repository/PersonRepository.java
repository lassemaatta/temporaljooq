package fi.lassemaatta.temporaljooq.feature.person.repository;

import fi.lassemaatta.jooq.tables.Person;
import fi.lassemaatta.jooq.tables.PersonWithHistoryView;
import fi.lassemaatta.jooq.tables.records.PersonRecord;
import fi.lassemaatta.temporaljooq.feature.person.dto.ModifiableFields;
import fi.lassemaatta.temporaljooq.feature.person.dto.PersistablePersonDto;
import fi.lassemaatta.temporaljooq.feature.person.dto.PersonDto;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static fi.lassemaatta.temporaljooq.feature.common.repository.Conditions.rangeContainsValue;

@Repository
public class PersonRepository implements IPersonRepository {

    private static final Person PERSON = Person.PERSON;
    private static final PersonWithHistoryView HISTORY_VIEW = PersonWithHistoryView.PERSON_WITH_HISTORY_VIEW;

    private final DSLContext db;

    @Autowired
    public PersonRepository(final DSLContext db) {
        this.db = db;
    }

    private static void write(final PersonRecord record,
                              final ModifiableFields<?> source) {
        record.setFirstName(source.firstName());
        record.setLastName(source.lastName());
        record.setFkEmployerId(source.employer().orElse(null));
    }

    @Override
    @Transactional
    public PersonDto create(final PersistablePersonDto person) {
        final PersonRecord r = db.newRecord(PERSON);

        write(r, person);

        r.store();
        return PersonDto.from(r);
    }

    @Override
    @Transactional
    public PersonDto update(final PersonDto person) {
        final PersonRecord r =
                db.fetchOne(PERSON,
                            PERSON.ID.eq(person.id()));

        write(r, person);

        r.update();

        return PersonDto.from(r);
    }

    @Override
    public List<PersonDto> find(final boolean includeHistory) {
        return includeHistory ?
                db.selectFrom(HISTORY_VIEW).fetch(PersonDto::from) :
                db.selectFrom(PERSON).fetch(PersonDto::from);
    }

    @Override
    public Optional<PersonDto> findAt(final Instant systemTime) {
        return db.selectFrom(HISTORY_VIEW)
                 .where(rangeContainsValue(HISTORY_VIEW.SYS_PERIOD,
                                           systemTime))
                 .fetchOptional()
                 .map(PersonDto::from);
    }
}
