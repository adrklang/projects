package com.leyou.api;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("brand")
@Api(value = "品牌管理接口",description = "对品牌进行增删改查")
public interface BrandControllerApi {

    /**
     *
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @ApiOperation("分页查询品牌")
    @GetMapping("page")
    ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @ApiParam(value = "当前页",name = "page",required = false,type = "Integer")
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam(value = "每页显示条数",name = "rows",required = false,type = "Integer")
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @ApiParam(value = "排序字段",name = "sortBy",required = false,type = "String")
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @ApiParam(value = "排序方式，倒排序还是正排序",name = "desc",required = false,type = "Boolean")
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @ApiParam(value = "查询关键字，首字母获取品牌名称",name = "key",required = false,type = "String")
            @RequestParam(value = "key", required = false) String key);

    @PostMapping
    @ApiOperation("保存品牌")
    ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids);

    @DeleteMapping("{id}")
    @ApiOperation("根据品牌id删除品牌")
    ResponseEntity<Void> deleteBrand(@PathVariable("id") Long bid);

    @PutMapping
    @ApiOperation("更新品牌")
    ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("cids") List<Long> cids);

    @GetMapping("cid/{cid}")
    @ApiOperation("根据分类id查询品牌")
    ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid") Long cid);

    @GetMapping("{id}")
    @ApiOperation("根据主键查询品牌")
    ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id);

    @GetMapping("list")
    @ApiOperation("根据主键集合查询品牌")
    ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}
