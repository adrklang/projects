package com.lhstack.common.handler;

import cn.hutool.db.handler.RsHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanHandler<T> extends BaseHandler<T> implements RsHandler<T> {

    private Class<T> clazz;
    @Override
    public T handle(ResultSet resultSet) throws SQLException {

        return beanBind(resultSet);
    }

    private T beanBind(ResultSet resultSet) {
        try {
            T t = clazz.newInstance();
            Field[] declaredFields = clazz.getDeclaredFields();
           while(resultSet.next()){
               for(Field field : declaredFields){
                   field.setAccessible(true);
                   invoke(t,field,resultSet);
               }
               return t;
           }
           return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BeanHandler(Class<T> clazz){
        this.clazz = clazz;
    }
}
