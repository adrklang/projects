package com.leyou.item.service.impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leyou.item.interfaces.ISpecificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@AllArgsConstructor
public class SpecificationServiceImpl implements ISpecificationService {

    private final SpecGroupMapper groupMapper;

    private final SpecParamMapper paramMapper;

    @Override
    @Cacheable(cacheNames = "specGroup",key = "@cacheKeyUtilsConfig.getKey('queryGroupByCid',#p0)")
    public List<SpecGroup> queryGroupByCid(Long cid) {
        // 查询条件
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        List<SpecGroup> list = groupMapper.select(group);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return list;
    }

    @Override
    @Cacheable(cacheNames = "specParam",key = "@cacheKeyUtilsConfig.getKey('queryParamList',#p0,#p1,#p2)")
    public List<SpecParam> queryParamList(Long gid, Long cid, Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> list = paramMapper.select(param);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return list;
    }

    @Override
    @Cacheable(cacheNames = "specGroup",key = "@cacheKeyUtilsConfig.getKey('queryListByCid',#p0)")
    public List<SpecGroup> queryListByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> specGroups = queryGroupByCid(cid);
        // 查询分类下的参数
        List<SpecParam> specParams = queryParamList(null, cid, null);
        // 先把规格参数变成map, map的key是规格组id, map的值是组下的所有参数
        Map<Long, List<SpecParam>> map = new HashMap<>();
        for (SpecParam param : specParams) {
            Long groupId = param.getGroupId();
            if (!map.containsKey(groupId)) {
                // 这个组id在map中不存在, 新增一个list
                List<SpecParam> list = new ArrayList<>();
                map.put(groupId, list);
            }
            List<SpecParam> list = map.get(groupId);
            list.add(param);
        }

        // 填充param到group
        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));
        }
        return specGroups;
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = LyException.class)
    @CacheEvict(cacheNames = "specParam",allEntries = true)
    public void saveSpecParam(SpecParam specParam) {
        int resultParamCount = paramMapper.insert(specParam);
        if(resultParamCount < 1){
            throw new LyException(ExceptionEnum.SPEC_PARAM_SAVE_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = LyException.class)
    @CacheEvict(cacheNames = "specParam",allEntries = true)
    public void updateSpecParam(SpecParam specParam) {
        int resultParamCount = paramMapper.updateByPrimaryKey(specParam);
        if(resultParamCount < 1){
            throw new LyException(ExceptionEnum.SPEC_PARAM_UPDATE_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = LyException.class)
    @CacheEvict(cacheNames = "specParam",allEntries = true)
    public void deleteSpecParamById(Long id) {
        int resultParamCount = paramMapper.deleteByPrimaryKey(id);
        if(resultParamCount < 1){
            throw new LyException(ExceptionEnum.SPEC_PARAM_UPDATE_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = LyException.class)
    @CacheEvict(cacheNames = "specGroup",allEntries = true)
    public void saveSpecGroup(SpecGroup specGroup) {
        int resultSpecGroupCount = groupMapper.insert(specGroup);
        if(resultSpecGroupCount < 1){
            throw new LyException(ExceptionEnum.SPEC_PARAM_UPDATE_ERROR);
        }
    }
}
