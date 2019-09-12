package com.lhstack.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClassPathResourceUtils {
    private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    public static Properties getProperties(String fileName) {
        InputStream resourceAsStream = classLoader.getResourceAsStream(fileName);
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new NullPointerException("properties load is a null file");
        }
        return properties;
    }
}
