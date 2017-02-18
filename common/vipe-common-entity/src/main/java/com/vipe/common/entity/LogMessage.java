package com.vipe.common.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/18 0018.
 */
public class LogMessage implements Serializable {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
