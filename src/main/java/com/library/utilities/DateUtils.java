/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.datamodel.Constants.NamedConstants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    //patterns
    //   "dd/MM/yyyy HH:mm:ss"
    /**
     *
     * @param string
     * @param dateStringFormat
     * @return
     */
    public static DateTime convertStringToDate(String string, String dateStringFormat) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateStringFormat);
        DateTime dt = formatter.parseDateTime(string);

        return dt;
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
     * Get Default DateTimeNow with Kampala timezone and date-time-dash format
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

}
