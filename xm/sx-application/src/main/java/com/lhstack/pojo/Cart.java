package com.lhstack.pojo;

import java.util.Objects;

public class Cart {

    private Product product;
    private Long count;
    private Double total;

    public Product getProduct() {
        return product;
    }

    public Cart setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public Cart setCount(Long count) {
        this.count = count;
        return this;
    }

    public Double getTotal() {
        return total;
    }

    public Cart setTotal(Double total) {
        this.total = total;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(product, cart.product) &&
                Objects.equals(count, cart.count) &&
                Objects.equals(total, cart.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, count, total);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "product=" + product +
                ", count=" + count +
                ", total=" + total +
                '}';
    }
}
