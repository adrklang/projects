package com.leyou.order.controller;

import com.leyou.order.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Api(value = "订单支付回调",description = "支付回调处理")
public class NotifyController {

    @Autowired
    private IOrderService orderService;

    @ApiOperation("监听微信回调函数")
    @PostMapping(value = "/wxpay/notify", produces = "application/xml")
    public Map<String, String> notify(@RequestBody Map<String, String> result) {
        orderService.handleNotify(result);

        log.info("[支付回调] 接收微信支付回调, 结果:{}", result);
        Map<String, String> msg = new HashMap<>();
        msg.put("return_code", "SUCCESS");
        msg.put("return_msg", "OK");
        return msg;
    }

}
