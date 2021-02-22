package fi.lassemaatta.temporaljooq.feature.company.dto;

public interface ModifiableFields<T> {
    String name();

    T withName(String name);
}
