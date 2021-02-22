package fi.lassemaatta.temporaljooq.feature.company.dto;

import fi.lassemaatta.jooq.tables.records.CompanyRecord;
import fi.lassemaatta.jooq.tables.records.CompanyWithHistoryViewRecord;
import fi.lassemaatta.temporaljooq.feature.common.mixin.VersionedDto;
import org.immutables.value.Value;

@Value.Immutable
public interface CompanyDto
        extends ModifiableFields<CompanyDto>,
                VersionedDto {

    static CompanyDto from(final CompanyRecord r) {
        return ImmutableCompanyDto.builder()
                                  .id(r.getId())
                                  .name(r.getName())
                                  .createdAt(r.getCreatedAt())
                                  .systemTime(r.getSysPeriod())
                                  .build();
    }

    static CompanyDto from(final CompanyWithHistoryViewRecord r) {
        return ImmutableCompanyDto.builder()
                                  .id(r.getId())
                                  .name(r.getName())
                                  .createdAt(r.getCreatedAt())
                                  .systemTime(r.getSysPeriod())
                                  .build();
    }
}
