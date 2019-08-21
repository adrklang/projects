package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.interfaces.IBrandService;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements IBrandService {


    private final BrandMapper brandMapper;


    @Override
    @Cacheable(cacheNames = "brand",key = "@cacheKeyUtilsConfig.getKey('queryBrandByPage',#p0,#p1,#p2,#p3,#p4)")
    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc,
                                              String key) {
        // 分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNoneBlank(key)) {
            // 过滤条件
            example.createCriteria()
                    .orLike("name", "%" + key + "%")
                    .orEqualTo("letter", key.toUpperCase());
        }
        // 排序
        if (StringUtils.isNoneBlank(sortBy)) {
            String order = desc ? " DESC" : " ASC";
            String orderByClause = sortBy + order;
            example.setOrderByClause(orderByClause);
        }
        // 查询
        List<Brand> list = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        // 解析分页结果
        PageInfo<Brand> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(), list);
    }


    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = LyException.class)
    @Override
    @CacheEvict(cacheNames = "brand",allEntries = true)
    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
        int count = brandMapper.insert(brand);
        if (count != 1) {
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        // 新增中间表
        for (Long cid : cids) {
            count = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (count != 1) {
                throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }
    }

    @Override
    @Cacheable(cacheNames = "brand",key = "@cacheKeyUtilsConfig.getKey('queryById',#p0)")
    public Brand queryById(Long id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if (brand == null) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brand;
    }

    @Override
    @Cacheable(cacheNames = "brand",key = "@cacheKeyUtilsConfig.getKey('queryBrandsByCid',#p0)")
    public List<Brand> queryBrandsByCid(Long cid) {
        List<Brand> list = brandMapper.queryByCategoryId(cid);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return list;
    }

    @Override
    @Cacheable(cacheNames = "brand",key = "@cacheKeyUtilsConfig.getKey('queryBrandByIds',#p0)")
    public List<Brand> queryBrandByIds(List<Long> ids) {
        List<Brand> list = brandMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return list;
    }

    @Override
    @CacheEvict(cacheNames = "brand",allEntries = true)
    public void updateBrand(Brand brand, List<Long> cids) {
        int resultBrandCount = brandMapper.updateByPrimaryKey(brand);
        if(resultBrandCount < 1){
            throw new LyException(ExceptionEnum.BRAND_UPDATE_ERROR);
        }
        int brandDeleteCount = brandMapper.deleteCategoryBrandByBid(brand.getId());
        if(brandDeleteCount < 1){
            throw new LyException(ExceptionEnum.BRAND_UPDATE_ERROR);
        }
        cids.forEach(cid ->{
            int insertCount = brandMapper.insertCategoryBrand(cid, brand.getId());
            if(insertCount < 1){
                throw new LyException(ExceptionEnum.BRAND_UPDATE_ERROR);
            }
        });
    }

    @Override
    @CacheEvict(cacheNames = "brand",allEntries = true)
    public void deleteById(Long bid) {
        int brandCategoryCount = brandMapper.deleteCategoryBrandByBid(bid);
        if(brandCategoryCount < 1){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        int brandCount = brandMapper.deleteByPrimaryKey(bid);
        if(brandCount < 1){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
    }
}
