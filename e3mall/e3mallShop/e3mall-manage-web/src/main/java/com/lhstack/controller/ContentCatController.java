package com.lhstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lhstack.common.pojo.EUTreeNode;
import com.lhstack.common.pojo.TaotaoResult;
import com.lhstack.content.service.ContentCategoryService;

import java.util.List;

@Controller
@RequestMapping("content/category")
public class ContentCatController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @ResponseBody
    @RequestMapping("list")
    public List<EUTreeNode> getContentCatList(@RequestParam(name="id", defaultValue="0")Long parentId) {
        List<EUTreeNode> list = contentCategoryService.getContentCatList(parentId);
        return list;
    }

    /**
     * 添加分类节点
     */
    @RequestMapping(value="create", method= RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createContentCategory(Long parentId, String name) {
        //调用服务添加节点
        TaotaoResult taotaoResult = contentCategoryService.addContentCategory(parentId, name);
        return taotaoResult;
    }

}
