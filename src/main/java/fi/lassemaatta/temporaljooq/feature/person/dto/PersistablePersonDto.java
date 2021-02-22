package fi.lassemaatta.temporaljooq.feature.person.dto;

import org.immutables.value.Value;

@Value.Immutable
public interface PersistablePersonDto
        extends ModifiableFields<PersistablePersonDto> {

    static PersistablePersonDto of(final String firstName,
                                   final String lastName) {
        return ImmutablePersistablePersonDto.builder()
                                            .firstName(firstName)
                                            .lastName(lastName)
                                            .build();
    }
}
