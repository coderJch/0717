package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/7
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/web/user")
@Slf4j
public class UserController {

    @Reference(interfaceClass = UserService.class)
    private UserService userService;






    @RequestMapping("/loginSuccess")
    public Result loginSuccess(){
        return new Result(true,MessageConst.LOGIN_SUCCESS);
    }
    @RequestMapping("/loginFail")
    public Result loginFail(){
        return new Result(false,"登录失败");
    }


  /*  @RequestMapping("/login")
    public Result login(String username, String password) {
        log.debug("oms backup,user:{},password:{}", username, password);
        if (userService.login(username, password)) {
            log.debug("login ok!!!");
            return new Result(true, MessageConst.ACTION_SUCCESS);
        } else {
            log.debug("login fail");
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }*/
}
