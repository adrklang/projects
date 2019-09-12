package com.lhstack.common.activemq;

import com.lhstack.utils.ClassPathResourceUtils;
import com.lhstack.utils.GsonUtils;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Properties;

public class ActivemqTemplate {

    private Properties properties = ClassPathResourceUtils.getProperties("activemq.properties");

    private static ActivemqTemplate activemqTemplate = null;

    static {
        activemqTemplate = new ActivemqTemplate();
    }

    private JmsTemplate jmsTemplate;

    public ConnectionFactory getActiveMqConnectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(properties.getProperty("broker.url","tcp://192.168.73.129:61616"));
        activeMQConnectionFactory.setUserName(properties.getProperty("username","admin"));
        activeMQConnectionFactory.setPassword(properties.getProperty("password","admin"));
        return activeMQConnectionFactory;
    }

    public void initJmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate(getActiveMqConnectionFactory());
        jmsTemplate.setMessageConverter(new MessageConverter() {
            @Override
            public Message toMessage(Object o, Session session) throws JMSException, MessageConversionException {
                ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
                activeMQTextMessage.setText(GsonUtils.createGson().toJson(o));
                return activeMQTextMessage;
            }

            @Override
            public Object fromMessage(Message message) throws JMSException, MessageConversionException {
                return new String(message.getJMSCorrelationIDAsBytes());
            }
        });
        this.jmsTemplate = jmsTemplate;
    }

    public ActivemqTemplate(){
        this.initJmsTemplate();
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public static JmsTemplate getActiveMQTemplate(){
        return activemqTemplate.getJmsTemplate();
    }
}
