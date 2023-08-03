package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/30
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("mobile/login")
@Slf4j
public class LoginController {


    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;


    @RequestMapping("/smsLogin")
    public Result smsLogin(@RequestBody Map<String, String> map) {

        try {
            String telephone = map.get("telephone");
            String validateCode = map.get("validateCode");
            //根据电话号码查询redis

            String codeInRedis = jedisPool.getResource().get(telephone + RedisConst.SENDTYPE_LOGIN);

            if (codeInRedis == null || codeInRedis.length() == 0) {
                return new Result(false, "验证码不存在");
            }
            if (!codeInRedis.equals(validateCode)) {
                return new Result(false, "验证码不正确");
            }
            memberService.smsLogin(telephone);
            return new Result(true, MessageConst.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }

    }


}
