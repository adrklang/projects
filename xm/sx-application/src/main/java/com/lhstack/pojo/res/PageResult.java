package com.lhstack.pojo.res;

import java.util.List;

public class PageResult<T> {
    private Integer count;
    private List<T> list;

    public Integer getCount() {
        return count;
    }

    public PageResult<T> setCount(Integer count) {
        this.count = count;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public PageResult<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "count=" + count +
                ", list=" + list +
                '}';
    }
}
