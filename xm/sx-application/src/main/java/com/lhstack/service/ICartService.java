package com.lhstack.service;

import com.lhstack.pojo.Cart;
import com.lhstack.pojo.CartItem;
import com.lhstack.pojo.User;

public interface ICartService {
    Boolean addCart(User user, Cart cart);

    Boolean delCart(User user, Cart cart);

    CartItem getCartItem(User user);

    Boolean clearAll(User user);

    Boolean update(User user, Long count, Long pid);

    Boolean deleteByPid(User user, Long pid);
}
