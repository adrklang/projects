package com.leyou.item.controller;

import com.leyou.api.BrandControllerApi;
import com.leyou.common.vo.PageResult;
import com.leyou.item.interfaces.IBrandService;
import com.leyou.item.pojo.Brand;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BrandController implements BrandControllerApi {

    private final IBrandService brandService;

    /**
     *  分页查询品牌
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @Override
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        PageResult<Brand> result = brandService.queryBrandByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     * @return
     */
    @Override
    public ResponseEntity<Void> saveBrand(Brand brand,List<Long> cids) {
        brandService.saveBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteBrand(Long bid){
        brandService.deleteById(bid);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateBrand(Brand brand, List<Long> cids) {
        brandService.updateBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * 根据cid查询品牌
     * @param cid
     * @return
     */
    @Override
    public ResponseEntity<List<Brand>> queryBrandsByCid(Long cid) {
        return ResponseEntity.ok(brandService.queryBrandsByCid(cid));
    }

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Brand> queryBrandById(Long id) {
        return ResponseEntity.ok(brandService.queryById(id));
    }

    /**
     * 根据id列表查询品牌列表
     * @param ids
     * @return
     */
    @Override
    public ResponseEntity<List<Brand>> queryBrandByIds(List<Long> ids) {
        return ResponseEntity.ok(brandService.queryBrandByIds(ids));
    }
}
