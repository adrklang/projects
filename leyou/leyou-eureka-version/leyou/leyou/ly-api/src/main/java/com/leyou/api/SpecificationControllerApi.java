package com.leyou.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("spec")
@Api(value = "规格参数",description = "通用规格参数和特有规格参数")
public interface SpecificationControllerApi {

    @ApiOperation("根据categoryId查询规格组")
    @GetMapping("groups/{cid}")
    ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid);

    @ApiOperation("保存规格参数")
    @PostMapping("param")
    ResponseEntity<Void> saveSpecParam(@RequestBody SpecParam specParam);

    @ApiOperation("根据主键删除规格参数")
    @DeleteMapping("param/{id}")
    ResponseEntity<Void> deleteSpecParam(@PathVariable("id") Long id);

    @ApiOperation("更新规格参数")
    @PutMapping("param")
    ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam specParam);

    @GetMapping("params")
    @ApiOperation("根据categoryId或者specGroupId查询规格参数")
    ResponseEntity<List<SpecParam>> queryParamList(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
           @ApiParam(name = "searching",value = "标识是否通用规格参数") @RequestParam(value = "searching", required = false) Boolean searching);

    @GetMapping("group")
    @ApiOperation("根据categoryId查询规格组")
    ResponseEntity<List<SpecGroup>> queryListByCid(@RequestParam("cid") Long cid);

    @ApiOperation("添加规格组")
    @PostMapping("group")
    ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroup specGroup);
}
