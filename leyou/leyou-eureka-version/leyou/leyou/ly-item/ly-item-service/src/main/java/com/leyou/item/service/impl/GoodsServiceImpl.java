package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.config.rabbitmq.RabbitMqProperties;
import com.leyou.common.dto.CartDTO;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.interfaces.IBrandService;
import com.leyou.item.interfaces.ICategoryService;
import com.leyou.item.interfaces.IGoodsService;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: cuzz
 * @Date: 2018/11/6 19:46
 * @Description: 商品货物
 */
@Slf4j
@Service
@AllArgsConstructor
public class GoodsServiceImpl implements IGoodsService {

    private final SpuMapper spuMapper;

    private final SpuDetailMapper spuDetailMapper;

    private final ICategoryService categoryService;

    private final IBrandService brandService;

    private final SkuMapper skuMapper;

    private final StockMapper stockMapper;

    private final AmqpTemplate amqpTemplate;

    @Override
    @Cacheable(cacheNames = "spu",key = "@cacheKeyUtilsConfig.getKey('querySpuPage',#p0,#p1,#p2,#p3)")
    public PageResult<Spu> querySpuPage(Integer page, Integer rows, Boolean saleable, String key) {
        // 1、查询SPU
        // 分页,最多允许查100条
        PageHelper.startPage(page, Math.min(rows, 200));

        // 创建查询条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        // 是否过滤上下架
        if (saleable != null) {
            criteria.orEqualTo("saleable", saleable);
        }

        // 是否模糊查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        // 默认排序
        example.setOrderByClause("last_update_time DESC");

        // 查询
        List<Spu> spus = spuMapper.selectByExample(example);

        // 判断
        if (CollectionUtils.isEmpty(spus)) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }

        // 解析分类和
        loadCategoryAndBrandName(spus);

