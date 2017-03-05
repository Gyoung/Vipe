package com.vipe.service.annotation;

import java.lang.annotation.*;

/**
 * Created by zengjiyang on 2016/4/8.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ErrorMessages {
    ErrorMessage[] value();
}
