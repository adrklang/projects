package com.leyou.item.controller;

import com.leyou.api.SpecificationControllerApi;
import com.leyou.item.interfaces.ISpecificationService;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SpecificationController implements SpecificationControllerApi {

    private final ISpecificationService specService;

    /**
     * 根据分类id查询规格组
     */
    @Override
    public ResponseEntity<List<SpecGroup>> queryGroupByCid( Long cid) {
        return ResponseEntity.ok(specService.queryGroupByCid(cid));
    }

    @Override
    public ResponseEntity<Void> saveSpecParam(SpecParam specParam){
        specService.saveSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteSpecParam(Long id){
        specService.deleteSpecParamById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateSpecParam(SpecParam specParam){
        specService.updateSpecParam(specParam);
        return ResponseEntity.ok().build();
    }



    /**
     * 查询参数的集合
     */
    @Override
    public ResponseEntity<List<SpecParam>> queryParamList(Long gid, Long cid, Boolean searching) {
        return ResponseEntity.ok(specService.queryParamList(gid, cid, searching));
    }

    /**
     * 根据分类查询规格组及组内参数
     * @param cid
     * @return
     */
    @Override
    public ResponseEntity<List<SpecGroup>> queryListByCid(Long cid) {
        return ResponseEntity.ok(specService.queryListByCid(cid));
    }

    @Override
    public ResponseEntity<Void> saveSpecGroup(SpecGroup specGroup) {
        specService.saveSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

