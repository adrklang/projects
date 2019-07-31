package com.lhstack.search.mapper;

import com.lhstack.common.pojo.SearchItem;

import java.util.List;

public interface ItemMapper {
    List<SearchItem> getItemList();

    SearchItem getItemById(long itemId);
}
