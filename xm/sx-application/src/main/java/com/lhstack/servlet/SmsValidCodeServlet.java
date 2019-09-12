package com.lhstack.servlet;

import com.lhstack.common.annotation.RequestMapping;
import com.lhstack.common.annotation.RequestParam;
import com.lhstack.pojo.res.Resp;
import com.lhstack.service.ISmsService;
import com.lhstack.service.impl.SmsServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@WebServlet("/sms/*")
public class SmsValidCodeServlet extends AbstractServlet {

    private ISmsService smsService = new SmsServiceImpl();

    private Random random = new Random();

    @RequestMapping(value = "send",method = RequestMapping.RequestConst.METHOD_POST)
    public void sendSmsValid(@RequestParam("phoneNumber") String phoneNumber, HttpServletResponse response){
        Map<String,String> map = new HashMap<>();
        map.put("key","phoneLogin_phoneNumber:" + phoneNumber);
        map.put("value",randomValidCode());
        map.put("phoneNumber",phoneNumber);
        smsService.send(map);
        out(response, Resp.builder().setStatus(200).setState(true).setMessage("验证码发送成功，请打开手机查看"));
    }

    public String randomValidCode(){
        int validCode = random.nextInt(9999 - 1000) + 1000;
        return "" + (validCode + 1000);
    }
}
