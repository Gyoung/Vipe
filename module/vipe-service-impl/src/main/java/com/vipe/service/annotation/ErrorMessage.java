package com.vipe.service.annotation;

import java.lang.annotation.*;

/**
 * Created by zengjiyang on 2016/4/8.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(ErrorMessages.class)
public @interface ErrorMessage {

    /**
     * 错误代码
     *
     * @return
     */
    String code();

    /**
     * 错误消息
     *
     * @return
     */
    String message();

}
