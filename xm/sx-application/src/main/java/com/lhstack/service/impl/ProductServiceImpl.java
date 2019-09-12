package com.lhstack.service.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.lhstack.common.handler.BeanHandler;
import com.lhstack.common.handler.BeanListHandler;
import com.lhstack.pojo.Page;
import com.lhstack.pojo.Product;
import com.lhstack.service.IProductService;
import com.lhstack.utils.GsonUtils;
import com.lhstack.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductServiceImpl implements IProductService {

    private static String HKEY = "product";
    @Override
    public Integer deleteByCid(Long cid){
        try {
            AtomicInteger del = new AtomicInteger();
            Db.use().tx(db -> {
                del.set(db.del("product", "cid", cid));
            });
            JedisUtils.hdel(HKEY);
            return del.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    @Override
    public Integer deleteProductByIds(List<Long> pids){
        Integer count = 0;
        for (int i = 0; i < pids.size(); i++) {
            Integer delCount = this.deleteByProductId(pids.get(i));
            if(delCount != -1){
                count += delCount;
            }else{
                return -1;
            }
        }
        JedisUtils.hdel(HKEY);
        return count;
    }

    @Override
    public List<Product> findByCategoryStatePages(String cname, Integer page, Integer size){
        if(page < 1){
            page = 1;
        }
        String key = String.format("findByCategoryStatePages:cname:%s:page:%s:size:%s",cname,page,size);
        String content = JedisUtils.hget(HKEY, key);
        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,List.class);
        }
        try {
            List<Product> products = Db.use().query("SELECT product.* FROM product LEFT JOIN category ON product.cid = category.`cid` WHERE category.state = 1 AND category.cname = ? ORDER BY product.product_date DESC LIMIT ?,?", new BeanListHandler<>(Product.class), cname,(page - 1) * size,size);
            JedisUtils.hset(HKEY,key,products);
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Product> findByStatePages(Integer state, Integer page, Integer size){
        if(page < 1){
            page = 1;
        }
        String key = String.format("findByStatePages:state:%s:page:%s:size:%s",state,page,size);
        String content = JedisUtils.hget(HKEY, key);
        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,List.class);
        }
        try {
            List<Product> products = Db.use().query("SELECT * FROM product WHERE state = ? ORDER BY product_date DESC LIMIT ?,?", new BeanListHandler<>(Product.class), state,(page - 1) * size,size);
            JedisUtils.hset(HKEY,key,products);
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer addProduct(Product product){
        try {
            AtomicInteger insert = new AtomicInteger();
            Db.use().tx(db ->{
                insert.set(db.insert(Entity.create("product")
                        .set("pname", product.getPname())
                        .set("color", product.getColor())
                        .set("price", product.getPrice())
                        .set("description", product.getDescription())
                        .set("pic",product.getPic())
                        .set("state",product.getState())
                        .set("version",product.getVersion())
                        .set("product_date",new Date())
                        .set("cid",product.getCid())
                ));
            });
            JedisUtils.hdel(HKEY);
            return insert.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer deleteByProductId(Long pId){
        try {
            AtomicInteger del = new AtomicInteger();
            Db.use().tx(db -> {
                del.set(db.del("product", "pid", pId));
            });
            JedisUtils.hdel(HKEY);
            return del.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Page<Product> findByPage(Long page, Long size){
        if(page < 1){
            page = 1L;
        }
        String key = String.format("findByPage:page:%s:size:%s",page,size);
        String content = JedisUtils.hget(HKEY, key);
        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,Page.class);
        }
        try {
            List<Product> products = Db.use().query("SELECT * FROM product LEFT JOIN category ON category.`cid` = product.`cid` WHERE 1 = 1 ORDER BY product.`product_date` LIMIT ?,?", new BeanListHandler<>(Product.class), (page - 1) * size, size);
            Page<Product> pages = new Page<>();
            pages.setContent(products)
                    .setCurrentPage(page)
                    .setMaxTotal(count())
                    .setSize(size)
                    .setMaxPage()
                    .setIndex();
            JedisUtils.hset(HKEY,key,pages);
            return pages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer count(){
        int count = 0;
        try {
            count = Db.use().count(Entity.create("product"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Integer updateByProductById(Product product) {
        try {
            AtomicInteger update = new AtomicInteger();
            Db.use().tx(db -> {
                update.set(db.update(Entity.create("product")
                        .set("pname", product.getPname())
                        .set("state", product.getState())
                        .set("cid", product.getCid())
                        .set("description", product.getDescription())
                        .set("color",product.getColor())
                        .set("version",product.getVersion())
                        .set("price",product.getPrice())
                        .set("pic",product.getPic())
                        , Entity.create().set("pid", product.getPid())));
            });
            JedisUtils.hdel(HKEY);
            return update.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Page<Product> findByPageAndCondition(Long page, Long size, Map<String, String> map) throws ParseException {
        if(page < 1){
            page = 1L;
        }
        String key = String.format("findByPageAndCondition:page:%s:size:%s:map:%s",page,size,map.toString());
        String content = JedisUtils.hget(HKEY, key);

        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,Page.class);
        }
        List<Object> params = new LinkedList<>();
        StringBuffer stb = new StringBuffer();
        stb.append("SELECT * FROM product LEFT JOIN category ON category.`cid` = product.`cid` WHERE 1 = 1 ");
        String pname = map.get("pname");
        if(StringUtils.isNotEmpty(pname)){
            stb.append("AND product.pname like ? ");
            params.add("%" + map.get("pname") + "%");
        }
        if(StringUtils.isNotEmpty(map.get("state"))){
            stb.append("AND product.state = ? ");
            params.add(map.get("state"));
        }
        if(StringUtils.isNotEmpty(map.get("startTime")) && StringUtils.isNotEmpty(map.get("endTime"))){
            stb.append("AND product.product_date BETWEEN ? AND ?");
            params.add(map.get("startTime"));
            params.add(map.get("endTime"));
        }
        stb.append(" ORDER BY product.`product_date` DESC LIMIT ?,?");
        params.add((page - 1) * size);
        params.add(size);
        try {
            List<Product> list = Db.use().query(stb.toString(), new BeanListHandler<>(Product.class),params.toArray());
            Page<Product> pages = new Page<>();
            pages.setContent(list)
                    .setCurrentPage(page)
                    .setMaxTotal(count(map))
                    .setSize(size)
                    .setMaxPage()
                    .setIndex();
            JedisUtils.hset(HKEY,key,pages);
            return pages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product findByPid(Long pid) {
        String key = String.format("findByPid:pid=%s",pid);
        String content = JedisUtils.hget(HKEY, key);
        if(StringUtils.isNotEmpty(content)){
            return GsonUtils.createGson().fromJson(content,Product.class);
        }
        try {
            Product product = Db.use().query("SELECT * FROM product WHERE pid = ?", new BeanHandler<>(Product.class), pid);
            JedisUtils.hset(HKEY,key,product);
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer count(Map<String, String> map) {
        String key = map.toString();
        String count = JedisUtils.hget(HKEY, key);
        if(StringUtils.isEmpty(count)){
            Entity product = Entity.create("product");
            String pname = map.get("pname");
            if(StringUtils.isNotEmpty(pname)){
                product.set("pname","LIKE %"+map.get("pname")+"%");
            }
            if(StringUtils.isNotEmpty(map.get("state"))){
                product.set("state",map.get("state"));
            }
            if(StringUtils.isNotEmpty(map.get("startTime")) && StringUtils.isNotEmpty(map.get("endTime"))){
                product.set("product_date","BETWEEN '"+map.get("startTime")+"' AND '"+map.get("endTime")+"'");
            }
            try {
                int conditionCount = Db.use().count(product);
                JedisUtils.hset(HKEY,key,conditionCount);
                return conditionCount;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Integer.parseInt(count);
    }
}
