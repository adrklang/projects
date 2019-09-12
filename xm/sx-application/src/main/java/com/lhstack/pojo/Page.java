package com.lhstack.pojo;

import java.util.List;

public class Page<T> {
    /**
     * 最大记录数
     */
    private Integer maxTotal;

    /**
     * 当前页
     */
    private Long currentPage;
    /**
     * 每页显示条数
     */
    private Long size;
    /**
     * 当前索引
     */
    private Long index;

    private Long maxPage;

    /**
     * 内容
     */
    private List<T> content;

    public Page(){

    }

    public Page(List<T> content,Integer maxTotal){
        this.content = content;
        this.maxTotal = maxTotal;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public Page<T> setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
        return this;
    }

    public Page<T> setMaxPage(){
        if(this.maxTotal > 0 && size > 0){
            this.maxPage = maxTotal % size != 0 ? maxTotal / size + 1: maxTotal / size;
        }
        return this;
    }

    public Page<T> setIndex(){
        if(size > 0){
            this.index = (getCurrentPage() - 1) * size;
        }
        return this;
    }

    /**
     * 总页数
     */
    public Long getMaxPage() {
        return maxTotal % size != 0 ? maxTotal / size + 1: maxTotal / size;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public Page<T> setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public Long getSize() {
        return size;
    }

    public Page<T> setSize(Long size) {
        this.size = size;
        return this;
    }

    public Long getIndex() {
        return (getCurrentPage() - 1) * size;
    }


    public List<T> getContent() {
        return content;
    }

    public Page<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return "Page{" +
                "maxTotal=" + maxTotal +
                ", currentPage=" + currentPage +
                ", size=" + size +
                ", index=" + index +
                ", maxPage=" + maxPage +
                ", content=" + content +
                '}';
    }
}
