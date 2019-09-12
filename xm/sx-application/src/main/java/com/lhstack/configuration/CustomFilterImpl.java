package com.lhstack.configuration;

import com.lhstack.common.filter.CustomFilter;
import com.lhstack.pojo.User;
import com.lhstack.pojo.res.Resp;
import com.lhstack.utils.GsonUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomFilterImpl implements CustomFilter {
    /**
     * true 表示拦截
     * false 表示不拦截
     * @param request
     * @param response
     * @param authorizedUser
     * @return
     */
    @Override
    public boolean doFilter(HttpServletRequest request, HttpServletResponse response, Object authorizedUser) {
        if(authorizedUser == null){
            out(response,Resp.builder().setMessage("你没有登录，请先登录").setState(false).setStatus(403));
            return true;
        }else{
            User user = (User) authorizedUser;
            if((user.getManager() < 1)){
                out(response,Resp.builder().setMessage("你没有权限访问，请向超级管理员申请管理员权限").setState(false).setStatus(403));
                return true;
            }
           return false;
        }
    }
    public void out(HttpServletResponse response,Resp resp){
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(GsonUtils.createGson().toJson(resp).getBytes());
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
