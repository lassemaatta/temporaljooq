package fi.lassemaatta.temporaljooq.feature.common.repository;

import fi.lassemaatta.temporaljooq.config.jooq.converter.TimeRange;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.time.Instant;

public final class Conditions {
    private Conditions() {
    }

    public static Condition rangeContainsValue(final Field<TimeRange> f1,
                                               final Instant f2) {
        return DSL.condition("({0} @> {1})", f1, f2);
    }
}
