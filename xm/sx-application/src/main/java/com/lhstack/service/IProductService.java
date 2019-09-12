package com.lhstack.service;

import com.lhstack.pojo.Page;
import com.lhstack.pojo.Product;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IProductService {
    Integer deleteByCid(Long cid);

    Integer deleteProductByIds(List<Long> pids);

    List<Product> findByCategoryStatePages(String cname, Integer page, Integer size);

    List<Product> findByStatePages(Integer state, Integer page, Integer size);

    Integer addProduct(Product product);

    Integer deleteByProductId(Long pId);

    Page<Product> findByPage(Long page, Long size);

    Integer count();

    Integer updateByProductById(Product product);

    Page<Product> findByPageAndCondition(Long page, Long size, Map<String, String> map) throws ParseException;

    Product findByPid(Long pid);
}
