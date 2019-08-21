package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.IUserService;
import com.lh.auto.limit.annotation.ResourceLimit;
import com.lh.auto.limit.model.LimitService;
import com.lh.auto.limit.model.LimitType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@Api(value = "用户服务",description = "用户的注册登录修改")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/check/{data}/{type}")
    @ApiOperation("用户数据校验")
    public ResponseEntity<Boolean> checkData(@ApiParam(name = "data",value = "校验的数据") @PathVariable("data") String data,
                                             @ApiParam(name = "type",example = "1",value = "数据类型,1 or 2 ,1 : 用户名,2 : 手机")@PathVariable("type") Integer type) {
        return ResponseEntity.ok(userService.checkData(data, type));
    }

    /**
     * 发送短信
     * @param phone
     * @return
     */
    @PostMapping("code")
    @ApiOperation("发送验证码")
    public ResponseEntity<Void> sendCode(@ApiParam(name = "phone",required = true,value = "手机号") @RequestParam("phone") String phone) {
        userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("register")
    @ApiOperation("注册用户")
    /**
     *  限流key为user_register:IP
     *  seconds: 3600000s == 1h
     *  type: Ip限流
     *  capacity: 容量 1,每次限流最大请求次数为1
     *  initCapacity： 初始化容量 ，比capacity 小
     *  useLimitService： JDK方式限流，基于内存方式
     *  secondsAddCount: 每seconds后补充secondsAddCount次请求
     */
    @ResourceLimit(
            key = "user_register",
            seconds = 3600000,
            type = LimitType.IP,
            capacity = 1,
            initCapacity = 1,
            useLimitService = LimitService.JDK,
            secondsAddCount = 1,
            fallbackFactory = UserFallbackFactory.class,
            method = "fallback")
    public ResponseEntity<Void> register(@Valid User user, @ApiParam(hidden = true) BindingResult result,
                                         @ApiParam(name = "code",value = "验证码") @RequestParam("code") String code) {
        if (result.hasFieldErrors()) {
            throw new RuntimeException(result.getFieldErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining("|")));
        }
        userService.register(user, code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("query")
    @ApiOperation("登录")
    public ResponseEntity<User> queryUserByUsernameAndPassword(
            @RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.ok(userService.queryUserByUsernameAndPassword(username, password));
    }

    @Slf4j
    static class UserFallbackFactory {

        public static ResponseEntity<Void> fallback(Object ...args){
          log.error("jdk resource limit excuet ... paramters {}", Arrays.asList(args));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
