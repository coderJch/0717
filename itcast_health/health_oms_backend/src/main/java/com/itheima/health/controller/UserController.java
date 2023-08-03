package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    @RequestMapping("/getUsername")
    public Result getUsername() {
        try{
            //从授权框架上下文获取授权对象，再从授权对象获取授权框架用户对象User,最后获取用户名
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
            log.debug("user:{}",user);
            if (user != null){
                return new Result(true,MessageConst.ACTION_SUCCESS,user.getUsername());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return new Result(false,MessageConst.ACTION_FAIL);
    }
}
