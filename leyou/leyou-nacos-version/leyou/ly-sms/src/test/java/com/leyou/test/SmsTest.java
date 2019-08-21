package com.leyou.test;

import java.util.HashMap;
import java.util.Map;

import com.leyou.common.config.rabbitmq.RabbitMqProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSendSms() {
        Map<String, String> msg = new HashMap<>();
        msg.put("phone", "13668309572");
        msg.put("code", "54321");
        amqpTemplate.convertAndSend(RabbitMqProperties.EXCHANGE_GOODS_NAME, RabbitMqProperties.SMS_VERIFY_CODE_KEY, msg);
    }
}
