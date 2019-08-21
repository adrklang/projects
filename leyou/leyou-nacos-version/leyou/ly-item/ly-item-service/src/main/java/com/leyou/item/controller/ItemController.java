package com.leyou.item.controller;

import com.leyou.api.ItemControllerApi;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.interfaces.IItemService;
import com.leyou.item.pojo.Item;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ItemController implements ItemControllerApi {

    private final IItemService itemService;

    @Override
    public ResponseEntity<Item> saveItem(Item item) {
        // 校验价格
        if (item.getPrice() == null) {
            throw new LyException(ExceptionEnum.PRICE_CANNOT_BE_NULL);
        }
        item = itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
}
