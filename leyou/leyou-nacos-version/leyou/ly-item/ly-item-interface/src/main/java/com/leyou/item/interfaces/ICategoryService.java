package com.leyou.item.interfaces;

import com.leyou.item.pojo.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> queryCategoryListByPid(Long pid);

    List<Category> queryByIds(List<Long> ids);

    List<Category> queryByBid(Long bid);
}
