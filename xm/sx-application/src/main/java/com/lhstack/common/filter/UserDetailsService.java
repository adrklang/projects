package com.lhstack.common.filter;

import com.lhstack.pojo.User;

public interface UserDetailsService {

    User loadByUser(String username,String password);
}
