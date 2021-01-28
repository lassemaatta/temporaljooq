package fi.lassemaatta.temporaljooq.feature.company.dto;

import org.immutables.value.Value;

@Value.Immutable
public interface PersistableCompany extends ModifiableFields {
    static PersistableCompany of(final String name) {
        return ImmutablePersistableCompany.builder()
                                          .name(name)
                                          .build();
    }
}
