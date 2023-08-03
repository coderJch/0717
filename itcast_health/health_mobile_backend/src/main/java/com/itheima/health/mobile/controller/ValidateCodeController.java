package com.itheima.health.mobile.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.mobile.utils.SMSUtils;
import com.itheima.health.mobile.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/27
 * @description ：
 * @version: 1.0
 */


@RestController
@RequestMapping("/mobile/validateCode")
@Slf4j
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/send4Order")
    public Result send4Order(@RequestParam("telephone") String telephone) {
        //生成验证码

        Integer code = null;


        try {
            //发短信验证码
            //SMSUtils.sendShortMessage(telephone, code.toString());


            code = ValidateCodeUtils.generateValidateCode(4);
            System.out.println("验证码：》》》" + code.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.SEND_VALIDATECODE_FAIL);
        }

        jedisPool.getResource().setex(telephone + RedisConst.SENDTYPE_ORDER, 30000, code.toString());

        return new Result(true, MessageConst.SEND_VALIDATECODE_SUCCESS);


    }

    @RequestMapping("/send4Login")
    public Result send4Login(@RequestParam("telephone") String telephone) {

        try {
            //生成验证码

            Integer code = ValidateCodeUtils.generateValidateCode(6);

            String codeString = code.toString();

            log.debug("验证码》》》》:{}", codeString);
            //发送阿里云
            //SMSUtils.sendShortMessage(telephone,codeString);

            //将验证码存到redis
            jedisPool.getResource().setex(telephone + RedisConst.SENDTYPE_LOGIN, 300, codeString);

            return new Result(true, MessageConst.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(false,MessageConst.SEND_VALIDATECODE_FAIL);
        }


    }


}
