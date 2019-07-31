package com.lhstack.content.service;

import com.lhstack.common.pojo.EUTreeNode;
import com.lhstack.common.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {
    /**
     * 查询内容分类
     * @param parentId
     * @return
     */
    List<EUTreeNode> getContentCatList(long parentId);

    TaotaoResult addContentCategory(long parentId, String name);
}






