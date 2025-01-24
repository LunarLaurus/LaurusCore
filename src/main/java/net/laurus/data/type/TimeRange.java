package net.laurus.data.type;

import static java.time.Duration.between;
import static java.time.ZoneId.systemDefault;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.NonNull;
import lombok.Value;

/**
 * Represents a range of time with a start, end, duration, and scale.
 */
@Value
public class TimeRange {

    @NonNull LocalDateTime startTime;
    @NonNull LocalDateTime endTime;
    @NonNull Duration duration;
    @NonNull ChronoUnit scale;

    /**
     * Constructs a TimeRange using start and end times, defaulting to seconds as the scale.
     *
     * @param start The start time (must not be null).
     * @param end   The end time (must not be null and must be after or equal to start).
     */
    public TimeRange(@NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        this(start, end, ChronoUnit.SECONDS);
    }

    /**
     * Constructs a TimeRange using start, end, and a specific ChronoUnit scale.
     *
     * @param start The start time (must not be null).
     * @param end   The end time (must not be null and must be after or equal to start).
     * @param scale The ChronoUnit scale (must not be null and must be time-based).
     */
    public TimeRange(@NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull ChronoUnit scale) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time cannot be after end time.");
        }
        if (!scale.isTimeBased()) {
            throw new IllegalArgumentException("Scale must be a time-based ChronoUnit.");
        }
        this.startTime = start;
        this.endTime = end;
        this.duration = between(start, end);
        this.scale = scale;
    }

    /**
     * Returns the duration of the time range in the specified scale.
     *
     * @return The duration in the defined ChronoUnit scale.
     */
    public long getDurationInScale() {
        return scale.between(startTime, endTime);
    }

    /**
     * Converts the start time to an Instant in the system default time zone.
     *
     * @return The start time as an Instant.
     */
    public java.time.Instant toStartInstant() {
        return startTime.atZone(systemDefault()).toInstant();
    }

    /**
     * Converts the end time to an Instant in the system default time zone.
     *
     * @return The end time as an Instant.
     */
    public java.time.Instant toEndInstant() {
        return endTime.atZone(systemDefault()).toInstant();
    }

    /**
     * Provides a string representation of the time range.
     *
     * @return A human-readable description of the time range.
     */
    @Override
    public String toString() {
        return String.format("TimeRange [%s to %s, duration: %s, scale: %s]",
                startTime, endTime, duration, scale.name());
    }

}
