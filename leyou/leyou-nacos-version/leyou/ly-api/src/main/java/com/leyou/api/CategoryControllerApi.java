package com.leyou.api;

import com.leyou.item.pojo.Category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(value = "商品分类",description = "查询商品分类")
@RequestMapping("category")
public interface CategoryControllerApi {

    @ApiOperation("根据parentId查询子商品分类")
    @GetMapping("list")
    ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid") Long pid);

    @ApiOperation("根据主键集合查询商品分类")
    @GetMapping("list/ids")
    ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids);

    @GetMapping("bid/{bid}")
    ResponseEntity<List<Category>> queryCategoryByBid(@PathVariable("bid") Long bid);
}
