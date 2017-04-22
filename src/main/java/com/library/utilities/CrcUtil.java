/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.utilities;

import com.library.sglogger.util.LoggerUtil;
import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;

public class CrcUtil {

    private static final LoggerUtil logger = new LoggerUtil(CrcUtil.class);

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
    private static byte[] getBytesFromText(String text) {

        byte[] byteArray = null;
        try {
            byteArray = ((text == null || text.length() == 0) ? new byte[0] : text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            byteArray = text.getBytes();
            ex.printStackTrace();
        }

        return byteArray;
    }

    /**
     *
     * @param text
     * @return
     */
    public static long buildText(String text) {

        byte[] byteArray = getBytesFromText(text);

        CRC32 crc32 = new CRC32();
        if (byteArray.length > 0) {
            crc32.update(byteArray);
        }

        //final long crcValue = crc32.getValue() & 0xFFFFFFFF;
        final long crcValue = crc32.getValue() & 0xFFFFFFFFL;

        return crcValue;
    }

    /**
     *
     * @param text
     * @return
     */
    public static String buildCDATASection(final String text) {

        byte[] byteArray = getBytesFromText(text);

        final StringBuilder builder = new StringBuilder();
        builder.append("<![CDATA[");
        for (int i = 0; i < byteArray.length; ++i) {
            builder.append(String.format("%02X", byteArray[i]));
        }
        builder.append("]]>");

        //String cdata = StringEscapeUtils.escapeXml("<![CDATA[" + builder.toString() + "]]>");
        String cdata = builder.toString();

        logger.debug("CDATA section is: " + cdata);

        return cdata;

    }

}
