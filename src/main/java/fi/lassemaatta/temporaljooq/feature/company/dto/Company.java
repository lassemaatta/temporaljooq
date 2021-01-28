package fi.lassemaatta.temporaljooq.feature.company.dto;

import fi.lassemaatta.jooq.tables.records.CompanyRecord;
import fi.lassemaatta.jooq.tables.records.CompanyWithHistoryViewRecord;
import fi.lassemaatta.temporaljooq.config.jooq.converter.TimeRange;
import org.immutables.value.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

@Value.Immutable
public interface Company extends ModifiableFields {
    UUID id();

    OffsetDateTime createdAt();

    TimeRange systemTime();

    Company withName(String name);

    static Company from(final CompanyRecord r) {
        return ImmutableCompany.builder()
                               .id(r.getId())
                               .name(r.getName())
                               .createdAt(r.getCreatedAt())
                               .systemTime(r.getSysPeriod())
                               .build();
    }

    static Company from(final CompanyWithHistoryViewRecord r) {
        return ImmutableCompany.builder()
                               .id(r.getId())
                               .name(r.getName())
                               .createdAt(r.getCreatedAt())
                               .systemTime(r.getSysPeriod())
                               .build();
    }
}
