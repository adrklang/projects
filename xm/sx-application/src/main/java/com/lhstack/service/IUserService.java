package com.lhstack.service;

import com.lhstack.pojo.Page;
import com.lhstack.pojo.User;

import java.util.List;

public interface IUserService {
    Integer updateState(Long uid, Long manager);

    Integer deleteByIds(List<Long> ids);

    Integer delete(Long id);

    Integer count();

    Integer update(User user);

    Integer count(String username);

    Integer insert(User user);

    User findByUsername(String username);

    User findByPhone(String phone);

    User findByName(String name);

    Page<User> findByUserPage(Long page, Long size);

    Page<User> findByUserPage(User user, Long page, Long size);
}
