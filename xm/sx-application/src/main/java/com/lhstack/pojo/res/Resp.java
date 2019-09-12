package com.lhstack.pojo.res;

public class Resp {
    private Integer status;
    private Boolean state;
    private String message;

    public Integer getStatus() {
        return status;
    }

    public Resp setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Boolean getState() {
        return state;
    }

    public Resp setState(Boolean state) {
        this.state = state;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Resp setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Resp{" +
                "status=" + status +
                ", state=" + state +
                ", message='" + message + '\'' +
                '}';
    }

    public static Resp builder(){
        return new Resp();
    }
}
