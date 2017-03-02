package com.vipe.service.context;


import com.vipe.common.entity.AuthData;

/**
 * Created by zengjiyang on 2016/7/12.
 */
public class ServiceContext extends AbstractThreadContext<ServiceContext> {

    private AuthData authData;
    private ServiceContext() {
        super(ServiceContext.class);
    }

    public static ServiceContext getCurrent() {
        return Holder.getContext();
    }

    public AuthData getAuthData() {
        return authData;
    }

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }

    public String getUserId() {
        return getAuthData().getUserId();
    }

    public boolean isAuthorization() {
        return getAuthData() != null;
    }

    private static class Holder {
        private static final ServiceContext CONTEXT = new ServiceContext();

        public static ServiceContext getContext() {
            return CONTEXT;
        }
    }
}
