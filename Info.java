package com;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
//TYPE是只能用到类上
@Retention(RetentionPolicy.RUNTIME)
public @interface Info {
    String value() default "tracy";
    boolean isDelete();
    int number() default 2;
}
