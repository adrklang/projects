package com.lhstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lhstack.common.pojo.EUTreeNode;
import com.lhstack.service.ItemCatService;

import java.util.List;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @ResponseBody
    @RequestMapping("/list")
    public List<EUTreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
        List<EUTreeNode> list = itemCatService.getItemCatList(parentId);
        return list;
    }
}
