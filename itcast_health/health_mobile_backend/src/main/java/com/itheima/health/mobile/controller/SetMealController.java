package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/26
 * @description ：
 * @version: 1.0
 */

@RestController
@Slf4j
@RequestMapping("/mobile/setmeal")
public class SetMealController {

    @Reference
    private SetmealService setmealService;


    @RequestMapping("getSetmeal")
    public Result getSetmeal() {


        try {
            List<Setmeal> allSetmeal = setmealService.findAllSetmeal();


            return new Result(true, MessageConst.QUERY_SETMEALLIST_SUCCESS, allSetmeal);


        } catch (Exception e) {
            e.printStackTrace();

            return new Result(false, MessageConst.QUERY_SETMEALLIST_FAIL);
        }


    }


    @RequestMapping("findById")
    public Result findById(@RequestParam("id") Integer id) {

        try {


            return new Result(true, MessageConst.QUERY_SETMEAL_SUCCESS, setmealService.selectSetmealById(id));

        } catch (Exception e) {
            e.printStackTrace();

            return new Result(false, MessageConst.QUERY_SETMEAL_FAIL);

        }


    }


}
