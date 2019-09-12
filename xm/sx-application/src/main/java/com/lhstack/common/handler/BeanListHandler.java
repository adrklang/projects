package com.lhstack.common.handler;

import cn.hutool.db.handler.RsHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanListHandler<T> extends BaseHandler<T> implements RsHandler<List<T>> {

    private Class<T> clazz;
    @Override
    public List<T> handle(ResultSet resultSet) throws SQLException {

        return beanBind(resultSet);
    }

    private List<T> beanBind(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
           while(resultSet.next()){
               T t = clazz.newInstance();
               Field[] declaredFields = clazz.getDeclaredFields();
               for(Field field : declaredFields){
                   field.setAccessible(true);
                   invoke(t,field,resultSet);
               }
               list.add(t);
           }
            return list.size() > 0 ? list : null;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public BeanListHandler(Class<T> clazz){
        this.clazz = clazz;
    }
}
