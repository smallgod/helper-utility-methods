/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

/**
 *
 * @author smallgod
 */
public class StringUtils {
    
    
        /**
     * Replace one or more spaces in a string with given string/xter
     *
     * @param stringToFormat
     * @param replaceWith
     * @return
     */
    public static String replaceSpaces(String stringToFormat, String replaceWith) {

        String formattedString = stringToFormat.replaceAll("\\s+", replaceWith);
        return formattedString;
    }

    
}
