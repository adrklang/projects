package com.lhstack.servlet.listener;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.level.Level;
import com.lhstack.utils.JedisUtils;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletRequestListenerClose implements ServletRequestListener {
    private static Log log = LogFactory.get(ServletRequestListenerClose.class);
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        if(log.isEnabled(Level.INFO)){
            log.info("servlet request stop");
        }
        JedisUtils.close();
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {

    }
}
