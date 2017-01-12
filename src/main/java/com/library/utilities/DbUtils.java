/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import java.util.Date;

public final class DbUtils {

    public static Integer ZeroToNull(int value) {
        return value == 0 ? null : value;
    }

    public static long ZeroToNull(long value) {
        return (value == 0L ? null : value);
    }

    public static String EmptyToNull(String value) {
        return (value == null) || (value.equals("")) ? null : value;
    }

    public static Date NullTo1970(Date date) {
        return date == null ? new Date(0L) : date;
    }
}
