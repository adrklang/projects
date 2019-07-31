package com.lhstack.cart.service;

import com.lhstack.common.pojo.TaotaoResult;
import com.lhstack.pojo.TbItem;

import java.util.List;

public interface CartService {
    TaotaoResult addCart(long userId, long itemId, int num);
    TaotaoResult mergeCart(long userId, List<TbItem> itemList);
    List<TbItem> getCartList(long userId);
    TaotaoResult updateCartNum(long userId, long itemId, int num);
    TaotaoResult deleteCartItem(long userId, long itemId);
    TaotaoResult clearCartItem(long userId);
}
