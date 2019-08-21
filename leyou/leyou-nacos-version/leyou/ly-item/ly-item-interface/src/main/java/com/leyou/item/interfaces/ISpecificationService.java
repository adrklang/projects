package com.leyou.item.interfaces;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

public interface ISpecificationService {
    List<SpecGroup> queryGroupByCid(Long cid);

    List<SpecParam> queryParamList(Long gid, Long cid, Boolean searching);

    List<SpecGroup> queryListByCid(Long cid);

    void saveSpecParam(SpecParam specParam);

    void updateSpecParam(SpecParam specParam);

    void deleteSpecParamById(Long id);

    void saveSpecGroup(SpecGroup specGroup);
}
