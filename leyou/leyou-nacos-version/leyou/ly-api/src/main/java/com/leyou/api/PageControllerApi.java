package com.leyou.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value = "页面预览",description = "生成商品详情页面预览")
public interface PageControllerApi {

    @GetMapping("items/{id}.html")
    @ApiOperation("根据SpuId返回商品详情页")
    String toItemPage(@PathVariable("id") Long spuId, Model model);
}
