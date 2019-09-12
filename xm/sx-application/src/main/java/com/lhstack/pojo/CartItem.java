package com.lhstack.pojo;

import java.util.LinkedList;
import java.util.List;

public class CartItem {
    private List<Cart> list;

    public List<Cart> getList() {
        return list;
    }

    public CartItem setList(List<Cart> list) {
        this.list = list;
        return this;
    }


    public Boolean delCart(Cart cart){
        for (int i = 0; i < this.list.size(); i++) {
            if(this.list.get(i).getProduct().getPid() == cart.getProduct().getPid()){
                this.list.remove(i);
                return true;
            }
        }
        return false;
    }

    public Boolean addCart(Cart cart){
        if(this.list == null){
            synchronized (CartItem.class){
                if(this.list == null){
                    this.list = new LinkedList<>();
                }
            }
        }
        Boolean flag = false;
        for (int i = 0; i < this.list.size(); i++) {
            if(this.list.get(i).getProduct().getPid() == cart.getProduct().getPid()){
                flag = true;
            }
        }
        if(!flag){
            this.list.add(cart);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "list=" + list +
                '}';
    }

    public void update(Long count, Long pid) {
        for (int i = 0; i < this.list.size(); i++) {
            Cart cart = this.list.get(i);
            if(cart.getProduct().getPid() == pid){
                cart.setCount(count).setTotal(count * cart.getProduct().getPrice());
            }
        }
    }

    public void delete(Long pid) {
        for (int i = 0; i < this.list.size(); i++) {
            Cart cart = this.list.get(i);
            if(cart.getProduct().getPid() == pid){
                this.list.remove(i);
                return ;
            }
        }
    }
}
