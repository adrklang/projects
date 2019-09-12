package com.lhstack.pojo;

import com.lhstack.common.annotation.Aliases;

import java.util.Date;
public class User {
    private Long uid;
    private String name;
    private Boolean sex;
    @Aliases(value = "phone_number")
    private String phoneNumber;
    private String area;
    private Integer manager;
    private String username;
    private String password;
    private String photo;
    @Aliases(value = "create_time")
    private Date createTime;
    private String salt;

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public Long getUid() {
        return uid;
    }

    public User setUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getSex() {
        return sex;
    }

    public User setSex(Boolean sex) {
        this.sex = sex;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getArea() {
        return area;
    }

    public User setArea(String area) {
        this.area = area;
        return this;
    }

    public Integer getManager() {
        return manager;
    }

    public User setManager(Integer manager) {
        this.manager = manager;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public User setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", area='" + area + '\'' +
                ", manager=" + manager +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", createTime=" + createTime +
                ", salt='" + salt + '\'' +
                '}';
    }
}
