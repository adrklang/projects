package com.leyou.api;

import com.leyou.common.dto.CartDTO;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品",description = "商品的增删改查")
public interface GoodsControllerApi {


    @GetMapping("spu/page")
    @ApiOperation("分页查询商品")
    ResponseEntity<PageResult<Spu>> querySpuByPage(
            @ApiParam(name = "page",value = "当前页",required = false,type = "Integer",defaultValue = "1",example = "1")
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam(value = "每页显示条数",name = "rows",required = false,type = "Integer",defaultValue = "5",example = "5")
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @ApiParam(value = "是否可出售的商品",name = "saleable",required = false,type = "Boolean",defaultValue = "true",example = "true")
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @ApiParam(value = "搜索关键字",name = "key",required = false,type = "String",defaultValue = "",example = "手机")
            @RequestParam(value = "key", required = false) String key);

    @ApiOperation("添加商品")
    @PostMapping("goods")
    ResponseEntity<Void> saveGoods(@RequestBody Spu spu);

    @PutMapping("goods")
    @ApiOperation("更新商品")
    ResponseEntity<Void> updateGoods(@RequestBody Spu spu);

    @DeleteMapping("goods/{id}")
    ResponseEntity<Void> deleteGoods(@PathVariable("id") Long id);


    @PutMapping("goods/{spuId}")
    @ApiOperation("修改商品状态")
    ResponseEntity<Void> updateGoodsState(@PathVariable("spuId") Long spuId,@RequestParam("state") String state);

    @GetMapping("spu/detail/{id}")
    @ApiOperation("根据商品id查询商品详情")
    ResponseEntity<SpuDetail> querySpuDetailById(@PathVariable("id") Long spuId);

    @GetMapping("/sku/list")
    @ApiOperation("根据spuId查询规格参数")
    ResponseEntity<List<Sku>> querySkuList(@RequestParam("id") Long id);

    @GetMapping("spu/{id}")
    @ApiOperation("根据spuId查询商品信息")
    ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id);

    @GetMapping("sku/list/ids")
    @ApiOperation("根据skuId集合查询商品规格参数")
    ResponseEntity<List<Sku>> querySkuByIds(@RequestParam("ids") List<Long> ids);

    @PostMapping("stock/decrease")
    @ApiOperation("减少库存")
    ResponseEntity<Void> decreaseStock(@RequestBody List<CartDTO> carts);
}
