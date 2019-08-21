package com.leyou.page.mq;

import com.leyou.common.config.rabbitmq.RabbitMqProperties;
import com.leyou.page.service.IPageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ItemListener {
    private final IPageService pageService;

    @RabbitListener(
            bindings = @QueueBinding(
                value = @Queue(name = RabbitMqProperties.EDIT_GOODS_PAGE_QUEUE, durable = "true"),
                exchange = @Exchange(name = RabbitMqProperties.EXCHANGE_GOODS_NAME, type = ExchangeTypes.TOPIC,durable = "true"),
                key = { RabbitMqProperties.ADD_GOODS_ROUTER_KEY, RabbitMqProperties.UPDATE_ROUTER_KEY }
            )
    )
    public void listenInsertOrUpdate(Long spuId) {
        if (spuId == null) {
            log.error("listenInsertOrUpdate spuId is null");
            return;
        }
        pageService.createHtml(spuId);
        log.info("listenInsertOrUpdate excute success -- spuId -- {},page create success",spuId);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                value = @Queue(name = RabbitMqProperties.DELETE_GOODS_PAGE_QUEUE, durable = "true"),
                exchange = @Exchange(name = RabbitMqProperties.EXCHANGE_GOODS_NAME, type = ExchangeTypes.TOPIC),
                key = { RabbitMqProperties.DELETE_GOODS_KEY }
            )
    )
    public void listenDelete(Long spuId) {
        if (spuId == null) {
            log.error("listenDelete spuId is null");
            return;
        }
        // 处理消息, 对静态页进行删除
        pageService.deleteHtml(spuId);
        log.info("listenDelete excute success -- spuId -- {}",spuId);
        log.info("listenInsertOrUpdate excute success -- spuId -- {},page delete success",spuId);
    }
}
