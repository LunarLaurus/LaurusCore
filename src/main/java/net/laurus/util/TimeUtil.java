package net.laurus.util;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtil {

	// Convert uptime (in milliseconds) or Duration to a human-readable format
	// (HH:mm:ss)
	public static String formatUptime(long uptimeMillis) {
		return formatUptime(Duration.ofMillis(uptimeMillis));
	}

	// Convert uptime (Duration) to a human-readable format (HH:mm:ss)
	public static String formatUptime(Duration uptime) {
		long hours = uptime.toHours();
		long minutes = uptime.toMinutes() % 60;
		long seconds = uptime.getSeconds() % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	// Convert uptime (in milliseconds) to a detailed format (days, hours, minutes,
	// seconds)
	public static String formatUptimeDetailed(long uptimeMillis) {
		return formatUptimeDetailed(Duration.ofMillis(uptimeMillis));
	}

	// Convert uptime (Duration) to a detailed format (days, hours, minutes,
	// seconds)
	public static String formatUptimeDetailed(Duration uptime) {
		long days = uptime.toDays();
		long hours = uptime.toHours() % 24;
		long minutes = uptime.toMinutes() % 60;
		long seconds = uptime.getSeconds() % 60;
		return String.format("%d days, %02d hours, %02d minutes, %02d seconds", days, hours, minutes, seconds);
	}

	// Convert uptime (in milliseconds) to a precise string (HH:mm:ss.SSS)
	public static String formatUptimePrecise(long uptimeMillis) {
		return formatUptimePrecise(Duration.ofMillis(uptimeMillis));
	}

	// Convert uptime (Duration) to a precise format (HH:mm:ss.SSS)
	public static String formatUptimePrecise(Duration uptime) {
		long hours = uptime.toHours();
		long minutes = uptime.toMinutes() % 60;
		long seconds = uptime.getSeconds() % 60;
		long millis = uptime.toMillis() % 1000;
		return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
	}

	// Convert uptime (in milliseconds or Duration) to a ZonedDateTime representing
	// the system boot time
	public static ZonedDateTime getSystemBootTime(long uptimeMillis) {
		return getSystemBootTime(Duration.ofMillis(uptimeMillis));
	}

	// Convert uptime (Duration) to a ZonedDateTime representing the system boot
	// time
	public static ZonedDateTime getSystemBootTime(Duration uptime) {
		Instant bootInstant = Instant.now().minus(uptime);
		return ZonedDateTime.ofInstant(bootInstant, ZoneOffset.UTC);
	}

	// Get system uptime in Duration (this works for either long or Duration input)
	public static Duration getUptime(long uptimeMillis) {
		return Duration.ofMillis(uptimeMillis);
	}

	// Get the system's uptime as a Duration
	public static Duration getUptime(Duration uptime) {
		return uptime;
	}

	// Get the current system time as a Duration (time since the Unix epoch)
	public static Duration getCurrentTime() {
		return Duration.between(Instant.EPOCH, Instant.now());
	}

	// Get the current system time as a human-readable string
	public static String formatCurrentTime() {
		return Instant.now().toString();
	}

	// Calculate the time difference (in seconds) between two uptime values
	public static long timeDifference(long startUptimeMillis, long endUptimeMillis) {
		return endUptimeMillis - startUptimeMillis;
	}

	// Calculate the time difference (in seconds) between two Duration values
	public static long timeDifference(Duration startUptime, Duration endUptime) {
		return endUptime.minus(startUptime).getSeconds();
	}

	// Calculate the Unix epoch time based on system uptime
	public static long getUnixEpochTimeMillis(long uptimeMillis) {
		return Instant.now().minusMillis(uptimeMillis).toEpochMilli();
	}

	// Get the Unix epoch time in seconds based on system uptime
	public static long getUnixEpochTimeSecs(long uptimeMillis) {
		return Instant.now().minusMillis(uptimeMillis).getEpochSecond();
	}

}
