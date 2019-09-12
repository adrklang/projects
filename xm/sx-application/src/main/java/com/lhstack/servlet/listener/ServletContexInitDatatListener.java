package com.lhstack.servlet.listener;

import com.lhstack.pojo.Category;
import com.lhstack.pojo.Page;
import com.lhstack.pojo.Product;
import com.lhstack.service.ICategoryService;
import com.lhstack.service.IProductService;
import com.lhstack.service.impl.CategoryServiceImpl;
import com.lhstack.service.impl.ProductServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ServletContexInitDatatListener implements ServletContextListener {

    private ICategoryService categoryService = new CategoryServiceImpl();

    private IProductService productService = new ProductServiceImpl();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Page<Category> categories = categoryService.findByPageCategory(1L, 10L);
        List<Product> products4Pages = productService.findByStatePages(4, 1, 5);
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("categories",categories.getContent());
        servletContext.setAttribute("products4",products4Pages);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
