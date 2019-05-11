package com.zappos.backoffice.util;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The utility class to provide methods 
 * to parse date string or format date string
 * 
 * @author spark
 *
 */
public class DateTimeUtils {

    public static final String TIMEZONE_ID_UTC		= "UTC";
    public static final String ISO_DATETIME_PATTERN	= "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'";
    private static final DateTimeFormatter isoDateTimeformatter = DateTimeFormatter.ofPattern(ISO_DATETIME_PATTERN).withZone(ZoneOffset.UTC);

    // hide constructor
    private DateTimeUtils() {}

    /**
     * Format local (system) timeZone Date object to ISO dateTime String in UTC timeZone by ZonedDateTime 
     * instead of using SimpleDateFormat which we need to make it as thread safe via ThreadLocal
     * @param date java.util.Date
     * @return 'yyyy-MM-ddTHH:mm:ss.SSSSSSSZ' format ISO dateTime string
     */
    public static String formatIsoUtcDateTime(Date date) {
        if(null == date) {
            return null;
        }
        return isoDateTimeformatter.format(date.toInstant());
    }

    /**
     * Parse ISO UTC string to Date object
     * @param isoString
     * @return java.util.Date
     * @throws ParseException 
     */
    public static Date parseIsoUtcString(String isoString) throws ParseException {
        LocalDateTime localDateTime = LocalDateTime.parse(isoString, isoDateTimeformatter);
        return Date.from(localDateTime.atZone(ZoneOffset.UTC).toInstant());
    }
}
