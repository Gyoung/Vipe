package com.vipe.common.util;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class StringUtil {

    public static final String EMPTY_STRING = "";


    public static boolean isNullOrWhiteSpace(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }




    public static String toMD5(String text) {
        if (text == null || text.trim().length() == 0)
            return EMPTY_STRING;

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = text.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
