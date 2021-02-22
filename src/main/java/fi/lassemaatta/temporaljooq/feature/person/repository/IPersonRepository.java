package fi.lassemaatta.temporaljooq.feature.person.repository;

import fi.lassemaatta.temporaljooq.feature.person.dto.PersistablePersonDto;
import fi.lassemaatta.temporaljooq.feature.person.dto.PersonDto;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface IPersonRepository {
    PersonDto create(PersistablePersonDto person);

    PersonDto update(PersonDto person);

    List<PersonDto> find(boolean includeHistory);

    Optional<PersonDto> findAt(Instant systemTime);
}
