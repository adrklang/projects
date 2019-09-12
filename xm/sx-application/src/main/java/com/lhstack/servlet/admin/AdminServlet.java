package com.lhstack.servlet.admin;

import com.lhstack.common.annotation.RequestMapping;
import com.lhstack.common.annotation.RequestParam;
import com.lhstack.common.filter.LoginAuthorizationFilter;
import com.lhstack.pojo.Page;
import com.lhstack.pojo.User;
import com.lhstack.pojo.res.Resp;
import com.lhstack.service.IUserService;
import com.lhstack.service.impl.UserServiceImpl;
import com.lhstack.servlet.AbstractServlet;
import com.lhstack.utils.GsonUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/super/*")
public class AdminServlet extends AbstractServlet {

    private IUserService userService = new UserServiceImpl();


    @RequestMapping(value = "updateState",method = RequestMapping.RequestConst.METHOD_POST)
    public void updateState(HttpServletResponse response, Map<String,String> map){
        Integer integer = userService.updateState(Long.parseLong(map.get("uid")), Long.parseLong(map.get("state")));
        if(integer > 0){
            out(response,Resp.builder().setMessage("更新用户状态成功").setState(true).setStatus(204));
        }else{
            out(response,Resp.builder().setStatus(403).setState(false).setMessage("更新用户状态失败"));
        }
    }


    @RequestMapping(value = "user_list", method = RequestMapping.RequestConst.METHOD_GET)
    public void user_list(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Long page = Long.parseLong(request.getParameter("page"));
        Long size = Long.parseLong(request.getParameter("size"));
        if (page == null || size == null) {
            out(response, Resp.builder().setMessage("page 或者 size为空").setState(false).setStatus(403));
            return;
        }
        User user = (User) session.getAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_KEY);
        Page<User> pages = userService.findByUserPage(user, page, size);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(GsonUtils.createGson().toJson(pages).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @RequestMapping(value = "deletes", method = RequestMapping.RequestConst.METHOD_DELETE)
    public void deletes(HttpServletResponse response, @RequestParam("uids")String uids){
        String[] ids = uids.split(",");
        List<Long> longIds = Arrays.asList(ids).stream().map(Long::parseLong).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(longIds)){
            Integer delete = userService.deleteByIds(longIds);
            if(delete > 0){
                out(response,Resp.builder().setStatus(204).setState(true).setMessage("删除用户成功"));
            }else{
                out(response,Resp.builder().setStatus(500).setState(false).setMessage("删除用户失败"));
            }
        }else{
            out(response,Resp.builder().setStatus(500).setState(false).setMessage("删除用户失败"));
        }
    }
    @RequestMapping(value = "delete", method = RequestMapping.RequestConst.METHOD_DELETE)
    public void delete(HttpServletResponse response, @RequestParam("uid")Long uid){
        if(uid != null){
            Integer delete = userService.delete(uid);
            if(delete > 0){
                out(response,Resp.builder().setStatus(204).setState(true).setMessage("删除用户成功"));
            }else{
                out(response,Resp.builder().setStatus(500).setState(false).setMessage("删除用户失败"));
            }
        }else{
            out(response,Resp.builder().setStatus(500).setState(false).setMessage("删除用户失败"));
        }
    }

    @RequestMapping(value = "update", method = RequestMapping.RequestConst.METHOD_POST)
    public void update(HttpServletResponse response, User user){
        Integer update = userService.update(user);
        if(update > 0){
            out(response,Resp.builder().setStatus(204).setState(true).setMessage("更新用户成功"));
        }else{
            out(response,Resp.builder().setStatus(500).setState(false).setMessage("更新用户失败"));
        }
    }

}
