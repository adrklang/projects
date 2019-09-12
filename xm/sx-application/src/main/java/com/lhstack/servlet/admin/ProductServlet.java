package com.lhstack.servlet.admin;

import com.lhstack.common.annotation.RequestMapping;
import com.lhstack.common.annotation.RequestParam;
import com.lhstack.pojo.Page;
import com.lhstack.pojo.Product;
import com.lhstack.pojo.res.Resp;
import com.lhstack.service.IProductService;
import com.lhstack.service.impl.ProductServiceImpl;
import com.lhstack.servlet.AbstractServlet;
import com.lhstack.utils.GsonUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/product/*")
public class ProductServlet extends AbstractServlet {


    private IProductService productService = new ProductServiceImpl();


    @RequestMapping(value = "findByPid",method = RequestMapping.RequestConst.METHOD_GET)
    public void findByPid(HttpServletResponse response,HttpServletRequest request,@RequestParam("pid")Long pid) throws ServletException, IOException {
        Product product = productService.findByPid(pid);
        request.setAttribute("product",product);
        request.getRequestDispatcher("/WEB-INF/product_detail.jsp").forward(request,response);
    }


    @RequestMapping(value = "list",method = RequestMapping.RequestConst.METHOD_POST)
    public void findByPages(HttpServletRequest request, HttpServletResponse response, Map<String,String> map) throws ParseException {
        Long page = Long.parseLong(map.get("page"));
        Long size = Long.parseLong(map.get("size"));
        if (page == null || size == null) {
            out(response, Resp.builder().setMessage("page 或者 size为空").setState(false).setStatus(403));
            return;
        }
        Page<Product> pages = productService.findByPageAndCondition(page, size,map);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(GsonUtils.createGson().toJson(pages).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @RequestMapping(value = "add",method = RequestMapping.RequestConst.METHOD_POST)
    public void addProduct(HttpServletResponse response,Product product){
        Integer add = productService.addProduct(product);
        if(add > 0){
            out(response,Resp.builder().setMessage("商品信息添加成功").setState(true).setStatus(204));
        }else{
            out(response,Resp.builder().setMessage("商品信息添加失败").setState(false).setStatus(403));
        }
    }


    @RequestMapping(value = "update",method = RequestMapping.RequestConst.METHOD_POST)
    public void updateCategory(HttpServletResponse response,Product product){
        Integer update = productService.updateByProductById(product);
        if(update > 0){
            out(response,Resp.builder().setMessage("商品信息更新成功").setState(true).setStatus(204));
        }else{
            out(response,Resp.builder().setMessage("商品信息更新失败").setState(false).setStatus(403));
        }
    }


    @RequestMapping(value = "deletes",method = RequestMapping.RequestConst.METHOD_DELETE)
    public void deleteByIds(HttpServletResponse response, @RequestParam("pids") String pids){
        String[] split = pids.split(",");
        List<Long> collect = Arrays.asList(split).stream().map(Long::parseLong).collect(Collectors.toList());
        Integer delete = productService.deleteProductByIds(collect);
        if(delete > 0){
            out(response,Resp.builder().setMessage("删除商品成功").setState(true).setStatus(204));
        }else{
            out(response,Resp.builder().setMessage("删除商品失败").setState(false).setStatus(403));
        }
    }


    @RequestMapping(value = "delete",method = RequestMapping.RequestConst.METHOD_DELETE)
    public void deleteProductById(HttpServletResponse response, @RequestParam("id")Long id){
        Integer delete = productService.deleteByProductId(id);
        if(delete > 0){
            out(response,Resp.builder().setMessage("删除商品成功").setState(true).setStatus(204));
        }else{
            out(response,Resp.builder().setMessage("删除商品失败").setState(false).setStatus(403));
        }
    }
}
