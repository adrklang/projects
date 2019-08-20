package com.leyou.page.controller;

import com.leyou.api.PageControllerApi;
import com.leyou.page.service.IPageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Map;

@Controller
@AllArgsConstructor
public class PageController implements PageControllerApi {

    private final IPageService pageService;
    @Override
    public String toItemPage(Long spuId, Model model) {
        // 查询模型数据
        Map<String, Object> attributes = pageService.loadModel(spuId);
        // 准备模型数据
        model.addAllAttributes(attributes);
        // 返回视图
        return "item";
    }


}
