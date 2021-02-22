package fi.lassemaatta.temporaljooq.feature.person.dto;

import fi.lassemaatta.jooq.tables.records.PersonRecord;
import fi.lassemaatta.jooq.tables.records.PersonWithHistoryViewRecord;
import fi.lassemaatta.temporaljooq.feature.common.mixin.VersionedDto;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface PersonDto
        extends ModifiableFields<PersonDto>,
                VersionedDto {

    static PersonDto from(final PersonRecord r) {
        return ImmutablePersonDto.builder()
                                 .id(r.getId())
                                 .createdAt(r.getCreatedAt())
                                 .systemTime(r.getSysPeriod())
                                 .firstName(r.getFirstName())
                                 .lastName(r.getLastName())
                                 .employer(Optional.ofNullable(r.getFkEmployerId()))
                                 .build();
    }

    static PersonDto from(final PersonWithHistoryViewRecord r) {
        return ImmutablePersonDto.builder()
                                 .id(r.getId())
                                 .createdAt(r.getCreatedAt())
                                 .systemTime(r.getSysPeriod())
                                 .firstName(r.getFirstName())
                                 .lastName(r.getLastName())
                                 .employer(Optional.ofNullable(r.getFkEmployerId()))
                                 .build();
    }
}
