package com.leyou.item.service.impl;

import com.leyou.common.exception.LyException;
import com.leyou.item.pojo.Item;
import com.leyou.item.interfaces.IItemService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements IItemService {
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = LyException.class)
    @CacheEvict(cacheNames = "item",allEntries = true)
    public Item saveItem(Item item) {
        // 商品新增
        int id = new Random().nextInt(100);
        item.setId(id);
        return item;
    }
}
