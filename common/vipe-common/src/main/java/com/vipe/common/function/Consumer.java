package com.vipe.common.function;

/**
 * Created by Eason on 2016/3/28.
 */
@FunctionalInterface
public interface Consumer<T> {
    void accept(T arg) throws Exception;
}
