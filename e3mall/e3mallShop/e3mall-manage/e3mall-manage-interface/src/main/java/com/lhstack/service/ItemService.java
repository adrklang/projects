package com.lhstack.service;

import com.lhstack.common.pojo.EUDataGridResult;
import com.lhstack.common.pojo.TaotaoResult;
import com.lhstack.pojo.TbItem;
import com.lhstack.pojo.TbItemDesc;

public interface ItemService {
    EUDataGridResult getItemList(Integer page, Integer rows);

    TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception;

    TbItem getItemById(long itemId);

    TbItemDesc getItemDescById(long itemId);
}
