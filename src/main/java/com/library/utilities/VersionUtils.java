package com.library.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class VersionUtils {

    public static final int FIELD_TASK = 0;
    public static final int FIELD_FILE = 1;
    public static final int FIELD_PLAY = 2;
    public static final int FIELD_STRATEGY = 3;
    private static final SimpleDateFormat versionDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");

    public static Date defaultVersion() {
        return date1970;
    }

    public static Date UpgradeVersion(Date timeVersion) {
        long now = System.currentTimeMillis();
        now -= now % 1000L;
        long version = timeVersion.getTime();
        version -= version % 1000L;
        timeVersion.setTime(now > version ? now : version + 1000L);
        return timeVersion;
    }

    public static final Date date1970 = parseDate("19700101 000000");

    private static Date parseDate(String dateString) {
        try {
            return versionDateFormat.parse(dateString);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date toDate(String versionString, int field) {
        if ((field < 0) || (field > 3)) {
            throw new IllegalArgumentException("invalid field");
        }
        try {
            String[] stringArray = versionString.split("\\.");
            if ((stringArray != null) && (stringArray.length > field)) {
                return versionDateFormat.parse(stringArray[field]);
            }
            return date1970;
        } catch (Exception e) {
        }
        return date1970;
    }

    public static Date toDate(String dateString) {
        try {
            return versionDateFormat.parse(dateString);
        } catch (Exception e) {
        }
        return date1970;
    }

    public static String toString(Date date) {
        return date != null ? versionDateFormat.format(date) : "";
    }
}
