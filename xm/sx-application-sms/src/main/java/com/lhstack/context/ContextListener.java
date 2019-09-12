package com.lhstack.context;

import com.lhstack.listener.ActivemqMessageListener;
import org.springframework.jms.listener.MessageListenerContainer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private ActivemqMessageListener activemqMessageListener = new ActivemqMessageListener();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        MessageListenerContainer messageListener = activemqMessageListener.createMessageListener();
        messageListener.start();
        System.out.println("短信服务启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
