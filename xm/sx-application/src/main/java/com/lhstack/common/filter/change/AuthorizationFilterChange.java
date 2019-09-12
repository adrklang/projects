package com.lhstack.common.filter.change;

import java.util.Map;

public interface AuthorizationFilterChange {
    /**
     * key is interceptor path
     * value is interceptor regular
     * value -> anon authc logout
     * @return
     */
    Map<String,String> getInterceptorRegulars();

    String exculdes();

    String loginPath();
    String loginSuccess();

    String loginProcess();

    String logout();
}
