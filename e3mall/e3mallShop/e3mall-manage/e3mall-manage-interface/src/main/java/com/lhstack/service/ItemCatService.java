package com.lhstack.service;

import com.lhstack.common.pojo.EUTreeNode;

import java.util.List;

public interface ItemCatService {
    List<EUTreeNode> getItemCatList(long parentId);
}
