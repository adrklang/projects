package com.lhstack.pojo;

import com.lhstack.common.annotation.Aliases;

import java.util.Date;

public class Product {
    private Long pid;

    private String pname;

    private String color;

    private Double price;

    private String description;

    private String pic;

    private Integer state;

    private String version;

    @Aliases("product_date")
    private Date productDate;

    @Aliases("cname")
    private String cname;
    private Long cid;

    public String getCname() {
        return cname;
    }

    public Product setCname(String cname) {
        this.cname = cname;
        return this;
    }

    public Long getPid() {
        return pid;
    }

    public Product setPid(Long pid) {
        this.pid = pid;
        return this;
    }

    public String getPname() {
        return pname;
    }

    public Product setPname(String pname) {
        this.pname = pname;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Product setColor(String color) {
        this.color = color;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Product setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public Product setPic(String pic) {
        this.pic = pic;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public Product setState(Integer state) {
        this.state = state;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Product setVersion(String version) {
        this.version = version;
        return this;
    }

    public Date getProductDate() {
        return productDate;
    }

    public Product setProductDate(Date productDate) {
        this.productDate = productDate;
        return this;
    }

    public Long getCid() {
        return cid;
    }

    public Product setCid(Long cid) {
        this.cid = cid;
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", pic='" + pic + '\'' +
                ", state=" + state +
                ", version='" + version + '\'' +
                ", productDate=" + productDate +
                ", cid=" + cid +
                '}';
    }
}
