package com.lhstack.content.service;

import com.lhstack.common.pojo.TaotaoResult;
import com.lhstack.pojo.TbContent;

import java.util.List;

public interface ContentService {
    /**
     * 添加内容
     * @param content
     * @return
     */
    TaotaoResult addContent(TbContent content);

    /**
     * 根据id查询内容
     * @param cid
     * @return
     */
    List<TbContent> getContentListByCid(long cid);
}
