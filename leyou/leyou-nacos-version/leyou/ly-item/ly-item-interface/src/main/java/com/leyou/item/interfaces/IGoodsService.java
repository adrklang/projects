package com.leyou.item.interfaces;

import com.leyou.common.dto.CartDTO;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

public interface IGoodsService {
    PageResult<Spu> querySpuPage(Integer page, Integer rows, Boolean saleable, String key);

    void deleteGoods(Long spuId);

    void saveGoods(Spu spu);

    void updateGoods(Spu spu);

    SpuDetail queryDetailById(Long spuId);

    List<Sku> querySkusBySpuId(Long spuId);

    Spu querySpuById(Long id);

    List<Sku> querySkuByIds(List<Long> ids);

    void decreaseStock(List<CartDTO> carts);

    void updateGoodsState(Long spuId, boolean equals);
}
