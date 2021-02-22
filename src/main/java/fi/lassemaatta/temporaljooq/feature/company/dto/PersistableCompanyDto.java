package fi.lassemaatta.temporaljooq.feature.company.dto;

import org.immutables.value.Value;

@Value.Immutable
public interface PersistableCompanyDto
        extends ModifiableFields<PersistableCompanyDto> {
    static PersistableCompanyDto of(final String name) {
        return ImmutablePersistableCompanyDto.builder()
                                             .name(name)
                                             .build();
    }
}
