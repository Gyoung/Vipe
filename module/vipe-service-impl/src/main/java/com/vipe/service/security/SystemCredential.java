package com.vipe.service.security;

import com.vipe.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zengjiyang on 2016/3/28.
 */
public final class SystemCredential {

    private static final ConcurrentHashMap<String, CredentialItem> credentialMap = new ConcurrentHashMap<String, CredentialItem>();
    private static final Logger logger = LoggerFactory.getLogger(SystemCredential.class);


    public static String createCredential(String userId, String userName, int expiresTime) {
        String credentialKey = getCredentialKey(userId, userName);
        logger.info("add user session,userName:" + userName);
        credentialMap.put(credentialKey, new CredentialItem(userId, userName, credentialKey, expiresTime));
        return credentialKey;
    }

    public static boolean clearExpiresCredential() {
        List<String> expires = new ArrayList<>();
        credentialMap.values().forEach(v -> {
            long timeDiff = new Date().getTime() - v.getActivateTime().getTime();
            if (timeDiff > v.getExpiresTime()) {
                expires.add(v.getCredential());
                logger.info("remove user session,userName:" + v.getUserName());
            }
        });
        expires.forEach(e -> {
            credentialMap.remove(e);
        });
        return true;
    }

    public static boolean checkCredential(String userId, String userName, String credential) {
        if (StringUtil.isNullOrWhiteSpace(credential) || !credentialMap.containsKey(credential)
                || StringUtil.isNullOrWhiteSpace(userId))
            return false;
        if (!credential.equals(getCredentialKey(userId, userName))) {
            return false;
        }
        logger.info("check user credential success,userName:" + userName);
        //重新设置超时时间
        CredentialItem item = credentialMap.get(credential);
        item.setActivateTime(new Date());
        return true;
    }

    private static String getCredentialKey(String userId, String userName) {
        return StringUtil.toMD5(String.join("zoe", userName, StringUtil.toMD5(userId), userName));
    }

    final static class CredentialItem {
        public String userId;
        public String userName;
        public String credential;
        public Date createAt;
        public Date activateTime;
        public int expiresTime;


        public CredentialItem(String userId, String userName, String credential, int expiresTime) {
            this.userId = userId;
            this.userName = userName;
            this.credential = credential;
            this.expiresTime = expiresTime;
            this.createAt = new Date();
            this.activateTime = new Date();
        }


        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCredential() {
            return credential;
        }

        public void setCredential(String credential) {
            this.credential = credential;
        }

        public Date getCreateAt() {
            return createAt;
        }

        public void setCreateAt(Date createAt) {
            this.createAt = createAt;
        }

        public Date getActivateTime() {
            return activateTime;
        }

        public void setActivateTime(Date activateTime) {
            this.activateTime = activateTime;
        }

        public int getExpiresTime() {
            return expiresTime;
        }

        public void setExpiresTime(int expiresTime) {
            this.expiresTime = expiresTime;
        }
    }
}
