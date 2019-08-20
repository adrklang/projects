package com.leyou.item.service.impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.interfaces.ICategoryService;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    @Cacheable(cacheNames = "category",key = "@cacheKeyUtilsConfig.getKey('queryCategoryListByPid',#p0)")
    public List<Category> queryCategoryListByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> list = categoryMapper.select(category);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }

    @Override
    @Cacheable(cacheNames = "category",key = "@cacheKeyUtilsConfig.getKey('queryByIds',#p0)")
    public List<Category> queryByIds(List<Long> ids) {
        final List<Category> list = categoryMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }

    @Override
    @Cacheable(cacheNames = "category",key = "@cacheKeyUtilsConfig.getKey('queryByBid',#p0)")
    public List<Category> queryByBid(Long bid) {
        return categoryMapper.findByBid(bid);
    }
}
