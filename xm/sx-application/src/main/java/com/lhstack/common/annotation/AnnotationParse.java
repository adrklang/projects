package com.lhstack.common.annotation;

import com.lhstack.utils.ClassPathResourceUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class AnnotationParse {


    private Properties properties = ClassPathResourceUtils.getProperties("application.properties");
    /**
     *  获取方法上与annotationType 匹配的注解
     * @param method
     * @param annotationType
     * @return
     */
    public <T> T getAnnotation(Method method, Class<T> annotationType) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == annotationType) {
                return (T) annotation;
            }
        }
        return null;
    }


    /**
     * 判断方法里面与clazz匹配的字段是否存在 c 注解
     * @param method
     * @param clazz
     * @param c
     * @return
     */
    public boolean isAnnotation(Method method,Class clazz, Class c) {
        Parameter[] parameters = method.getParameters();
        for(Parameter parameter : parameters){
            if(parameter.getType() == clazz){
                return parameter.isAnnotationPresent(c);
            }
        }
        return false;
    }


    /**
     * 判断annotations里面是否存在 c
     * @param annotations
     * @param c
     * @return
     */
    public boolean isAnnotation(Annotation[] annotations,Class c){
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == c) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param method
     * @param annotationType
     * @return
     */
    public boolean isAnnotation(Method method, Class annotationType) {
        Annotation[] annotations = method.getAnnotations();
        return isAnnotation(annotations,annotationType);
    }


    /**
     * 获取method里面与clazz匹配 并且存在 c 的注解
     * @param method 调用的方法
     * @param clazz 字段类型
     * @param c 注解类型
     * @return
     */
    public Annotation getAnnotation(Method method,Class clazz, Class c) {
        Parameter[] parameters = method.getParameters();
        for(Parameter parameter : parameters){
            if(parameter.getType() == clazz){
                if(parameter.isAnnotationPresent(c)){
                    return parameter.getAnnotation(c);
                }
            }
        }
        return null;
    }

    /**
     *  解析字符串，转换成与c匹配的类型
     * @param c
     * @param parameter
     * @return
     */
    public Object parseParameter(Class c, String parameter) {
        if(c == String.class){
            return parameter;
        }else if(c == Boolean.class || c == boolean.class){
            return Boolean.parseBoolean(parameter);
        }else if(c == Integer.class || c == int.class){
            return Integer.parseInt(parameter);
        }else if(c == Short.class || c == short.class){
            return Short.parseShort(parameter);
        }else if(c == Long.class || c == long.class){
            return Long.parseLong(parameter);
        }else if(c == Byte.class || c == byte.class){
            return Byte.parseByte(parameter);
        }else if(c == char.class || c == Character.class){
            return parameter.charAt(0);
        }else if(c == double.class || c == Double.class){
            return Double.parseDouble(parameter);
        }else if(c == float.class || c == Float.class){
            return Float.parseFloat(parameter);
        }else if(c == Date.class){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(properties.getProperty("date.format.patten","yyyy-MM-dd HH:mm:ss"));
            try {
                return simpleDateFormat.parse(parameter);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }
}
