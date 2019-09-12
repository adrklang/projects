package com.lhstack.servlet;

import com.google.gson.Gson;
import com.lhstack.common.servlet.BaseServlet;
import com.lhstack.pojo.res.Resp;
import com.lhstack.utils.GsonUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbstractServlet extends BaseServlet {

    protected void out(HttpServletResponse response, Resp resp){
        Gson gson = GsonUtils.createGson();
        ServletOutputStream out = null;
        try {
            response.setStatus(resp.getStatus());
            out = response.getOutputStream();
            out.write(gson.toJson(resp).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
