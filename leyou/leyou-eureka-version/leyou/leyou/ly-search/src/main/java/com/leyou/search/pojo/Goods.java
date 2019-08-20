package com.leyou.search.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Data
/**
 * replicas: 备份
 * shards: 分片
 * type: 相当于mysql里面的table
 * indexName: 相当于mysql里面的database
 */
@Document(indexName = "goods", type = "docs", shards = 1, replicas = 0)
@ApiModel(value = "商品对象",description = "对页面显示商品的封装")
public class Goods {
    @Id
    @ApiModelProperty(name = "id",value = "商品id",required = false,example = "76",dataType = "Integer")
    /**
     * spuId
     */
    private Long id;

    @ApiModelProperty(name = "all",value = "搜索字段",required = false,example = "手机",dataType = "String")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    /**
     * 所有需要被搜索的信息, 包含标题,分类甚至品牌
     */
    private String all;

    @ApiModelProperty(name = "subTitle",value = "子标题",required = false,dataType = "String")
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;

    @ApiModelProperty(hidden = true)
    private Long brandId;

    @ApiModelProperty(hidden = true)
    private Long cid1;

    @ApiModelProperty(hidden = true)
    private Long cid2;

    @ApiModelProperty(hidden = true)
    private Long cid3;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(hidden = true)
    /**多个sku价格的集合
    **/
     private Set<Long> price;


    @Field(type = FieldType.Keyword, index = false)
    /**
     * sku信息的json结构
     */
    @ApiModelProperty(hidden = true)
    private String skus;

    @ApiModelProperty(value = "可搜索的规格参数",dataType = "Map")
    /**
     *  可搜索的规格参数, key是参数名, 值是参数
     */
    private Map<String, Object> specs;
}
