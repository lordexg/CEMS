package com.sage.cems.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeConversion {
    public static long millisecondsToSeconds(long milliseconds) {
        return milliseconds / 1000;
    }

    public static long secondsToMilliseconds(long seconds) {
        return seconds * 1000;
    }

    public static String formatMilliseconds(long milliseconds) {
        // Convert milliseconds to total minutes
        double totalMinutes = milliseconds / (1000.0 * 60.0);

        if (totalMinutes < 60) {
            // If less than an hour, display in minutes
            return String.format("%.0f Min", totalMinutes);
        } else {
            // Convert to hours for greater durations
            double hours = totalMinutes / 60.0;

            // Format fractional hours up to 2 decimal places
            if (hours % 1 == 0) {
                // Exact hours
                return String.format("%.0f Hour%s", hours, hours > 1 ? "s" : "");
            } else {
                // Fractional hours
                return String.format("%.2f Hour%s", hours, hours > 1 ? "s" : "");
            }
        }
    }

    public static long calculateRemainingTimeInSeconds(Date targetDate) {
        Instant now = Instant.now();
        Instant target = targetDate.toInstant();
        return ChronoUnit.SECONDS.between(now, target);
    }

    public static String formatTime(long totalSeconds) {
        long days = totalSeconds / (24 * 3600);
        long hours = (totalSeconds % (24 * 3600)) / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
    }
}
