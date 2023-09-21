package com.eagle.mas.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
    /**
     * Default UTC ZoneId.
     */
    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");
    /**
     * Default UTC pattern.
     */
    private static final String UTC_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static LocalDateTime convertUTCToLocalDateTime(String utcDateTime) {
        return ZonedDateTime.parse(utcDateTime).toLocalDateTime();
    }

    public static String getUTCTimeFromDate(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(UTC_DATETIME_PATTERN);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(UTC_ZONE_ID));
        return dateFormatter.format(date);
    }
    public static LocalDateTime getUTCCurrentDateTime() {
        return ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
    }


    public static boolean before(LocalDateTime d1, LocalDateTime d2) {
        try {
            return d1.isBefore(d2);
        } catch (Exception e) {
        e.printStackTrace();
        }
        return false;
    }



}
