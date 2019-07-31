package com.lhstack.content.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.lhstack.common.jedis.JedisClient;
import com.lhstack.common.pojo.TaotaoResult;
import com.lhstack.common.util.JsonUtils;
import com.lhstack.content.service.ContentService;
import com.lhstack.mapper.TbContentMapper;
import com.lhstack.pojo.TbContent;
import com.lhstack.pojo.TbContentExample;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Override
    public TaotaoResult addContent(TbContent content) {
        //将内容数据插入到内容表
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入到数据库
        contentMapper.insert(content);
        //删除缓存
        jedisClient.hdel(CONTENT_LIST,content.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    /**
     * 根据内容分类id查询内容列表
     * @param cid
     * @return
     */
    @Override
    public List<TbContent> getContentListByCid(long cid) {
        //缓存查询
        try {
            String json = jedisClient.hget(CONTENT_LIST, cid + "");
            if(StringUtils.isNoneBlank(json)){
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
        //添加到缓存
        try {
            jedisClient.hset(CONTENT_LIST,String.valueOf(cid),JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
