package com.lhstack.service.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.lhstack.common.handler.BeanHandler;
import com.lhstack.common.handler.BeanListHandler;
import com.lhstack.pojo.Page;
import com.lhstack.pojo.User;
import com.lhstack.service.IUserService;
import com.lhstack.utils.GsonUtils;
import com.lhstack.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserServiceImpl implements IUserService {


    private static String HKEY = "user";
    @Override
    public Integer updateState(Long uid, Long manager){
        if(manager == 0L || manager == 1L){
            try {
                AtomicInteger update = new AtomicInteger();
                Db.use().tx(db -> {
                    update.set(db.update(Entity.create("user").set("manager", manager), Entity.create().set("uid", uid)));
                });
                JedisUtils.hdel(HKEY);
                return update.get();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    @Override
    public Integer deleteByIds(List<Long> ids){
        try {
            AtomicInteger del = new AtomicInteger();
            Db.use().tx(db -> {
                for(Long id : ids){
                    int dell = db.del("user", "uid", id);
                    del.set(del.get() + dell);
                }
            });
            JedisUtils.hdel(HKEY);
            return del.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer delete(Long id){
        try {
            AtomicInteger del = new AtomicInteger();
            Db.use().tx(db -> {
                del.set(db.del("user", "uid", id));
            });
            JedisUtils.hdel(HKEY);
            return del.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer count(){
        try {
            int count = Db.use().count(Entity.create("user"));
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer update(User user){
        try {
            AtomicInteger update = new AtomicInteger();
            Db.use().tx(db ->{
               update.set(db.update(Entity.create("user")
                       .set("name", user.getName())
                       .set("area", user.getArea())
                       .set("sex", user.getSex())
                       .set("photo", user.getPhoto()), Entity.create().set("username", user.getUsername())));

            });
            JedisUtils.hdel(HKEY);
            return update.get();
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public Integer count(String username){
        String key = "user:username:" + username;
        String content = JedisUtils.hget(HKEY, key);
        if(StringUtils.isNotEmpty(content)){
            return Integer.parseInt(content);
        }
        try {
            int count = Db.use().count(Entity.create("user").set("username", "<> " + username));
            JedisUtils.hset(HKEY,key,count);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public Integer insert(User user){
        AtomicInteger atomicInteger = new AtomicInteger(0);
        try {
            Db.use().tx(db -> {
                atomicInteger.set(db.insert(Entity.create("user")
                        .set("name", user.getName())
                        .set("username", user.getUsername())
                        .set("password", user.getPassword())
                        .set("sex", user.getSex())
                        .set("phone_number", user.getPhoneNumber())
                        .set("area", user.getArea())
                        .set("manager", user.getManager())
                        .set("salt", user.getSalt())
                        .set("create_time", user.getCreateTime())
                        .set("photo", user.getPhoto())));
            });
            JedisUtils.hdel(HKEY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atomicInteger.get();
    }


    @Override
    public User findByUsername(String username){
        String key = "findByUsername:username:" + username;
        String content = JedisUtils.hget(HKEY, key);
        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,User.class);
        }
        try {
            User user = Db.use().query("SELECT * FROM user WHERE username = ? AND 1 = 1", new BeanHandler<>(User.class), username);
            JedisUtils.hset(HKEY,key,user);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByPhone(String phone){
        String key = "findByPhone:username:" + phone;
        String content = JedisUtils.hget(HKEY, key);
        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,User.class);
        }
        try {
            User user = Db.use().query("SELECT * FROM user WHERE phone_number = ? AND 1 = 1", new BeanHandler<>(User.class), phone);
            JedisUtils.hset(HKEY,key,user);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public User findByName(String name) {
        String key = "findByName:username:" + name;
        String content = JedisUtils.hget(HKEY, key);
        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,User.class);
        }
        try {
            User user = Db.use().query("SELECT * FROM user WHERE name = ? AND 1 = 1", new BeanHandler<>(User.class), name);
            JedisUtils.hset(HKEY,key,user);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<User> findByUserPage(Long page, Long size) {
        if(page < 1){
            page = 1L;
        }
        String key = String.format("findByUserPage:page:%s:size:%s",page,size);
        String content = JedisUtils.hget(HKEY, key);
        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,Page.class);
        }
        try {
            List<User> list = Db.use().query("SELECT * FROM user WHERE 1 =1 ORDER BY create_time LIMIT ?,?", new BeanListHandler<>(User.class), (page - 1) * size, size);
            Page<User> pages = new Page<>();
            pages.setContent(list)
                    .setCurrentPage(page)
                    .setMaxTotal(count())
                    .setSize(size)
                    .setMaxPage()
                    .setIndex();
            JedisUtils.hset(HKEY,key,pages);
            return pages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<User> findByUserPage(User user, Long page, Long size){
        if(page < 1){
            page = 1L;
        }
        String key = String.format("findByUserPage:user:%s:page:%s:size:%s",user.toString(),page,size);
        String content = JedisUtils.hget(HKEY, key);
        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,Page.class);
        }
        try {

            List<User> list = Db.use().query("SELECT * FROM user WHERE 1 =1 AND username != ? AND manager < ? ORDER BY create_time LIMIT ?,?", new BeanListHandler<>(User.class), user.getUsername(), user.getManager(), (page - 1) * size, size);
            Page<User> pages = new Page<>();
            pages.setContent(list)
                    .setCurrentPage(page)
                    .setMaxTotal(count(user.getUsername()))
                    .setSize(size)
                    .setMaxPage()
                    .setIndex();
            JedisUtils.hset(HKEY,key,pages);
            return pages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
