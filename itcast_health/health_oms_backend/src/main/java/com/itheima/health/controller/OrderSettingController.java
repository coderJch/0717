package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/24
 * @description ：
 * @version: 1.0
 */

@RestController
@Slf4j
@RequestMapping("ordersetting")
public class OrderSettingController {


    @Reference
    private OrderSettingService orderSettingService;


    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile multipartFile) {

        log.debug("OrderSettingController multipartFile:{}", multipartFile);
        //封装成List集合
        try {
            List<String[]> strings = POIUtils.readExcel(multipartFile);
            if (strings != null && strings.size() > 0) {
                List<OrderSetting> orderSettingList = new ArrayList<>();
                for (String[] orderSettingString : strings) {
                    if (orderSettingString[0] == null) {
                        continue;
                    }
                    OrderSetting orderSetting = new OrderSetting(new Date(orderSettingString[0]), Integer.parseInt(orderSettingString[1]));
                    orderSettingList.add(orderSetting);
                }
                orderSettingService.add(orderSettingList);
                return new Result(true, MessageConst.ORDERSETTING_SUCCESS);
            } else {
                throw new RuntimeException("录入文件内容不能为空");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ORDERSETTING_FAIL);
        }


    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(@RequestParam("queryDate") String queryDate) {


        try {
            List<Map> mapList =
                    orderSettingService.selectOrderSettingByQueryDate(queryDate);
            return new Result(true, MessageConst.QUERY_ORDER_SUCCESS, mapList);


        } catch (Exception e) {
            e.printStackTrace();

            return new Result(false, MessageConst.QUERY_ORDER_FAIL);
        }


    }


    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConst.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConst.ORDERSETTING_FAIL);
        }
    }
}
