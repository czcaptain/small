package com.yin.annotation;

import java.lang.annotation.*;

/**
 * @author 17694
 * @date 2022/01/18
 **/

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     *
     */
    Class<? extends Annotation> value();
}
