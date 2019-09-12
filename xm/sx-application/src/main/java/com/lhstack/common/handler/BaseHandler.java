package com.lhstack.common.handler;

import com.lhstack.common.annotation.Aliases;
import com.lhstack.common.annotation.Transient;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BaseHandler<T> {

    protected void invoke(T t, Field field, ResultSet resultSet) throws SQLException, IllegalAccessException {
        if(field.isAnnotationPresent(Aliases.class)){
            Aliases aliases = field.getAnnotation(Aliases.class);
            String key = aliases.value();
            field.set(t,parameterParseConverter(resultSet,key,field));
        }else if(field.isAnnotationPresent(Transient.class)){
            field.set(t,null);
        }else{
            field.set(t,parameterParseConverter(resultSet,field.getName(),field));
        }
    }

    private Object parameterParseConverter(ResultSet resultSet, String key, Field field) throws SQLException {
        Class c = field.getType();
        try{
            if(c == String.class){
                return resultSet.getString(key);
            }else if(c == Boolean.class || c == boolean.class){
                return resultSet.getBoolean(key);
            }else if(c == Integer.class || c == int.class){
                return resultSet.getInt(key);
            }else if(c == Short.class || c == short.class){
                return resultSet.getShort(key);
            }else if(c == Long.class || c == long.class){
                return resultSet.getLong(key);
            }else if(c == Byte.class || c == byte.class){
                return resultSet.getByte(key);
            }else if(c == char.class || c == Character.class){
                return resultSet.getString(key).charAt(0);
            }else if(c == double.class || c == Double.class){
                return resultSet.getDouble(key);
            }else if(c == float.class || c == Float.class){
                return resultSet.getFloat(key);
            }else if(c == byte[].class || c == Byte[].class){
                return resultSet.getBytes(key);
            }else if(c == Date.class || c == java.sql.Date.class){
                return resultSet.getDate(key);
            }
        }catch (Exception ex){
            return null;
        }
        return null;
    }
}
