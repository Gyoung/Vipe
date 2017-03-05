package com.vipe.service.annotation;

import java.lang.annotation.*;

/**
 * Created by qiuyungen on 2016/6/20.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuthrzation {
}
