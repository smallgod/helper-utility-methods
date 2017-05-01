package com.library.utilities.dsmbridge;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public final class UploadUtils {

    protected static final List<String> VIDEO_FILE_EXT_NAMES = new Vector(Arrays.asList(new String[]{
        "wmv", "avi", "rm", "mpg", "mpeg", "mp4", "mp2", "rmvb",
        "mov", "vob", "flv", "f4v", "asx", "asf", "mkv"}));

    protected static final List<String> IMAGE_FILE_EXT_NAMES = new Vector(Arrays.asList(new String[]{
        "bmp", "gif", "png", "jpg", "jpeg", "pdf"}));

    protected static final List<String> AUDIO_FILE_EXT_NAMES = new Vector(Arrays.asList(new String[]{
        "wma", "midi", "mp3"}));

    protected static final List<String> FLASH_FILE_EXT_NAMES = new Vector(Arrays.asList(new String[]{
        "swf"}));

    public static int getFileType(String fileName) {
        try {
            
           System.out.println("File Name: " + fileName);
           
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            
            if (IMAGE_FILE_EXT_NAMES.contains(extension)) {
                return 0;
            }
            
            if (VIDEO_FILE_EXT_NAMES.contains(extension)) {
                return 1;
            }
            
            if (AUDIO_FILE_EXT_NAMES.contains(extension)) {
                return 2;
            }
            if (FLASH_FILE_EXT_NAMES.contains(extension)) {
                return 3;
            }
            return -1;
        } catch (Exception ex) {
        }
        return -1;
    }

    public static int getFileFlag(String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            if (extension.equals("pdf")) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
        }
        return -1;
    }
}
