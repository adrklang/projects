package com.lhstack.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value();
    String method() default "";
    class RequestConst{
        public static final String METHOD_DELETE = "DELETE";
        public static final String METHOD_HEAD = "HEAD";
        public  static final String METHOD_GET = "GET";
        public static final String METHOD_OPTIONS = "OPTIONS";
        public static final String METHOD_POST = "POST";
        public  static final String METHOD_PUT = "PUT";
        public static final String METHOD_TRACE = "TRACE";
    }
}
