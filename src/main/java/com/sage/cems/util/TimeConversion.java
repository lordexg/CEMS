package com.sage.cems.util;

public class TimeConversion {
    public static long millisecondsToMinutes(long milliseconds) {
        return milliseconds / (1000 * 60);
    }

    public static long minutesToMilliseconds(long minutes) {
        return minutes * 60 * 1000;
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
}
