package com.leyou.user.service;

import com.leyou.user.pojo.User;

public interface IUserService {
    Boolean checkData(String data, Integer type);

    void sendCode(String phone);

    void register(User user, String code);

    User queryUserByUsernameAndPassword(String username, String password);
}
