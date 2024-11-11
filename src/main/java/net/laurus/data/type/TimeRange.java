package net.laurus.data.type;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class TimeRange {

    // Constants for conversions
    private static final int HOURS_IN_DAY = 24;
    private static final int DAYS_IN_WEEK = 7;
    private static final int DAYS_IN_MONTH = 30;      // Approximate month as 30 days
    private static final int DAYS_IN_YEAR = 365;      // Approximate year as 365 days
    private static final int YEARS_IN_DECADE = 10;
    private static final int YEARS_IN_CENTURY = 100;
    private static final int YEARS_IN_MILLENNIUM = 1000;

    private final long period;
    private final ChronoUnit scale;

    // Constructor that accepts LocalDateTime and calculates the duration
    public TimeRange(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        this.period = duration.toSeconds();
        this.scale = ChronoUnit.SECONDS; // Default to seconds for precise Duration
    }

    // Calculate start time based on the current time and duration
    private Instant getStartTimeInstant() {
        Instant now = Instant.now();
        return now.minus(calculateDuration());
    }

    // Returns the current time as the end time
    private Instant getEndTimeInstant() {
        return Instant.now();
    }
    
    // Converts Instant to LocalDateTime in the system's default time zone
    public LocalDateTime getStartTime() {
        return LocalDateTime.ofInstant(getStartTimeInstant(), ZoneId.systemDefault());
    }

    // Converts Instant to LocalDateTime in the system's default time zone
    public LocalDateTime getEndTime() {
        return LocalDateTime.ofInstant(getEndTimeInstant(), ZoneId.systemDefault());
    }

    // Return the total duration based on the time unit (scale)
    public Duration getDuration() {
        return calculateDuration();
    }

    // Calculate duration based on supported or unsupported ChronoUnits
    private Duration calculateDuration() {
        switch (scale) {
            case NANOS:
                return Duration.ofNanos(period);
            case MICROS:
                return Duration.of(period * 1_000, ChronoUnit.NANOS); // Convert micros to nanos
            case MILLIS:
                return Duration.ofMillis(period);
            case SECONDS:
                return Duration.ofSeconds(period);
            case MINUTES:
                return Duration.ofMinutes(period);
            case HOURS:
                return Duration.ofHours(period);
            case HALF_DAYS:
                return Duration.of(period * (HOURS_IN_DAY / 2), ChronoUnit.HOURS); // Convert half-days to hours
            case DAYS:
                return Duration.ofDays(period);
            case WEEKS:
                return Duration.of(period * DAYS_IN_WEEK, ChronoUnit.DAYS); // Convert weeks to days
            case MONTHS:
                return Duration.of(period * DAYS_IN_MONTH, ChronoUnit.DAYS); // Approximate months as 30 days
            case YEARS:
                return Duration.of(period * DAYS_IN_YEAR, ChronoUnit.DAYS); // Approximate years as 365 days
            case DECADES:
                return Duration.of(period * YEARS_IN_DECADE * DAYS_IN_YEAR, ChronoUnit.DAYS); // Approximate decades as 10 years
            case CENTURIES:
                return Duration.of(period * YEARS_IN_CENTURY * DAYS_IN_YEAR, ChronoUnit.DAYS); // Approximate centuries as 100 years
            case MILLENNIA:
                return Duration.of(period * YEARS_IN_MILLENNIUM * DAYS_IN_YEAR, ChronoUnit.DAYS); // Approximate millennia as 1000 years
            case ERAS:
                throw new UnsupportedOperationException("ChronoUnit.ERAS is not supported for calculating duration.");
            case FOREVER:
                throw new UnsupportedOperationException("ChronoUnit.FOREVER represents infinite duration and cannot be calculated.");
            default:
                throw new UnsupportedOperationException("Unsupported time unit: " + scale);
        }
    }
}
