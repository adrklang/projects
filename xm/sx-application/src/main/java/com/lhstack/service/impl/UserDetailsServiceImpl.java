package com.lhstack.service.impl;

import com.lhstack.common.filter.UserDetailsService;
import com.lhstack.pojo.User;
import com.lhstack.service.IUserService;
import com.lhstack.utils.Md5Utils;

import java.security.NoSuchAlgorithmException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private IUserService userService = new UserServiceImpl();

    @Override
    public User loadByUser(String username, String password) {
        User user = userService.findByUsername(username);
        try {
            String encode = Md5Utils.encode(password.trim(), user.getSalt());
            if(user.getPassword().equals(encode)){
                System.out.println(user);
                return user;
            }
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return null;
    }
}
