package fi.lassemaatta.temporaljooq.config.jooq.converter;

import org.immutables.value.Value;

import java.time.Instant;
import java.util.Optional;


@Value.Immutable
public abstract class TimeRange {

    public abstract boolean isEmpty();

    // TODO: Might be nice to specify open/closed for the boundaries
    public abstract Optional<Instant> from();

    public abstract Optional<Instant> to();

    @Override
    public String toString() {
        if (isEmpty()) {
            return "(empty)";
        }
        if (from().isEmpty() && to().isEmpty()) {
            return "(unbounded)";
        }

        final StringBuilder sb = new StringBuilder(3);
        sb.append('[');
        from().ifPresent(sb::append);
        sb.append(',');
        to().ifPresent(sb::append);
        sb.append(']');

        return sb.toString();
    }

    public static TimeRange empty() {
        return ImmutableTimeRange.builder()
                                 .isEmpty(true)
                                 .build();
    }

    public static TimeRange unbounded() {
        return ImmutableTimeRange.builder()
                                 .isEmpty(false)
                                 .build();
    }

    public static TimeRange between(final Optional<Instant> from,
                                    final Optional<Instant> to) {
        return ImmutableTimeRange.builder()
                                 .isEmpty(false)
                                 .from(from)
                                 .to(to)
                                 .build();
    }

    public static TimeRange between(final Instant from,
                                    final Instant to) {
        return ImmutableTimeRange.builder()
                                 .isEmpty(false)
                                 .from(from)
                                 .to(to)
                                 .build();
    }
}
