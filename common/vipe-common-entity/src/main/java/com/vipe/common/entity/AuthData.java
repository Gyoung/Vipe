package com.vipe.common.entity;

import java.io.Serializable;

/**
 *  后端认证实体
 * Created by Administrator on 2017/2/25 0025.
 */
public class AuthData implements Serializable {
    /**
     * 用户标识
     */
    public String userId;
    /**
     * 菜单标识
     */
    public String userName;
    /**
     * 登陆凭据
     */
    public String credential;

    /**
     * 忽略认证
     * TODO: 忽略认证以后必须删除
     */
    private boolean ignoreCredential;


    public AuthData() {
        ignoreCredential = false;
    }

    public boolean isIgnoreCredential() {
        return ignoreCredential;
    }

    public void setIgnoreCredential(boolean ignoreCredential) {
        this.ignoreCredential = ignoreCredential;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
