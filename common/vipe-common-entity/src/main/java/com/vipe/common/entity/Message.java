package com.vipe.common.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/18 0018.
 */
public class Message implements Serializable {
    /**
     * 消息id
     */
    private String id;
    /**
     * 消息内容 content
     */
    private String content;

    /**
     * 获取消息id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置消息id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取消息内容 content
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容 content
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

}
