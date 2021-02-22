package fi.lassemaatta.temporaljooq.feature.person.dto;

import java.util.Optional;
import java.util.UUID;

public interface ModifiableFields<T> {
    String firstName();

    String lastName();

    Optional<UUID> employer();

    T withFirstName(String firstName);

    T withLastName(String firstName);

    T withEmployer(Optional<? extends UUID> employer);

    T withEmployer(UUID employer);
}
