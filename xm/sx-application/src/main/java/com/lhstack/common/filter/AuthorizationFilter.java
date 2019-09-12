package com.lhstack.common.filter;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.level.Level;
import com.lhstack.common.filter.change.AuthorizationFilterChange;
import com.lhstack.pojo.User;
import com.lhstack.utils.ClassPathResourceUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * configuration is class 类全限定名
 */
public class AuthorizationFilter implements Filter {

    private AuthorizationFilterChange authorizationFilterChange;
    private LoginAuthorizationFilter loginAuthorizationFilter;
    private static Properties properties = ClassPathResourceUtils.getProperties("encoding.properties");
    private static Log log = LogFactory.get(AuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String configuration = filterConfig.getInitParameter("configuration");
        if (StringUtils.isEmpty(configuration)) {
            throw new NullPointerException("configuration 参数为空");
        }
        try {
            Class clazz = Class.forName(configuration);
            if (AuthorizationFilterChange.class.isAssignableFrom(clazz)) {
                authorizationFilterChange = (AuthorizationFilterChange) clazz.newInstance();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        String loginFilter = filterConfig.getInitParameter("loginFilter");
        if (StringUtils.isEmpty(loginFilter)) {
            throw new NullPointerException("loginFilter 参数为空");
        }
        try {
            Class clazz = Class.forName(loginFilter);
            if (LoginAuthorizationFilter.class.isAssignableFrom(clazz)) {
                loginAuthorizationFilter = (LoginAuthorizationFilter) clazz.newInstance();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public void initCharacter(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
        req.setCharacterEncoding(properties.getProperty("req.charset"));
        res.setContentType(properties.getProperty("resp.contextCharset"));
        res.setCharacterEncoding(properties.getProperty("resp.charset"));
        if (log.isEnabled(Level.INFO)) {
            log.info("req-charset -> {},resp-charset -> {},resp-contextCharset -> {}", properties.getProperty("req.charset"), properties.getProperty("resp.contextCharset"), properties.getProperty("resp.charset"));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }


    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        initCharacter(request, response);
        String uri = request.getRequestURI();
        if(!logoutFilterHandler(request, response, uri)){
            if (!filterStaticFile(uri)) {
                if (!filterAnon(uri)) {
                    if (!filterAuthc(uri)) {
                        try{
                            User user = loginAuthorizationFilter.authorizationUser(request, response);
                            if (user == null) {
                                try{
                                    response.sendRedirect(request.getContextPath() + authorizationFilterChange.loginPath());
                                    return;
                                }catch (Exception ex){

                                }
                            } else {
                                if (!customFilter(uri, request, response)) {
                                    filterChain.doFilter(request, response);
                                }
                                return;
                            }
                        }catch (Exception ex){

                        }
                    }
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    private boolean customFilter(String uri, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> interceptorRegulars = authorizationFilterChange.getInterceptorRegulars();
        Set<String> keys = interceptorRegulars.keySet();
        for (String key : keys) {
            if (key.indexOf("**") != -1) {
                String s = key.replace("**", "");
                int length = s.length();
                String s1 = interceptorRegulars.get(key);
                if (uri.substring(0, length > uri.length() ? uri.length() : length).equals(s) && s1.startsWith("authc:admin:")) {
                    String replace = s1.replace("authc:admin:", "");
                    try {
                        Class<?> clazz = Class.forName(replace);
                        CustomFilter customFilter = (CustomFilter) clazz.getConstructor().newInstance();
                        return customFilter.doFilter(request, response, request.getSession().getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_KEY));
                    } catch (ClassNotFoundException e) {
                        throw new NullPointerException("class " + replace + " 不存在");
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException("authc:admin:" + replace + "不是ConstmerFilter的子类");
                    } catch (InstantiationException e) {
                        throw new InstantiationError("class " + replace + " 必须提供空参构造函数");
                    } catch (NoSuchMethodException e) {
                        throw new NullPointerException("authc:admin:" + replace + "不是ConstmerFilter的子类");
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            } else {
                int length = uri.length();
                String s1 = interceptorRegulars.get(key);
                if (uri.substring(0, length > uri.length() ? uri.length() : length).equals(key) && s1.equals("authc:admin:")) {
                    String replace = s1.replace("authc:admin:", "");
                    try {
                        Class<?> clazz = Class.forName(replace);
                        CustomFilter customFilter = (CustomFilter) clazz.getConstructor().newInstance();
                        return customFilter.doFilter(request, response, request.getSession().getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_KEY));
                    } catch (ClassNotFoundException e) {
                        throw new NullPointerException("class " + replace + " 不存在");
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException("authc:admin:" + replace + "不是ConstmerFilter的子类");
                    } catch (InstantiationException e) {
                        throw new InstantiationError("class " + replace + " 必须提供空参构造函数");
                    } catch (NoSuchMethodException e) {
                        throw new NullPointerException("authc:admin:" + replace + "不是ConstmerFilter的子类");
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            }
        }

        return false;
    }

    private boolean logoutFilterHandler(HttpServletRequest request, HttpServletResponse response, String uri) throws IOException {
        if (uri.equals(authorizationFilterChange.logout())) {
            request.getSession().setAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_STATE_KEY,false);
            response.sendRedirect(request.getContextPath() + authorizationFilterChange.loginPath());
            return true;
        }
        return false;
    }

    private boolean filterAuthc(String uri) {
        Map<String, String> interceptorRegulars = authorizationFilterChange.getInterceptorRegulars();
        Set<String> keys = interceptorRegulars.keySet();
        for (String key : keys) {
            if (key.indexOf("**") != -1) {
                String s = key.replace("**", "");
                int length = s.length();
                String s1 = interceptorRegulars.get(key);
                if (uri.substring(0, length > uri.length() ? uri.length() : length).equals(s) && s1.equals("authc")) {
                    return false;
                }
            } else {
                int length = uri.length();
                String s1 = interceptorRegulars.get(key);
                if (uri.substring(0, length > uri.length() ? uri.length() : length).equals(key) && s1.equals("authc")) {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean filterAnon(String uri) {
        Map<String, String> interceptorRegulars = authorizationFilterChange.getInterceptorRegulars();
        Set<String> keys = interceptorRegulars.keySet();
        for (String key : keys) {
            if (key.indexOf("**") != -1) {
                String s = key.replace("**", "");
                int length = s.length();
                String s1 = interceptorRegulars.get(key);
                if (uri.substring(0, length > uri.length() ? uri.length() : length).equals(s) && s1.equals("anon")) {
                    return true;
                }
            } else {
                int length = uri.length();
                String s1 = interceptorRegulars.get(key);
                if (uri.substring(0, length > uri.length() ? uri.length() : length).equals(key) && s1.equals("anon")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean filterStaticFile(String uri) {
        if (uri.lastIndexOf(".") != -1) {
            String exculdes = authorizationFilterChange.exculdes();
            if (exculdes.contains(uri.substring(uri.lastIndexOf(".") + 1))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
