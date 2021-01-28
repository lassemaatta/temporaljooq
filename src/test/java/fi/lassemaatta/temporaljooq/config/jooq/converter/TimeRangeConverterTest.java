package fi.lassemaatta.temporaljooq.config.jooq.converter;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class TimeRangeConverterTest {

    private static final TimeRangeConverter CONVERTER = new TimeRangeConverter();

    @Test
    public void givenNull_thenReturnNull() {
        assertThat(CONVERTER.from(null),
                   is(nullValue()));
    }

    @Test
    public void givenEmpty_thenReturnEmpty() {
        assertThat(CONVERTER.from("empty"),
                   is(TimeRange.empty()));
    }

    @Test
    public void givenUnbounded_thenReturnUnbounded() {
        assertThat(CONVERTER.from("(,)"),
                   is(TimeRange.unbounded()));
    }

    @Test
    public void givenBounded_thenReturnBounded() {
        assertThat(CONVERTER.from("[2004-10-19 10:23:54+02,2005-10-19 10:23:54+02]"),
                   is(TimeRange.between(Instant.parse("2004-10-19T08:23:54.00Z"),
                                        Instant.parse("2005-10-19T08:23:54.00Z"))));
        assertThat(CONVERTER.from("(2004-10-19 10:23:54+02,2005-10-19 10:23:54+02)"),
                   is(TimeRange.between(Instant.parse("2004-10-19T08:23:54.00Z"),
                                        Instant.parse("2005-10-19T08:23:54.00Z"))));
    }

    @Test
    public void givenOnlyTo_thenReturnBounded() {
        assertThat(CONVERTER.from("[,2005-10-19 10:23:54+02]"),
                   is(TimeRange.between(Optional.empty(),
                                        Optional.of(Instant.parse("2005-10-19T08:23:54.00Z")))));
        assertThat(CONVERTER.from("(,2005-10-19 10:23:54+02)"),
                   is(TimeRange.between(Optional.empty(),
                                        Optional.of(Instant.parse("2005-10-19T08:23:54.00Z")))));
    }

    @Test
    public void givenOnlyFrom_thenReturnBounded() {
        assertThat(CONVERTER.from("[2004-10-19 10:23:54+02,]"),
                   is(TimeRange.between(Optional.of(Instant.parse("2004-10-19T08:23:54.00Z")),
                                        Optional.empty())));
        assertThat(CONVERTER.from("(2004-10-19 10:23:54+02,)"),
                   is(TimeRange.between(Optional.of(Instant.parse("2004-10-19T08:23:54.00Z")),
                                        Optional.empty())));

        assertThat(CONVERTER.from("[\"2021-01-28 13:40:04.392588+02\",)"),
                   is(TimeRange.between(Optional.of(Instant.parse("2021-01-28T11:40:04.392588Z")),
                                        Optional.empty())));
    }
}
