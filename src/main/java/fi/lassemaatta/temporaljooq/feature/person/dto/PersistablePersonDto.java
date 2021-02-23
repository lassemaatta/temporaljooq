package fi.lassemaatta.temporaljooq.feature.person.dto;

import org.immutables.value.Value;

import java.util.Optional;
import java.util.UUID;

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

    static PersistablePersonDto of(final String firstName,
                                   final String lastName,
                                   final Optional<UUID> employer) {
        return ImmutablePersistablePersonDto.builder()
                                            .firstName(firstName)
                                            .lastName(lastName)
                                            .employer(employer)
                                            .build();
    }
}
