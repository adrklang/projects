package com.lhstack.configuration;

import com.lhstack.common.filter.LoginAuthorizationFilter;
import com.lhstack.pojo.User;
import com.lhstack.utils.ClassPathResourceUtils;
import com.lhstack.utils.CookieUtils;
import com.lhstack.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Properties;
public class LoginAuthorizationFilterImpl implements LoginAuthorizationFilter {
    @Override
    public User authorizationUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(AUTHORIZATION_SESSION_KEY);
        if(session.getAttribute(AUTHORIZATION_SESSION_STATE_KEY) == null || (boolean)session.getAttribute(AUTHORIZATION_SESSION_STATE_KEY) == true){
            if (attribute != null) {
                Cookie cookie = new Cookie(AUTHORIZATION_COOKIE_KEY,JwtUtils.accessToken((User) attribute));
                cookie.setMaxAge(60 * 30);
                cookie.setDomain("localhost");
                response.addCookie(cookie);
                return (User) attribute;
            } else {
                Cookie cookie = CookieUtils.getCookie(request, AUTHORIZATION_COOKIE_KEY);
                if (cookie != null) {
                    User user = JwtUtils.validToken(cookie.getValue());
                    if (user != null) {
                        cookie.setValue(JwtUtils.accessToken(user));
                        cookie.setMaxAge(60 * 30);
                        cookie.setDomain("localhost");
                        response.addCookie(cookie);
                        session.setAttribute(AUTHORIZATION_SESSION_KEY,user);
                        return user;
                    } else {
                        return null;
                    }
                } else {
                    String header = request.getHeader(AUTHORIZATION_HEADER_KEY);
                    if (StringUtils.isNotEmpty(header)) {
                        User user = JwtUtils.validToken(header);
                        if (user != null) {
                            response.setHeader(AUTHORIZATION_HEADER_KEY, JwtUtils.accessToken(user));
                            session.setAttribute(AUTHORIZATION_SESSION_KEY,user);
                            return user;
                        } else {
                            return null;
                        }
                    } else {
                        String token = request.getParameter(AUTHORIZATION_PARAMETER_KEY);
                        if (StringUtils.isNotEmpty(token)) {
                            User user = JwtUtils.validToken(token);
                            if (user != null) {
                                session.setAttribute(AUTHORIZATION_SESSION_KEY,user);
                                return user;
                            } else {
                                return null;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}
