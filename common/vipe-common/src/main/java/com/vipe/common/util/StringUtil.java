package com.vipe.common.util;

import java.util.UUID;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class StringUtil {

    public static boolean isNullOrWhiteSpace(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
