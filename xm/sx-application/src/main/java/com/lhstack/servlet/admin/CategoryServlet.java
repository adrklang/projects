package com.lhstack.servlet.admin;

import com.lhstack.common.annotation.RequestMapping;
import com.lhstack.common.annotation.RequestParam;
import com.lhstack.pojo.Category;
import com.lhstack.pojo.Page;
import com.lhstack.pojo.res.Resp;
import com.lhstack.service.ICategoryService;
import com.lhstack.service.impl.CategoryServiceImpl;
import com.lhstack.servlet.AbstractServlet;
import com.lhstack.utils.GsonUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/category/*")
public class CategoryServlet extends AbstractServlet {

    private ICategoryService categoryService = new CategoryServiceImpl();


    @RequestMapping(value = "all",method = RequestMapping.RequestConst.METHOD_GET)
    public void all(HttpServletResponse response) throws IOException {
        List<Category> list = categoryService.findAll();
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(GsonUtils.createGson().toJson(list).getBytes());
        outputStream.close();
    }


    @RequestMapping(value = "findById",method = RequestMapping.RequestConst.METHOD_GET)
    public void findById(HttpServletResponse response,@RequestParam("cid") Long cid) throws IOException {
        Category category = categoryService.findById(cid);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(GsonUtils.createGson().toJson(category).getBytes());
        outputStream.close();
    }


    @RequestMapping(value = "deletes",method = RequestMapping.RequestConst.METHOD_DELETE)
    public void deleteByIds(HttpServletResponse response, @RequestParam("cids") String cids){
        String[] split = cids.split(",");
        List<Long> collect = Arrays.asList(split).stream().map(Long::parseLong).collect(Collectors.toList());
        Integer delete = categoryService.deleteCategoryByIds(collect);
        if(delete > 0){
            out(response,Resp.builder().setMessage("删除类别成功").setState(true).setStatus(204));
        }else{
            out(response,Resp.builder().setMessage("删除类别失败").setState(false).setStatus(403));
        }
    }

    @RequestMapping(value = "delete",method = RequestMapping.RequestConst.METHOD_DELETE)
    public void deleteById(HttpServletResponse response, @RequestParam("cid") Long cid){
        Integer delete = categoryService.deleteCategoryById(cid);
        if(delete > 0){
            out(response,Resp.builder().setMessage("删除类别成功").setState(true).setStatus(204));
        }else{
            out(response,Resp.builder().setMessage("删除类别失败").setState(false).setStatus(403));
        }
    }


    @RequestMapping(value = "add",method = RequestMapping.RequestConst.METHOD_POST)
    public void addCategory(HttpServletResponse response,Category category){
        Integer add = categoryService.addCategory(category);
        if(add > 0){
            out(response,Resp.builder().setMessage("分类信息更新成功").setState(true).setStatus(204));
        }else{
            out(response,Resp.builder().setMessage("分类信息更新失败").setState(false).setStatus(403));
        }
    }

    @RequestMapping(value = "update",method = RequestMapping.RequestConst.METHOD_POST)
    public void updateCategory(HttpServletResponse response,Category category){
        Integer integer = categoryService.updateCategoryById(category);
        if(integer > 0){
            out(response,Resp.builder().setMessage("分类信息更新成功").setState(true).setStatus(204));
        }else{
            out(response,Resp.builder().setMessage("分类信息更新失败").setState(false).setStatus(403));
        }
    }


    @RequestMapping(value = "list",method = RequestMapping.RequestConst.METHOD_POST)
    public void findByPage(HttpServletRequest request, HttpServletResponse response){
        Long page = Long.parseLong(request.getParameter("page"));
        Long size = Long.parseLong(request.getParameter("size"));
        if (page == null || size == null) {
            out(response, Resp.builder().setMessage("page 或者 size为空").setState(false).setStatus(403));
            return;
        }
        Page<Category> pages = categoryService.findByPageCategory(page, size);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(GsonUtils.createGson().toJson(pages).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
