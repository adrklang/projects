package com.lhstack.pojo;

import com.lhstack.common.annotation.Aliases;

import java.util.Date;

public class Category {
    private Long cid;
    private String cname;
    private Integer state;
    @Aliases("order_number")
    private Long orderNumber;
    private String description;
    @Aliases("create_time")
    private Date createTime;

    public Long getCid() {
        return cid;
    }

    public Category setCid(Long cid) {
        this.cid = cid;
        return this;
    }

    public String getCname() {
        return cname;
    }

    public Category setCname(String cname) {
        this.cname = cname;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public Category setState(Integer state) {
        this.state = state;
        return this;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public Category setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Category setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", state=" + state +
                ", orderNumber=" + orderNumber +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
