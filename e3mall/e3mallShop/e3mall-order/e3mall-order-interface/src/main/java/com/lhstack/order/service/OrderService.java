package com.lhstack.order.service;

import com.lhstack.common.pojo.TaotaoResult;
import com.lhstack.order.pojo.OrderInfo;

public interface OrderService {
    TaotaoResult createOrder(OrderInfo orderInfo);
}
