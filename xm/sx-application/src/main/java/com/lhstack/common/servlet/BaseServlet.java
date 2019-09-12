package com.lhstack.common.servlet;

import com.lhstack.common.annotation.AnnotationParse;
import com.lhstack.common.annotation.RequestMapping;
import com.lhstack.common.annotation.RequestParam;
import com.lhstack.utils.GsonUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class BaseServlet extends GenericServlet {
    /**
     * 保存子类里面与路由匹配的方法
     */
    private Map<String, Method> map = new HashMap<>();
    private AnnotationParse parse = new AnnotationParse();
    /**
     * 初始化方法
     */
    public void initState() {
        String prefix = "";
        Class<? extends BaseServlet> clazz = this.getClass();
        if (clazz.isAnnotationPresent(WebServlet.class)) {
            WebServlet requestMapping = clazz.getAnnotation(WebServlet.class);
            String value = requestMapping.value()[0];
            prefix = value.replaceFirst("/", "");
            if (prefix.lastIndexOf("/") != -1) {
                prefix = prefix.substring(0, prefix.lastIndexOf("/"));
            }
        }
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (parse.isAnnotation(method, RequestMapping.class)) {
                RequestMapping requestMapping =  parse.getAnnotation(method, RequestMapping.class);
                String value = requestMapping.value();
                value = value.replaceFirst("/", "");
                if(value.length() > 1 && value.charAt(value.length() - 1) == '/'){
                    value = value.substring(0,value.length() - 1);
                }
                String key = prefix + "/" + value + ":" + requestMapping.method();
                if(map.containsKey(key)){
                    throw new IllegalArgumentException("request uri exist");
                }
                map.put(key , method);
            }
        }
    }

    @Override
    public void init(ServletConfig config) {
        this.initState();
    }


    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
        service((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
    }

    private void service(HttpServletRequest req, HttpServletResponse res) {
        String requestURI = req.getRequestURI().replaceFirst("/", "");
        if(requestURI.charAt(requestURI.length() - 1) == '/'){
            requestURI = requestURI.substring(0,requestURI.length() - 1);
        }
        Method method = map.get(requestURI + ":" + req.getMethod().toUpperCase());
        if(method == null){
            method = map.get(requestURI + ":");
        }
        try {
            if(method != null){
                invoke(method,req,res);
            }else{
                throw new NoSuchMethodException("没有找到该方法");
            }
        } catch (Exception ex){
            res.setStatus(500);
            ServletOutputStream outputStream = null;
            try {
                outputStream = res.getOutputStream();
                if(outputStream != null){
                    outputStream.write(GsonUtils.createGson().toJson(ex).getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(outputStream != null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 泛化调用 进行匹配的方法
     * @param method
     * @param req
     * @param res
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void invoke(Method method, HttpServletRequest req, HttpServletResponse res) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        List<Object> list = new LinkedList<>();
        for(Class c : parameterTypes){
            list.add(getParamter(method,c,req,res));
        }
        method.invoke(this,list.toArray());
    }

    /**
     *  返回与c匹配的对象,与参数绑定
     * @param method
     * @param c
     * @param req
     * @param res
     * @return
     */
    private Object getParamter(Method method,Class c, HttpServletRequest req, HttpServletResponse res) {
        if (c == HttpSession.class) {
            return req.getSession();
        } else if (c == Cookie[].class) {
            return req.getCookies();
        } else if (c == HttpServletRequest.class) {
            return req;
        } else if (c == HttpServletResponse.class) {
            return res;
        } else if (parse.isAnnotation(method, c, RequestParam.class)) {
            RequestParam annotation = (RequestParam) parse.getAnnotation(method, c, RequestParam.class);
            String key = annotation.value();
            String parameter = req.getParameter(key);
            if (StringUtils.isBlank(parameter)) {
                parameter = annotation.defaultValue();
                if (StringUtils.isBlank(parameter)) {
                    if (annotation.require()) {
                        throw new IllegalArgumentException(key + " -----  is require");
                    }
                }
            }
            /**
             * 解析使用注解的参数
             */
            return parse.parseParameter(c, parameter);
        }else if(c == Map.class){
            /**
             * 将数据转换成map
             */
            return mapBinding(req);
        }else {
            /**
             * 将数据转换成对象
             */
            return objectBind(c,req);
        }
    }

    /**
     * map绑定
     * @param req
     * @return
     */
    private Object mapBinding(HttpServletRequest req) {
        Map map = new HashMap();
        Enumeration<String> parameterNames = req.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String key = parameterNames.nextElement();
            map.put(key,req.getParameter(key));
        }
        return map;
    }

    /**
     * 对象绑定
     * @param c
     * @param req
     * @return
     */
    private Object objectBind(Class c, HttpServletRequest req){
        try {
            Object o = c.newInstance();
            Enumeration<String> parameterNames = req.getParameterNames();
            while(parameterNames.hasMoreElements()){
                String key = parameterNames.nextElement();
                try{
                    Field field = c.getDeclaredField(key);
                    if(ObjectUtils.isNotEmpty(field)){
                        field.setAccessible(true);
                        field.set(o,parse.parseParameter(field.getType(),req.getParameter(key)));
                    }
                }catch (Exception ex){

                }
            }
            return o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
