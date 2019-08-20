package com.leyou.item.interfaces;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

public interface IBrandService {
    PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc,
                                       String key);

    void saveBrand(Brand brand, List<Long> cids);

    Brand queryById(Long id);

    List<Brand> queryBrandsByCid(Long cid);

    List<Brand> queryBrandByIds(List<Long> ids);

    void updateBrand(Brand brand, List<Long> cids);

    void deleteById(Long bid);
}
