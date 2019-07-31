package com.lhstack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lhstack.common.pojo.TaotaoResult;
import com.lhstack.content.service.ContentService;
import com.lhstack.pojo.TbContent;

@Controller
public class ContentController   {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value="/content/save", method= RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addContent(TbContent content) {
        //调用服务把内容数据保存到数据库
        TaotaoResult taotaoResult = contentService.addContent(content);
        return taotaoResult;
    }

}
