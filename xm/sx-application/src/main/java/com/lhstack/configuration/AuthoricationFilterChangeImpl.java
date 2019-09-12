package com.lhstack.configuration;

import com.lhstack.common.filter.change.AuthorizationFilterChange;

import java.util.LinkedHashMap;
import java.util.Map;

public class AuthoricationFilterChangeImpl implements AuthorizationFilterChange {
    @Override
    public Map<String, String> getInterceptorRegulars() {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("/user/**","anon");
        map.put("/sms/**","anon");
        map.put("/product/findByPid","anon");
        map.put("/","anon");
        map.put("/index.jsp","anon");
        map.put("/admin/login.jsp","anon");
        map.put("/login.jsp","anon");
        map.put("/register.jsp","anon");
        map.put("/upload/**","anon");
        map.put("/js/**","anon");
        map.put("/css/**","anon");
        map.put("/image/**","anon");
        map.put("/admin/**","authc:admin:com.lhstack.configuration.CustomFilterImpl");
        //map.put("/admin/**","anon");//测试开启
        map.put("/**","authc");
        return map;
    }

    @Override
    public String exculdes(){
        return ".js,.css,.html,.jpg,.jpeg,.git,.png,.ico";
    }

    @Override
    public String loginPath() {
        return "/login.jsp";
    }

    @Override
    public String loginSuccess() {
        return "/";
    }

    @Override
    public String loginProcess() {
        return "/login";
    }

    @Override
    public String logout() {
        return "/logout";
    }
}
