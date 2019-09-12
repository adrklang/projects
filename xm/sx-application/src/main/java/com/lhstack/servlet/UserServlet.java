package com.lhstack.servlet;

import com.lhstack.common.annotation.RequestMapping;
import com.lhstack.common.annotation.RequestParam;
import com.lhstack.common.filter.LoginAuthorizationFilter;
import com.lhstack.pojo.User;
import com.lhstack.pojo.res.Resp;
import com.lhstack.service.IUserService;
import com.lhstack.service.impl.UserServiceImpl;
import com.lhstack.utils.JedisUtils;
import com.lhstack.utils.Md5Utils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

@WebServlet("/user/*")
public class UserServlet extends AbstractServlet {

    private IUserService userService = new UserServiceImpl();


    @RequestMapping(value = "login",method = RequestMapping.RequestConst.METHOD_POST)
    public void login(User user,HttpServletResponse response,HttpSession session) throws NoSuchAlgorithmException {
        User rUser = userService.findByUsername(user.getUsername());
        String password = user.getPassword();
        String encode = Md5Utils.encode(password, rUser.getSalt());
        if(rUser.getPassword().equals(encode)){
            rUser.setPassword(null).setSalt(null);
            session.setAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_KEY,rUser);
            session.setAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_STATE_KEY,true);
            out(response,Resp.builder().setMessage("登录成功").setState(true).setStatus(200));
        }else{
            out(response,Resp.builder().setStatus(500).setState(false).setMessage("登录失败，用户名密码输入有误"));
        }
    }

    @RequestMapping(value = "register", method = RequestMapping.RequestConst.METHOD_POST)
    public void register( HttpServletResponse response, User user) throws NoSuchAlgorithmException {
        if (ObjectUtils.isNotEmpty(user)) {
            user.setManager(0)
                    .setCreateTime(new Date())
                    .setSalt(UUID.randomUUID().toString())
                    .setPassword(Md5Utils.encode(user.getPassword(), user.getSalt()));
            Integer insert = userService.insert(user);
            if (insert < 1) {
                out(response, Resp.builder().setMessage("用户注册失败，请稍后重新注册")
                        .setState(false)
                        .setStatus(500));
            } else {
                out(response, Resp.builder().setStatus(200).setState(true).setMessage("用户注册成功,3秒后跳转登录页面"));
            }
        } else {
            out(response, Resp.builder().setMessage("提交的用户信息有误,请重新注册")
                    .setState(false)
                    .setStatus(500));
        }
    }

    @RequestMapping(value = "findByName")
    public void findByName(@RequestParam("name") String name, HttpServletResponse response) throws SQLException {
        if (StringUtils.isBlank(name)) {
            out(response, Resp.builder().setMessage("昵称不能为空").setState(false).setStatus(500));
        }
        User user = userService.findByName(name);
        if (ObjectUtils.isEmpty(user)) {
            out(response, Resp.builder().setMessage("昵称可以使用").setState(true).setStatus(200));
        } else {
            out(response, Resp.builder().setMessage("昵称已存在").setState(false).setStatus(401));
        }
    }

    @RequestMapping(value = "findByPhone")
    public void findByPhone(@RequestParam("phoneNumber") String phoneNumber, HttpServletResponse response) throws SQLException {
        if (StringUtils.isBlank(phoneNumber)) {
            out(response, Resp.builder().setMessage("电话号码不能为空").setState(false).setStatus(500));
        }
        User user = userService.findByPhone(phoneNumber);
        if (ObjectUtils.isEmpty(user)) {
            out(response, Resp.builder().setMessage("电话号码可以使用").setState(true).setStatus(200));
        } else {
            out(response, Resp.builder().setMessage("电话号码已存在").setState(false).setStatus(401));
        }
    }

    @RequestMapping(value = "findByUsername")
    public void findByUsername(@RequestParam("username") String username, HttpServletResponse response) throws SQLException {
        if (StringUtils.isBlank(username)) {
            out(response, Resp.builder().setMessage("用户名不能为空").setState(false).setStatus(500));
        }
        User user = userService.findByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            out(response, Resp.builder().setMessage("用户名可以使用").setState(true).setStatus(200));
        } else {
            out(response, Resp.builder().setMessage("用户名已存在").setState(false).setStatus(401));
        }
    }

    @RequestMapping(value = "phoneLogin",method = RequestMapping.RequestConst.METHOD_POST)
    public void phoneLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String phoneNumber = request.getParameter("phoneNumber");
        String validCode = request.getParameter("validCode");
        User user = userService.findByPhone(phoneNumber);
        if(ObjectUtils.isEmpty(user)){
            out(response,Resp.builder().setMessage("用户未注册").setState(false).setStatus(500));
        }else{
            Jedis jedis = JedisUtils.getJedis();
            String validCodeResult = jedis.get("phoneLogin_phoneNumber:" + phoneNumber);
            if(!validCode.equals(validCodeResult)){
                out(response,Resp.builder().setMessage("验证码输入有误，请重新获输入").setState(false).setStatus(403));
                return ;
            }else{
                user.setPassword(null).setSalt(null);
                session.setAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_KEY,user);
                session.setAttribute(LoginAuthorizationFilter.AUTHORIZATION_SESSION_STATE_KEY,true);
                out(response,Resp.builder().setMessage("登录成功,3秒后跳转首页").setState(true).setStatus(200));
                return ;
            }
        }
    }
}
