package fi.lassemaatta.temporaljooq.config.jooq.converter;

import org.jooq.Converter;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

public class TimeRangeConverter implements Converter<Object, TimeRange> {

    private static final String DATE_OR_EMPTY = "([\"]?[0-9-:\\.+\\s]*[\"]?)";
    private static final Pattern PATTERN = Pattern.compile("[(\\[(]" + DATE_OR_EMPTY + ',' + DATE_OR_EMPTY + "[\\])]");
    private static final DateTimeFormatter PG_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .appendOffset("+HHmm", "+00")
            .toFormatter();

    private static Optional<Instant> fromString(@Nullable final String val) {
        return Optional.ofNullable(val)
                       .filter(str -> !str.isEmpty())
                       .map(str -> PG_TIME_FORMATTER.parse(str, OffsetDateTime::from).toInstant());
    }

    @Override
    @Nullable
    public TimeRange from(@Nullable final Object t) {
        if (t == null) {
            return null;
        }
        if ("empty".equals(t)) {
            return TimeRangeUtil.EMPTY;
        }
        final Matcher m = PATTERN.matcher(t.toString()
                                           .replaceAll("\"", ""));
        if (m.find()) {
            final Optional<Instant> from = fromString(m.group(1));
            final Optional<Instant> to = fromString(m.group(2));
            return TimeRange.between(from, to);
        } else {
            throw new IllegalArgumentException("Unsupported range : " + t);
        }
    }

    @Override
    @Nullable
    public Object to(final TimeRange u) {
        if (u == null) {
            return null;
        }
        if (TimeRangeUtil.EMPTY.equals(u)) {
            return "empty";
        }
        if (TimeRangeUtil.UNBOUNDED.equals(u)) {
            return "(,)";
        }
        final String fromStr = u.from()
                                .map(from -> Timestamp.from(from).toString())
                                .orElse("");
        final String toStr = u.to()
                              .map(to -> Timestamp.from(to).toString())
                              .orElse("");
        return '[' + fromStr + ',' + toStr + ']';
    }

    @Override
    public Class<Object> fromType() {
        return Object.class;
    }

    @Override
    public Class<TimeRange> toType() {
        return TimeRange.class;
    }
}
