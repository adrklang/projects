package com.leyou.api;

import com.leyou.item.pojo.Item;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("item")
@Api(value = "订单",description = "生成订单")
public interface ItemControllerApi {
    @PostMapping
    @ApiOperation("保存订单")
    ResponseEntity<Item> saveItem(Item item);
}
