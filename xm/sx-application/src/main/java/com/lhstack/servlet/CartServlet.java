package com.lhstack.servlet;

import cn.hutool.core.collection.CollectionUtil;
import com.lhstack.common.annotation.RequestMapping;
import com.lhstack.common.annotation.RequestParam;
import com.lhstack.common.filter.LoginAuthorizationFilter;
import com.lhstack.pojo.Cart;
import com.lhstack.pojo.CartItem;
import com.lhstack.pojo.Product;
import com.lhstack.pojo.User;
import com.lhstack.pojo.res.Resp;
import com.lhstack.service.ICartService;
import com.lhstack.service.IProductService;
import com.lhstack.service.impl.CartServiceImpl;
import com.lhstack.service.impl.ProductServiceImpl;
import com.lhstack.utils.GsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/cart/*")
public class CartServlet extends AbstractServlet {

    private ICartService cartService = new CartServiceImpl();

    private IProductService productService = new ProductServiceImpl();

    @RequestMapping(value = "delete",method = RequestMapping.RequestConst.METHOD_DELETE)
    public void delete(HttpServletResponse response,HttpSession session,@RequestParam("pid") Long pid){
        boolean state = (boolean) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_STATE_KEY);
        if(!state) {
            out(response, Resp.builder().setStatus(403).setState(false).setMessage("你没有登录，请先登录"));
            return;
        }else{
            User user = (User) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_KEY);
            Boolean delete = cartService.deleteByPid(user,pid);
            if(delete){
                out(response,Resp.builder().setMessage("删除商品成功").setState(true).setStatus(200));
            }else{
                out(response,Resp.builder().setMessage("删除商品失败").setState(false).setStatus(403));
            }
        }
    }


    @RequestMapping(value = "clearAll",method = RequestMapping.RequestConst.METHOD_POST)
    public void clearAll(HttpServletResponse response,HttpSession session){
        boolean state = (boolean) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_STATE_KEY);
        if(!state) {
            out(response, Resp.builder().setStatus(403).setState(false).setMessage("你没有登录，请先登录"));
            return;
        }else{
            User user = (User) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_KEY);
            Boolean clearAll = cartService.clearAll(user);
            if(clearAll){
                out(response,Resp.builder().setMessage("清空购物车成功").setState(true).setStatus(200));
            }else{
                out(response,Resp.builder().setMessage("清空购物车失败").setState(false).setStatus(403));
            }
        }
    }


    @RequestMapping(value = "count",method = RequestMapping.RequestConst.METHOD_POST)
    public void count(HttpServletResponse response,HttpSession session) throws IOException {
        boolean state = (boolean) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_STATE_KEY);
        if(!state){
            out(response, Resp.builder().setStatus(403).setState(false).setMessage("你没有登录，请先登录"));
            return ;
        }else {
            User user = (User) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_KEY);
            CartItem cartItem = cartService.getCartItem(user);
            response.getOutputStream().write(GsonUtils.createGson().toJson(cartItem).getBytes());
        }
    }


    @RequestMapping(value = "toCart",method = RequestMapping.RequestConst.METHOD_GET)
    public void toCart(HttpServletRequest request, HttpSession session,HttpServletResponse response) throws ServletException, IOException {
        boolean state = (boolean) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_STATE_KEY);
        if(!state){
            out(response, Resp.builder().setStatus(403).setState(false).setMessage("你没有登录，请先登录"));
            return ;
        }else{
            request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request,response);
        }
    }

    @RequestMapping(value = "update",method = RequestMapping.RequestConst.METHOD_POST)
    public void updateCart(Map<String,String> cart, HttpServletResponse response, HttpSession session){
        boolean state = (boolean) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_STATE_KEY);
        if(!state) {
            out(response, Resp.builder().setStatus(403).setState(false).setMessage("你没有登录，请先登录"));
            return;
        }else{
            if(CollectionUtil.isEmpty(cart)){
                out(response, Resp.builder().setStatus(403).setState(false).setMessage("请添加你的商品"));
                return ;
            }
            User user = (User) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_KEY);
            Long count = Long.parseLong(cart.get("count"));
            Long pid = Long.parseLong(cart.get("pid"));
            Boolean update = cartService.update(user,count,pid);
            if(update){
                out(response, Resp.builder().setStatus(200).setState(true).setMessage("商品更新成功，即将跳转购物车页面"));
                return ;
            }else{
                out(response, Resp.builder().setStatus(403).setState(false).setMessage("商品更新失败，购物车已有该商品"));
            }
        }
    }

    @RequestMapping(value = "add",method = RequestMapping.RequestConst.METHOD_POST)
    public void addCart(Map<String,String> cart, HttpServletResponse response, HttpSession session){
        boolean state = (boolean) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_STATE_KEY);
        if(!state){
            out(response, Resp.builder().setStatus(403).setState(false).setMessage("你没有登录，请先登录"));
            return ;
        }else{
            if(CollectionUtil.isEmpty(cart)){
                out(response, Resp.builder().setStatus(403).setState(false).setMessage("请添加你的商品"));
                return ;
            }
            User user = (User) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_KEY);
            Long count = Long.parseLong(cart.get("count"));
            Integer pid = Integer.parseInt(cart.get("pid"));
            Product product = productService.findByPid(pid * 1L);
            Cart c = new Cart();
            c.setCount(count).setProduct(product).setTotal(count * product.getPrice());
            Boolean add = cartService.addCart(user, c);
            if(add){
                out(response, Resp.builder().setStatus(200).setState(true).setMessage("商品添加成功，即将跳转购物车页面"));
                return ;
            }else{
                out(response, Resp.builder().setStatus(403).setState(false).setMessage("商品添加失败，购物车已有该商品"));
            }
        }
    }
}
