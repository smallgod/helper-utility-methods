package com.library.utilities;

import com.library.datamodel.Constants.NamedConstants;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 *
 * @author smallgod
 */
public class DateUtils {

    private static final LoggerUtil logger = new LoggerUtil(DateUtils.class);

    /**
     * Pattern - yyyy/MM/dd HH:mm:ss
     *
     * @param string
     * @param dateStringFormat
     * @return
     */
    public static LocalDateTime convertStringToLocalDateTime(String string, String dateStringFormat) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateStringFormat);
        LocalDateTime dt = formatter.parseLocalDateTime(string);

        return dt;
    }

    /**
     * Pattern - yyyy/MM/dd
     *
     * @param string
     * @param dateStringFormat
     * @return
     */
    public static LocalDate convertStringToLocalDate(String string, String dateStringFormat) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateStringFormat);
        LocalDate dt = formatter.parseLocalDate(string);

        return dt;
    }

    //patterns
    //   "dd/MM/yyyy HH:mm:ss"
    /**
     *
     * @param string
     * @param dateStringFormat
     * @return
     */
    public static DateTime convertStringToDateTime(String string, String dateStringFormat) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateStringFormat);
        DateTime dt = formatter.parseDateTime(string);

        return dt;
    }

    /**
     * Get LocalDateTime now
     *
     * @param timeZoneStr
     * @return
     */
    public static LocalDateTime getDateTimeNow(String timeZoneStr) {

        DateTimeZone desiredTimeZone = DateTimeZone.forID(timeZoneStr);
        return LocalDateTime.now(desiredTimeZone);
    }

  

    public static LocalDateTime getDateTimeNow() {

        DateTimeZone desiredTimeZone = DateTimeZone.forID(NamedConstants.KAMPALA_TIME_ZONE);
        return LocalDateTime.now(desiredTimeZone);
    }

    /**
     * Get Default DateTimeNow with Kampala timezone and date-time-dash format
     *
     * @return
     */
    public static String getDefaultDateTimeNow() {

        /*
        DateTime now = new DateTime();
        DateTimeZone kampalaTimeZone1 = DateTimeZone.forID(timeZone);
        DateTime convertedTime1 = now.toDateTime(kampalaTimeZone1);
         */
        DateTime dateNow = DateTime.now();
        DateTimeZone desiredTimeZone = DateTimeZone.forID(NamedConstants.KAMPALA_TIME_ZONE);
        DateTime dateTime = dateNow.toDateTime(desiredTimeZone);

        DateTimeFormatter formatter = DateTimeFormat.forPattern(NamedConstants.DATE_TIME_DASH_FORMAT);
        //DateTime dateTime = formatter.parseDateTime(dateString);
        String formattedDate = formatter.print(dateTime);

        return formattedDate;
    }

    /**
     * Get current date-time string
     *
     * @param timeZone
     * @param dateStringFormat
     * @return
     */
    //kampala time zone - 'Africa/Kampala'
    public static String getDateTimeNow(String timeZone, String dateStringFormat) {

        /*
        DateTime now = new DateTime();
        DateTimeZone kampalaTimeZone1 = DateTimeZone.forID(timeZone);
        DateTime convertedTime1 = now.toDateTime(kampalaTimeZone1);
         */
        DateTime dateNow = DateTime.now();
        DateTimeZone kampalaTimeZone = DateTimeZone.forID(timeZone);
        DateTime kampalTimeNow = dateNow.toDateTime(kampalaTimeZone);

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateStringFormat);
        //DateTime dateTime = formatter.parseDateTime(dateString);
        String formattedDate = formatter.print(kampalTimeNow);

        return formattedDate;
    }

    /**
     * Get LocalDate now
     *
     * @return
     */
    public static LocalDate getDateNow() {

        return LocalDate.now();
    }

    /**
     * Get LocalTime now with upto smallest precision
     *
     * @return
     */
    public static LocalTime getTimeNow() {
        return LocalTime.now();
    }

    /**
     * Get LocalTime now with upto minute precision
     *
     * @return
     */
    public static LocalTime getTimeNowToNearestMinute() {

        LocalTime now = LocalTime.now();
        logger.debug("Time here is : " + now);

        int hourOfDay = now.getHourOfDay();
        int minuteOfHOur = now.getMinuteOfHour();

        minuteOfHOur += Minutes.ONE.getMinutes(); //Add one minute to current time to get next biggest minute

        now = new LocalTime(hourOfDay, minuteOfHOur);
        //now = now.minuteOfHour().roundHalfCeilingCopy(); //round time to the nearest minute, rounds down if less than 30 seconds
        logger.debug("And then here after rounding it up: " + now);

        return now;
    }

    /**
     * parse dateTime in a formatted string - e.g. Kampala timezone is -
     * "Africa/Kampala" and a string-format can be "ddMMyyyyHHmmss"
     *
     * @param dateTimeToFormat
     * @param timeZone
     * @param dateStringFormat
     * @return
     */
    public static String formatDateTime(DateTime dateTimeToFormat, String timeZone, String dateStringFormat) {

        if (dateTimeToFormat == null) {
            logger.warn("Failed to Convert Date, DateTime to Format is NULL!!");
            return "";
        }
        DateTimeZone dateTimeZone = DateTimeZone.forID(timeZone);
        DateTime newDateTime = dateTimeToFormat.toDateTime(dateTimeZone);
        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateStringFormat);
        //DateTime dateTime = formatter.parseDateTime(dateString);
        String formattedDate = formatter.print(newDateTime);

        return formattedDate;
    }

    /**
     *
     * @param dateTimeToFormat
     * @param dateStringFormat
     * @return
     */
    public static String convetLocalDateToString(LocalDate dateTimeToFormat, String dateStringFormat) {

        if (dateTimeToFormat == null) {
            logger.warn("Failed to Convert Date, LocalDate to Format is NULL!!");
            return "";
        }

        String formattedDate = dateTimeToFormat.toString(dateStringFormat);
        return formattedDate;
    }

    /**
     *
     * @param dateTimeToFormat
     * @param dateStringFormat
     * @return
     */
    public static String convetLocalDateToString(LocalDateTime dateTimeToFormat, String dateStringFormat) {

        if (dateTimeToFormat == null) {
            logger.warn("Failed to Convert Date, LocalDate to Format is NULL!!");
            return "";
        }

        String formattedDate = dateTimeToFormat.toString(dateStringFormat);
        return formattedDate;
    }

    /**
     * Time difference between startTime and now
     *
     * @param startTime
     * @return
     */
    public static String timeTakenToNow(DateTime startTime) {

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendYears().appendSuffix(" years ")
                .appendMonths().appendSuffix(" months ")
                .appendWeeks().appendSuffix(" weeks ")
                .appendDays().appendSuffix(" days ")
                .appendHours().appendSuffix(" hours ")
                .appendMinutes().appendSuffix(" minutes ")
                .appendSeconds().appendSuffix(" seconds ")
                .appendMillis().appendSuffix(" milliseconds ")
                //.printZeroNever() //if you don't want to print zeros
                .toFormatter();

        //DateTime myBirthDate = new DateTime(1978, 3, 26, 12, 35, 0, 0);
        DateTime now = new DateTime();
        Period period = new Period(startTime, now);

        String elapsed = formatter.print(period);

        return elapsed;
    }

    /**
     * Time difference between startTime and endTime
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String timeTakenToNow(DateTime startTime, DateTime endTime) {

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendYears().appendSuffix(" years  ")
                .appendMonths().appendSuffix(" months  ")
                .appendWeeks().appendSuffix(" weeks  ")
                .appendDays().appendSuffix(" days  ")
                .appendHours().appendSuffix(" hours  ")
                .appendMinutes().appendSuffix(" minutes  ")
                .appendSeconds().appendSuffix(" seconds  ")
                .appendMillis().appendSuffix(" milliseconds  ")
                //.printZeroNever() //if you don't want to print zeros
                .toFormatter();

        //DateTime myBirthDate = new DateTime(1978, 3, 26, 12, 35, 0, 0);
        Period period = new Period(startTime, endTime);

        String elapsed = formatter.print(period);

        return elapsed;
    }

    /**
     * get time now since 1970
     *
     * @return
     */
    public static long getTimeNowInLong() {

        DateTime timeNow = DateTime.now();
        long time = timeNow.getMillis();

        Date date = new Date();
        long time2 = date.getTime();

        logger.debug("time from joda is: " + time);
        logger.debug("time from jdk is : " + time2);

        return time;
    }

    /**
     * Get Days between 2 Dates
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysBetweenTwoDates(LocalDateTime startDate, LocalDateTime endDate) {

        //Days days = Days.daysBetween(startDate, endDate);
        Days days = Days.daysBetween(startDate.toLocalDate(), endDate.toLocalDate());

        return days.getDays();
    }

    /**
     * Get Days between 2 Dates - with an extra day to represent that very day
     * (start date)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {

        Days days = Days.daysBetween(startDate, endDate);

        return days.getDays() + 1; //Add the extra day to represent that very day
    }

    /**
     * convert LocalDateTime to String
     *
     * @param dateTime
     * @param dateTimePattern
     * @return
     */
    public static String convertLocalDateTimeToString(LocalDateTime dateTime, String dateTimePattern) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateTimePattern);
        String formattedDateTime = dateTime.toString(formatter); // "1986-04-08 12:30"

        return formattedDateTime;
    }

    public static String convertLocalDateToString(LocalDate dateTime, String dateTimePattern) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateTimePattern);
        String formattedDateTime = dateTime.toString(formatter); // "1986-04-08"

        return formattedDateTime;
    }

    /**
     * Add days to LocalDateTime
     *
     * @param daysToAdd
     * @return
     */
    public static LocalDateTime addDaysToLocalDateTimeNow(int daysToAdd) {

        LocalDateTime dateTime = getDateTimeNow(NamedConstants.KAMPALA_TIME_ZONE);
        dateTime = dateTime.plusDays(daysToAdd);

        return dateTime;
    }

    /**
     *
     * @param dateToAddDaysTo
     * @param daysToAdd
     * @return
     */
    public static LocalDate addDaysToLocalDate(LocalDate dateToAddDaysTo, int daysToAdd) {

        LocalDate incrementedDate = dateToAddDaysTo.plusDays(daysToAdd);

        return incrementedDate;
    }

    /**
     *
     * @param daysToAdd
     * @param dateToAddDaysTo
     * @return
     */
    public static LocalDateTime addDaysToLocalDateTime(int daysToAdd, LocalDateTime dateToAddDaysTo) {

        LocalDateTime incrementedDate = dateToAddDaysTo.plusDays(daysToAdd);

        return incrementedDate;
    }

    /**
     * Compare if 2 Local Dates are equal
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean isDateEqualTo(LocalDate dateA, LocalDate dateB) {

        if (dateA.compareTo(dateB) == 0) {
            return Boolean.TRUE;
        }

//        if(dateA.equals(dateB)){
//            return Boolean.TRUE;
//        }
        return Boolean.FALSE;
    }

    /**
     * Convert Time from any form such as Days, Minutes, Seconds to Milliseconds
     *
     * @param timeUnit The TimeUnit we are converting from such as
     * TimeUnit.Minutes
     * @param duration
     * @return
     */
    public static long convertToMillis(TimeUnit timeUnit, long duration) {

        return (timeUnit.toMillis(duration));
    }

    /**
     * Convert Milliseconds of Day to LocalTime
     *
     * @param millisOfDay
     * @param timeZone
     * @return
     */
    public static LocalTime convertMillisToLocalTime(long millisOfDay, DateTimeZone timeZone) {

        LocalTime localTime = new LocalTime(millisOfDay, timeZone);

        return localTime;

    }

    /**
     * Convert millis to Hour of Day
     *
     * @param millisOfDay
     * @return
     */
    public static int convertMillisToHourOfDay(long millisOfDay) {

        LocalTime localTime = new LocalTime(millisOfDay);
        return localTime.getHourOfDay();
    }

    /**
     * Convert LocaTime to String
     *
     * @param time
     * @param timePattern
     * @return
     */
    public static String convertLocalTimeToString(LocalTime time, String timePattern) {

        //time = time.plusSeconds(110);
        String formattedTime = DateTimeFormat.forPattern(timePattern).print(time);

        return formattedTime;

    }

    /**
     *
     * @param millisOfDay
     * @param timeZone
     * @param timeFormat
     * @return
     */
    public static String convertMillisToFormattedLocalTime(long millisOfDay, DateTimeZone timeZone, String timeFormat) {

        LocalTime localTime = new LocalTime(millisOfDay, timeZone);

        String formattedTime = DateTimeFormat.forPattern(timeFormat).print(localTime);

        return formattedTime;

    }

}
