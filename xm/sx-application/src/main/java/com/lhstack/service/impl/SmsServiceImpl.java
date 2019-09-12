package com.lhstack.service.impl;

import com.lhstack.common.activemq.ActivemqTemplate;
import com.lhstack.service.ISmsService;
import com.lhstack.utils.ClassPathResourceUtils;
import org.springframework.jms.core.JmsTemplate;

import java.util.Map;
import java.util.Properties;

public class SmsServiceImpl implements ISmsService {

    private JmsTemplate jmsTemplate = ActivemqTemplate.getActiveMQTemplate();

    private Properties properties = ClassPathResourceUtils.getProperties("activemq.properties");

    @Override
    public void send(Map<String,String> map){
        jmsTemplate.convertAndSend(properties.getProperty("queue.sms","sx_application_sms_queue"),map);
    }
}
