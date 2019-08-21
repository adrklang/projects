package com.leyou.common.config;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CacheKeyUtilsConfig {
    public String getKey(Object... target){
        List<Object> objects = Arrays.asList(target);
        return objects.toString();
    }
}
