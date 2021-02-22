package fi.lassemaatta.temporaljooq.feature.common.mixin;

import fi.lassemaatta.temporaljooq.config.jooq.converter.TimeRange;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface VersionedDto {
    UUID id();

    OffsetDateTime createdAt();

    TimeRange systemTime();
}
