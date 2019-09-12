package com.lhstack.service.impl;

import com.lhstack.pojo.Cart;
import com.lhstack.pojo.CartItem;
import com.lhstack.pojo.User;
import com.lhstack.service.ICartService;
import com.lhstack.utils.GsonUtils;
import com.lhstack.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

public class CartServiceImpl implements ICartService {

    private static String PREFIXKEY = "cart:name:%s:username:%s:phoneNumber %s";

    @Override
    public Boolean addCart(User user, Cart cart){
        String key = String.format(PREFIXKEY, user.getName(), user.getUsername(), user.getPhoneNumber());
        Jedis jedis = JedisUtils.getJedis();
        String content = jedis.get(key);
        if(StringUtils.isNotEmpty(content)){
            CartItem cartItem = GsonUtils.createGson().fromJson(content, CartItem.class);
            Boolean add = cartItem.addCart(cart);
            if(add){
                jedis.set(key,GsonUtils.createGson().toJson(cartItem));
                return true;
            }
            return false;
        }else{
            CartItem cartItem = new CartItem();
            Boolean add = cartItem.addCart(cart);
            if(add){
                jedis.set(key,GsonUtils.createGson().toJson(cartItem));
                return true;
            }
            return false;
        }
    }

    @Override
    public Boolean delCart(User user, Cart cart){
        String key = String.format(PREFIXKEY, user.getName(), user.getUsername(), user.getPhoneNumber());
        Jedis jedis = JedisUtils.getJedis();
        String content = jedis.get(key);
        if(StringUtils.isNotEmpty(content)){
            CartItem cartItem = GsonUtils.createGson().fromJson(content,CartItem.class);
            Boolean del = cartItem.delCart(cart);
            if(del){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    @Override
    public CartItem getCartItem(User user){
        String key = String.format(PREFIXKEY, user.getName(), user.getUsername(), user.getPhoneNumber());
        Jedis jedis = JedisUtils.getJedis();
        String content = jedis.get(key);
        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,CartItem.class);
        }
        return null;
    }

    @Override
    public Boolean clearAll(User user) {
        String key = String.format(PREFIXKEY, user.getName(), user.getUsername(), user.getPhoneNumber());
        Jedis jedis = JedisUtils.getJedis();
        Long del = jedis.del(key);
        return del > 0;
    }

    @Override
    public Boolean update(User user, Long count, Long pid) {
        String key = String.format(PREFIXKEY, user.getName(), user.getUsername(), user.getPhoneNumber());
        Jedis jedis = JedisUtils.getJedis();
        String content = jedis.get(key);
        if(StringUtils.isNotEmpty(content)){
            CartItem cartItem = GsonUtils.createGson().fromJson(content,CartItem.class);
            cartItem.update(count,pid);
            jedis.set(key,GsonUtils.createGson().toJson(cartItem));
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteByPid(User user, Long pid) {
        String key = String.format(PREFIXKEY, user.getName(), user.getUsername(), user.getPhoneNumber());
        Jedis jedis = JedisUtils.getJedis();
        String content = jedis.get(key);
        if(StringUtils.isNotEmpty(content)){
            CartItem cartItem = GsonUtils.createGson().fromJson(content,CartItem.class);
            cartItem.delete(pid);
            jedis.set(key,GsonUtils.createGson().toJson(cartItem));
            return true;
        }
        return false;
    }
}
