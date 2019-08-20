package com.leyou.item.controller;

import com.leyou.api.GoodsControllerApi;
import com.leyou.common.dto.CartDTO;
import com.leyou.common.vo.PageResult;
import com.leyou.item.interfaces.IGoodsService;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: cuzz
 * @Date: 2018/11/6 19:50
 * @Description:
 */
@RestController
@AllArgsConstructor
public class GoodsController implements GoodsControllerApi {

    private final IGoodsService goodsService;



    @Override
    public ResponseEntity<PageResult<Spu>> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        return ResponseEntity.ok(goodsService.querySpuPage(page, rows, saleable, key));
    }

    /**
     * 商品新增
     * @param spu
     * @return
     */
    @Override
    public ResponseEntity<Void> saveGoods(Spu spu) {
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 商品修改
     * @param spu
     * @return
     */
    @Override
    public ResponseEntity<Void> updateGoods(Spu spu){
        goodsService.updateGoods(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> deleteGoods(Long id) {
        goodsService.deleteGoods(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateGoodsState(Long spuId, String state) {
        goodsService.updateGoodsState(spuId,"putaway".equals(state));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * 根据spu的id查询详情detail
     * @param spuId
     * @return
     */
    @Override
    public ResponseEntity<SpuDetail> querySpuDetailById(Long spuId) {
        return ResponseEntity.ok(goodsService.queryDetailById(spuId));

    }

    /**
     * 根据spu查询下面的所有sku
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<List<Sku>> querySkuList(Long id){
        return ResponseEntity.ok(goodsService.querySkusBySpuId(id));
    }

    @Override
    public ResponseEntity<Spu> querySpuById(Long id) {
        return ResponseEntity.ok(goodsService.querySpuById(id));
    }

    @Override
    public ResponseEntity<List<Sku>> querySkuByIds(List<Long> ids) {
        return ResponseEntity.ok(goodsService.querySkuByIds(ids));
    }

    @Override
    public ResponseEntity<Void> decreaseStock(List<CartDTO> carts) {
        goodsService.decreaseStock(carts);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
