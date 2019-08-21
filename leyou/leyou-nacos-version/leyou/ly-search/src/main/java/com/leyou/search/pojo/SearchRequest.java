package com.leyou.search.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel(value = "搜索对象",description = "对搜索参数的封装")
public class SearchRequest {
    /**
     *  搜索字段
     */
    @ApiModelProperty(value = "搜索关键字",dataType = "String",required = false)
    private String key;
    /**
     *  当前页
     */
    @ApiModelProperty(value = "当前页",dataType = "String",required = false)
    private Integer page;

    private static final int DEFAULT_SIZE = 20;
    private static final int DEFAULT_PAGE = 1;

    @ApiModelProperty(value = "过滤参数",dataType = "Map",required = false)
    private Map<String, String> filter;

    public Map<String, String> getFilter() {
        return filter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPage() {
        if (page == null) {
            return DEFAULT_PAGE;
        }
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public int getSize() {
        return DEFAULT_SIZE;
    }
}
