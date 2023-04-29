package com.example.wgusoftware2scheduler.Utility;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
/**
 * The TimeHandler class provides various utility methods to handle time related operations.
 */
public class TimeHandler {
    /**
     * Converts a given UTC time to the local time of the system.
     *
     * @param utcTime the UTC time to be converted
     * @return the converted local time
     */
    public static LocalDateTime convertUTCtoLocal(LocalDateTime utcTime) {
        return utcTime.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Converts a given local time to UTC time.
     *
     * @param localTime the local time to be converted
     * @return the converted UTC time
     */
    public static LocalDateTime convertLocaltoUTC(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

    /**
     * Returns the current UTC time.
     *
     * @return the current UTC time
     */
    public static LocalDateTime getNowUTC() {
        return LocalDateTime.now(ZoneId.of("UTC"));
    }
    /**
     * Returns the current UTC date.
     *
     * @return the current UTC date
     */
    public static LocalDate getTodayUTC() {
        return LocalDate.now(ZoneId.of("UTC"));
    }
    /**
     * Returns the current local time.
     *
     * @return the current local time
     */
    public static LocalDateTime getNowLocal() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }
    /**
     * Checks if two given dates are in the same week.
     *
     * @param time1 the first date
     * @param time2 the second date
     * @return true if the two dates are in the same week, false otherwise
     */
    public static boolean sameWeek(LocalDate time1, LocalDate time2) {
        return time1.getYear() == time2.getYear()
                && time1.get(WeekFields.ISO.weekOfWeekBasedYear()) == time2.get(WeekFields.ISO.weekOfWeekBasedYear());
    }
    /**
     * Checks if two given dates are in the same month.
     *
     * @param time1 the first date
     * @param time2 the second date
     * @return true if the two dates are in the same month, false otherwise
     */
    public static boolean sameMonth(LocalDate time1, LocalDate time2) {

        return time1.getYear() == time2.getYear() && time1.getMonth().equals(time2.getMonth());
    }
    /**
     * Generates a list of times between the given start and end times with the given interval in minutes.
     *
     * @param start the start time in the format "HH:mm"
     * @param end the end time in the format "HH:mm"
     * @param minutes the interval between the times in minutes
     * @return a list of times in the format "HH:mm"
     */
    public static List<String> timeRange(String start, String end, int minutes) {
        LocalTime startTime = LocalTime.parse(start);
        LocalTime endTime = LocalTime.parse(end);
        Stream<LocalTime> times = Stream.iterate(startTime, t -> t.plusMinutes(minutes))
                .limit(1 + startTime.until(endTime, ChronoUnit.MINUTES) / minutes);
        List<String> timeList = new ArrayList<String>();
        times.forEach(time -> timeList.add(time.toString()));
        return timeList;
    }
    /**
     * Checks if a given time is within the business hours (8am - 10pm EST).
     *
     * @param time the time to be checked
     * @return true if the given time is within business hours, false otherwise
     */
    public static boolean isWithinBusinessHours(LocalDateTime time) {
        ZoneId sourceZone = ZoneId.systemDefault();
        ZoneId targetZone = ZoneId.of("America/New_York");
        ZonedDateTime targetDateTime = ZonedDateTime.of(time, sourceZone).withZoneSameInstant(targetZone);
        LocalTime startTime = LocalTime.of(8, 0); // 8am EST
        LocalTime endTime = LocalTime.of(22, 0); // 10pm EST
        LocalTime targetTime = targetDateTime.toLocalTime();
        return !targetTime.isBefore(startTime) && !targetTime.isAfter(endTime);
    }
    /**
     * Checks if a given time is within 15 minutes of the current time.
     *
     * @param time the time to be checked
     * @return true if the given time is within 15 minutes of the current time, false otherwise
     */
    public static boolean isWithinFifteenMinutes(LocalTime time) {
        LocalTime now = LocalTime.now();
        long timeDiff = Duration.between(now, time).toMinutes();
        return timeDiff > 0 && timeDiff <= 15;
    }
}