package com.leyou.search.mq;

import com.leyou.common.config.rabbitmq.RabbitMqProperties;
import com.leyou.search.service.ISearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ItemListener {

    private final ISearchService searchService;

    @RabbitListener(
            bindings = @QueueBinding(
                value = @Queue(name = RabbitMqProperties.EDIT_GOODS_SEARCH_QUEUE, durable = "true"),
                exchange = @Exchange(name = RabbitMqProperties.EXCHANGE_GOODS_NAME, type = ExchangeTypes.TOPIC),
                key = { RabbitMqProperties.ADD_GOODS_ROUTER_KEY, RabbitMqProperties.UPDATE_ROUTER_KEY }
            )
    )
    public void listenInsertOrUpdate(Long spuId) {
        if (spuId == null) {
            log.error("listenInsertOrUpdate spuId is null");
            return;
        }
        // 处理消息, 对索引库进行新增或修改
        searchService.createOrUpdateIndex(spuId);
        log.info("listenInsertOrUpdate excute success -- spuId -- {},index create success",spuId);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                value = @Queue(name = RabbitMqProperties.DELETE_GOODS_SEARCH_QUEUE, durable = "true"),
                exchange = @Exchange(name = RabbitMqProperties.EXCHANGE_GOODS_NAME, type = ExchangeTypes.TOPIC),
                key = { RabbitMqProperties.DELETE_GOODS_KEY }
            )
    )
    public void listenDelete(Long spuId) {
        if (spuId == null) {
            log.error("listenDelete spuId is null");
            return;
        }
        // 处理消息, 对索引库进行新增或修改
        searchService.deleteIndex(spuId);
        log.info("listenDelete excute success -- spuId -- {}, index delete success",spuId);
    }
}
