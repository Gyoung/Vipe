package com.vipe.service.context;

/**
 * Created by Administrator on 2017/2/25 0025.
 */
public abstract class AbstractThreadContext<T> {

    private final ThreadLocal<T> local;

    protected AbstractThreadContext(final Class<T> clazz) {
        local = new ThreadLocal<T>() {
            @Override
            protected T initialValue() {
                try {
                    return clazz.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    protected T getLocal() {
        return local.get();
    }

    public void clean() {
        local.remove();
    }
}