        // 解析分页的结果
        PageInfo<Spu> info = new PageInfo<>(spus);
        return new PageResult<>(info.getTotal(), spus);
    }

    private void loadCategoryAndBrandName(List<Spu> spus) {
        for (Spu spu : spus) {
            // 处理分类名称
            List<String> names = categoryService.queryByIds(
                    Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream()
                    .map(new Function<Category, String>() {
                        @Override
                        public String apply(Category category) {
                            return category.getName();
                        }
                    })
                    .collect(Collectors.toList());
            spu.setCname(StringUtils.join(names, "/"));
            // 处理品牌名称
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());
        }
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = LyException.class)
    @CacheEvict(cacheNames = {"spu","spuDetail","sku","stock"},allEntries = true)
    @Override
    public void deleteGoods(Long spuId){
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        skuMapper.select(sku).forEach(resultSku ->{
            int stockCount = stockMapper.deleteByPrimaryKey(resultSku.getId());
            if(stockCount < 1){
                throw new LyException(ExceptionEnum.GOODS_STOCK_NOT_FOUND);
            }
        });
        int skuCount = skuMapper.delete(sku);

        if(skuCount < 1){
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }

        int spuDetailCount = spuDetailMapper.deleteByPrimaryKey(spuId);
        if(spuDetailCount < 1){
            throw new LyException(ExceptionEnum.SPU_DETAIL_NOT_FOUND);
        }
        int spuCount = spuMapper.deleteByPrimaryKey(spuId);
        if(spuCount < 1){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        amqpTemplate.convertAndSend(RabbitMqProperties.DELETE_GOODS_KEY,spuId);
    }

    @CacheEvict(cacheNames = {"spu","spuDetail","sku","stock"},allEntries = true)
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = LyException.class)
    @Override
    public void saveGoods(Spu spu) {
        // 新增spu
        saveSpu(spu);
        // 新增detail
        saveSpuDetail(spu);
        // 新增sku和库存
        saveSkuAndStock(spu);
        // 发送mq消息
        amqpTemplate.convertAndSend(RabbitMqProperties.ADD_GOODS_ROUTER_KEY, spu.getId());
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = LyException.class)
    @Override
    @CacheEvict(cacheNames = {"spu","spuDetail","sku","stock"},allEntries = true)
    public void updateGoods(Spu spu) {
        if (spu.getId() == null) {
            throw new LyException(ExceptionEnum.GOODS_ID_CANNOT_BE_NULL);
        }
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        // 查询sku
        List<Sku> skuList = skuMapper.select(sku);
        if (!CollectionUtils.isEmpty(skuList)) {
            // 删除sku
            skuMapper.delete(sku);
            // 删除stock
            List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());
            stockMapper.deleteByIdList(ids);
        }
        // 修改spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);

        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1) {
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        // 修改detail
        count = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if (count != 1) {
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        // 新增sku和stock
        saveSkuAndStock(spu);
        // 发送mq消息
        amqpTemplate.convertAndSend(RabbitMqProperties.UPDATE_ROUTER_KEY, spu.getId());
    }

    private void saveSkuAndStock(Spu spu) {
        List<Stock> stockList = new ArrayList<>();
        List<Sku> skus = spu.getSkus();
        for (Sku sku : skus) {
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            int count = skuMapper.insert(sku);
            if (count != 1) {
                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
            }
            // 保存库存信息
            Stock stock = new Stock();
            // 只有sku保存成功了, 才能获取sku的id, 批量新增不会产生id
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        // 批量新增库存
        // 这里有个坑, 有两个InsertListMapper,insertList方法限制不一样, 可以点进去看看
        int count = stockMapper.insertList(stockList);
        if (count != stockList.size()) {
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
    }

    private void saveSpuDetail(Spu spu) {
        SpuDetail detail = spu.getSpuDetail();
        detail.setSpuId(spu.getId());
        int count = spuDetailMapper.insert(detail);
        if (count != 1) {
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
    }

    private void saveSpu(Spu spu) {
        spu.setId(null);
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        int count = spuMapper.insert(spu);
        if (count != 1) {
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
    }

    @Override
    @Cacheable(cacheNames = "spuDetail",key = "@cacheKeyUtilsConfig.getKey('queryDetailById',#p0)")
    public SpuDetail queryDetailById(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if (spuDetail == null) {
            throw new LyException(ExceptionEnum.SPU_DETAIL_NOT_FOUND);
        }
        return spuDetail;
    }

    @Override
    @Cacheable(cacheNames = "sku",key = "@cacheKeyUtilsConfig.getKey('querySkusBySpuId',#p0)")
    public List<Sku> querySkusBySpuId(Long spuId) {
        // 查询sku
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if (CollectionUtils.isEmpty(skuList)) {
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        // 查询库存
        for (Sku s : skuList) {
            Stock stock = stockMapper.selectByPrimaryKey(s.getId());
            if (stock == null) {
                throw new LyException(ExceptionEnum.GOODS_STOCK_NOT_FOUND);
            }
            s.setStock(stock.getStock());
        }
        return skuList;
    }

    @Override
    @Cacheable(cacheNames = "spu",key = "@cacheKeyUtilsConfig.getKey('querySpuById',#p0)")
    public Spu querySpuById(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        // 查询sku
        spu.setSkus(querySkusBySpuId(id));
        // 查询detail
        spu.setSpuDetail(queryDetailById(id));
        return spu;
    }

    /**
     * 购物车中商品列表
     */
    @Override
    @Cacheable(cacheNames = "sku",key = "@cacheKeyUtilsConfig.getKey('querySkuByIds',#p0)")
    public List<Sku> querySkuByIds(List<Long> ids) {
        // 查询 sku
        List<Sku> skus = skuMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(skus)) {
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        // 填充库存
        loadStockInSku(ids, skus);
        return skus;
    }

    /**
     * 根据 sku 列表查询库存
     */
    private void loadStockInSku(List<Long> ids, List<Sku> skus) {
        // 查询库存
        List<Stock> stockList = stockMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(stockList)) {
            throw new LyException(ExceptionEnum.GOODS_STOCK_NOT_FOUND);
        }
        // 将库存转为 map，key 是 skuId，值是库存
        Map<Long, Integer> stockMap = stockList.stream()
                .collect(Collectors.toMap(stock -> stock.getSkuId(), stock -> stock.getStock()));
        // 保存库存到 sku
        for (Sku sku : skus) {
            sku.setStock(stockMap.get(sku.getId()));
        }
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = LyException.class)
    @Override
    public void decreaseStock(List<CartDTO> carts) {
        for (CartDTO cart : carts) {
            int count = stockMapper.decreaseStock(cart.getSkuId(), cart.getNum());
            if (count != 1) {
                throw new LyException(ExceptionEnum.STOCK_NOT_ENOUGH);
            }
        }
    }

    @Override
    @CacheEvict(cacheNames = {"spu","spuDetail","sku","stock"},allEntries = true)
    public void updateGoodsState(Long spuId, boolean state) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setSaleable(state);
        int updateCount = spuMapper.updateByPrimaryKeySelective(spu);
        if(updateCount < 1){
            throw new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
    }
}
