package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/29
 * @description ：
 * @version: 1.0
 */

@RestController
@RequestMapping("mobile/order/")
@Slf4j
public class OrderController {

    @Reference
    private OrderService orderService;


    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("submit")
    public Result submitOrder(@RequestBody Map<String, String> submitMap) {

        log.debug("Map<String,String> submitMap :{}", submitMap);
        String telephone = submitMap.get("telephone");
        String validateCode = submitMap.get("validateCode");


        //获取redis中的验证码
        String codeInRedis = jedisPool.getResource().get(telephone + RedisConst.SENDTYPE_ORDER);
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {
            return new Result(false, MessageConst.VALIDATECODE_ERROR);
        }

        Result result = null;
        try {
            //根据预约时间查询是否 这一天有预约设置
            result = orderService.add(submitMap);
            if (result.isFlag()) {
                // 发送短信通知
                String orderDate = submitMap.get("orderDate");
                //SMSUtils.sendShortMessage(telephone,orderDate);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
        return result;
    }


    @RequestMapping("findById")
    public Result findById4Detail(@RequestParam("id") Integer id) {
        log.debug("Integer id:{}", id);

        try {
            Map byId4Detail = orderService.findById4Detail(id);


            return new Result(true, MessageConst.QUERY_SETMEAL_SUCCESS, byId4Detail);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_SETMEAL_SUCCESS);
        }


    }

}
