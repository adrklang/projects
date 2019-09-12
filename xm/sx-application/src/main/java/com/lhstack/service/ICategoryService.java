package com.lhstack.service;

import com.lhstack.pojo.Category;
import com.lhstack.pojo.Page;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryService {
    Page<Category> findByPageCategory(Long page, Long size);

    Integer updateCategoryById(Category category);

    Integer deleteCategoryByIds(List<Long> cids);

    Integer deleteCategoryById(Long cid);

    Integer addCategory(Category category);

    Integer count() throws SQLException;

    Category findById(Long cid);

    List<Category> findAll();
}
