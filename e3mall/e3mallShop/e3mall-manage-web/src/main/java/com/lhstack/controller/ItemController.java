package com.lhstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.lhstack.common.pojo.EUDataGridResult;
import com.lhstack.common.pojo.TaotaoResult;
import com.lhstack.pojo.TbItem;
import com.lhstack.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getItemList(@RequestParam(defaultValue="1")Integer page,@RequestParam(defaultValue="30")Integer rows){
        EUDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping(value="/save",method= RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception{
        TaotaoResult result = itemService.createItem(item,desc,itemParams);
        return result;
    }

    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }
}
