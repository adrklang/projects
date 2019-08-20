package com.leyou.item.controller;

import com.leyou.api.CategoryControllerApi;
import com.leyou.item.interfaces.ICategoryService;
import com.leyou.item.pojo.Category;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController implements CategoryControllerApi {

    private final ICategoryService categoryService;

    /**
     * 根据父节点id查询商品分类
     */
    @Override
    public ResponseEntity<List<Category>> queryCategoryListByPid(Long pid) {
        return ResponseEntity.ok(categoryService.queryCategoryListByPid(pid));
    }

    /**
     * 根据id查询商品分类
     * @param ids
     * @return
     */
    @Override
    public ResponseEntity<List<Category>> queryCategoryByIds(List<Long> ids) {
        return ResponseEntity.ok(categoryService.queryByIds(ids));
    }

    @Override
    public ResponseEntity<List<Category>> queryCategoryByBid(Long bid){
        return ResponseEntity.ok(categoryService.queryByBid(bid));
    }
}
