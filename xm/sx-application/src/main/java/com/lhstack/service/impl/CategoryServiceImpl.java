package com.lhstack.service.impl;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.lhstack.common.handler.BeanHandler;
import com.lhstack.common.handler.BeanListHandler;
import com.lhstack.pojo.Category;
import com.lhstack.pojo.Page;
import com.lhstack.service.ICategoryService;
import com.lhstack.service.IProductService;
import com.lhstack.utils.GsonUtils;
import com.lhstack.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CategoryServiceImpl implements ICategoryService {


    private IProductService productService = new ProductServiceImpl();

    private static String HKEY = "category";

    @Override
    public Page<Category> findByPageCategory(Long page, Long size) {
        if (page < 1) {
            page = 1L;
        }
        String key = String.format("findByPageCategories:page-%s:size-%s", page, size);
        String content = JedisUtils.hget(HKEY, key);
        if (StringUtils.isEmpty(content)) {
            try {
                List<Category> categories = Db.use().query("SELECT * FROM category WHERE 1 = 1 ORDER BY order_number DESC LIMIT ?,?", new BeanListHandler<>(Category.class), (page - 1) * size, size);
                Page<Category> pages = new Page<>();
                pages.setContent(categories)
                        .setCurrentPage(page)
                        .setMaxTotal(count())
                        .setSize(size)
                        .setMaxPage()
                        .setIndex();
                JedisUtils.hset(HKEY, key, pages);
                return pages;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return GsonUtils.createGson().fromJson(content, Page.class);
    }

    @Override
    public Integer updateCategoryById(Category category) {
        try {
            AtomicInteger update = new AtomicInteger();
            Db.use().tx(db -> {
                update.set(db.update(Entity.create("category")
                        .set("cname", category.getCname())
                        .set("state", category.getState())
                        .set("order_number", category.getOrderNumber())
                        .set("description", category.getDescription()), Entity.create().set("cid", category.getCid())));
            });
            JedisUtils.hdel(HKEY);
            return update.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Integer deleteCategoryByIds(List<Long> cids) {
        Integer count = 0;
        for (int i = 0; i < cids.size(); i++) {
            Integer delCount = this.deleteCategoryById(cids.get(i));
            if (delCount != -1) {
                count += delCount;
            } else {
                return -1;
            }
        }
        JedisUtils.hdel(HKEY);
        return count;
    }

    @Override
    public Integer deleteCategoryById(Long cid) {
        try {
            AtomicInteger del = new AtomicInteger();
            Db.use().tx(db -> {
                Integer integer = productService.deleteByCid(cid);
                if (integer != -1) {
                    del.set(db.del("category", "cid", cid));
                } else {
                    del.set(-1);
                }
            });
            JedisUtils.hdel(HKEY);
            return del.get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    @Override
    public Integer addCategory(Category category) {
        try {
            AtomicInteger insert = new AtomicInteger();
            Db.use().tx(db -> {
                insert.set(db.insert(Entity.create("category")
                        .set("cname", category.getCname())
                        .set("state", category.getState())
                        .set("order_number", category.getOrderNumber())
                        .set("description", category.getDescription())
                        .set("create_time", new Date())
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
    public Integer count() {
        Integer iCount = 0;
        try {
            iCount = Db.use().count(Entity.create("category"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return iCount;
    }

    @Override
    public Category findById(Long cid) {
        String key = String.format("findById:%s", cid);
        String content = JedisUtils.hget(HKEY, key);
        if (StringUtils.isEmpty(content)) {
            try {
                Category category = Db.use().query("SELECT * FROM category WHERE cid = ?", new BeanHandler<>(Category.class), cid);
                JedisUtils.hset(HKEY, key, category);
                return category;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return GsonUtils.createGson().fromJson(content, Category.class);
    }

    @Override
    public List<Category> findAll() {
        String key = String.format("findAll");
        String content = JedisUtils.hget(HKEY, key);
        if (StringUtils.isEmpty(content)) {
            try {
                List<Category> categories = Db.use().query("SELECT * FROM category", new BeanListHandler<>(Category.class));
                JedisUtils.hset(HKEY, key, categories);
                return categories;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return GsonUtils.createGson().fromJson(content, List.class);
    }
}
