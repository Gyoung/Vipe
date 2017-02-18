package com.vipe.common.entity;

/**
 * Created by Administrator on 2017/2/18 0018.
 */
public enum SortOrder {
    DESC,
    ASC;

    public static SortOrder forValue(String value) {
        for (SortOrder type : values()) {
            if (type.toString().equalsIgnoreCase(value))
                return type;
        }
        return null;
    }
}
