package com.lhstack.common.filter;

import com.lhstack.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginAuthorizationFilter {
    User authorizationUser(HttpServletRequest request, HttpServletResponse response);
    public static String AUTHORIZATION_SESSION_KEY = "authorization_session_key";

    public static String AUTHORIZATION_COOKIE_KEY = "authorization_cookie_key";

    public static String AUTHORIZATION_HEADER_KEY = "authorization_header_key";

    public static String AUTHORIZATION_PARAMETER_KEY = "authorization_parameter_key";
    public static String AUTHORIZATION_SESSION_STATE_KEY = "authorization_session_state_key";
}
