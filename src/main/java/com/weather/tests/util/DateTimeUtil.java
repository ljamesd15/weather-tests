package com.weather.tests.util;

import com.weather.tests.enums.TimeConstant;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjuster;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.time.ZoneOffset.UTC;

@Component
public class DateTimeUtil {

    private static final Map<TimeConstant, TemporalAdjuster> TIME_CONSTANT_DAY_ADJUSTMENT = Map.of(
            TimeConstant.YESTERDAY, t -> t.minus(Period.ofDays(1)),
            TimeConstant.TODAY, t -> t.plus(Period.ofDays(0)),
            TimeConstant.NOW, t -> t.plus(Period.ofDays(0)),
            TimeConstant.TOMORROW, t -> t.plus(Period.ofDays(1))
    );
    private static final Map<TimeConstant, Function<Clock, LocalTime>> TIME_CONSTANT_TIME_SUPPLIER = Map.of(
            TimeConstant.YESTERDAY, (clock) -> LocalTime.MIDNIGHT,
            TimeConstant.TODAY, (clock) -> LocalTime.MIDNIGHT,
            TimeConstant.NOW, LocalTime::now,
            TimeConstant.TOMORROW, (clock) -> LocalTime.MIDNIGHT
    );

    private final Clock clock;

    public DateTimeUtil(final Clock clock) {
        this.clock = clock;
    }

    public ZonedDateTime timeConstantToDateTime(TimeConstant time) {
        final LocalDate today = LocalDate.now(this.clock);
        final TemporalAdjuster adjuster = TIME_CONSTANT_DAY_ADJUSTMENT.get(time);
        final var timeFunction = TIME_CONSTANT_TIME_SUPPLIER.get(time);
        return ZonedDateTime.of(today.with(adjuster).atTime(timeFunction.apply(this.clock)), UTC);
    }
}
