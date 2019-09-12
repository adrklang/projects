package com.lhstack.listener;

import com.lhstack.utils.ClassPathResourceUtils;
import com.lhstack.utils.GsonUtils;
import com.lhstack.utils.JedisUtils;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import redis.clients.jedis.Jedis;

import javax.jms.ConnectionFactory;
import javax.jms.TextMessage;
import java.util.Map;
import java.util.Properties;


public class ActivemqMessageListener {
    private Properties properties = ClassPathResourceUtils.getProperties("activemq.properties");
    public ConnectionFactory getActiveMqConnectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(properties.getProperty("broker.url","tcp://192.168.73.129:61616"));
        activeMQConnectionFactory.setUserName(properties.getProperty("username","admin"));
        activeMQConnectionFactory.setPassword(properties.getProperty("password","admin"));
        return activeMQConnectionFactory;
    }
    public MessageListenerContainer createMessageListener(){
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(getActiveMqConnectionFactory());
        messageListenerContainer.setDestination(new ActiveMQQueue(properties.getProperty("queue.sms","sx_application_sms_queue")));
        messageListenerContainer.setMessageListener(messageListener());
        return messageListenerContainer;
    }

    public SessionAwareMessageListener messageListener(){
        return (message,session) ->{
            if(message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                Jedis jedis = null;
                try{
                    Map map = GsonUtils.createGson().fromJson(textMessage.getText(), Map.class);
                    String phoneNumber = (String) map.get("phoneNumber");
                    //SMSTools.sendCode(phoneNumber, (String) map.get("value"));
                    jedis = JedisUtils.getJedis();
                    System.out.println(map);
                    jedis.setex(map.get("key").toString(),300,map.get("value").toString());
                }catch (Exception ex){
                    System.out.println("数据转换异常   " + ex);
                }finally {
                    if(jedis != null){
                        jedis.close();
                    }
                }
            }
        };
    }
}
