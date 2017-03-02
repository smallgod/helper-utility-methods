/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;

public class CrcUtil {

//    public static MarqueeXML build(String text) {
//        MarqueeXML marquee = new MarqueeXML();
//        byte[] byteArray = null;
//        try {
//            byteArray = text.getBytes("UTF-8");
//        } catch (UnsupportedEncodingException ex) {
//            byteArray = text.getBytes();
//        }
//        CRC32 crc32 = new CRC32();
//        crc32.update(byteArray);
//        long crcValue = crc32.getValue() & 0xFFFFFFFF;
//
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < byteArray.length; i++) {
//            builder.append(String.format("%02X", new Object[]{Byte.valueOf(byteArray[i])}));
//        }
//        marquee.setId(IDCreator.GenerateInt());
//        marquee.setText(builder.toString());
//        marquee.setSum(crcValue);
//        return marquee;
//    }
    public static long buildText(String text) {
        byte[] byteArray = null;
        try {
            byteArray = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            byteArray = text.getBytes();
            ex.printStackTrace();
        }
        CRC32 crc32 = new CRC32();
        crc32.update(byteArray);
        long crcValue = crc32.getValue() & 0xFFFFFFFF;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            builder.append(String.format("%02X", new Object[]{Byte.valueOf(byteArray[i])}));
        }
        return crcValue;
    }

}
