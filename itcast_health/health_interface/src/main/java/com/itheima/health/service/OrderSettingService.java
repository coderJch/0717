package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/24
 * @description ：
 * @version: 1.0
 */
public interface OrderSettingService {


    /**
     * 添加预约内容
     *
     * @param list 预约设置列表
     */
     void add(List<OrderSetting> list);



    List<Map> selectOrderSettingByQueryDate(String QueryDate);



   void editNumberByDate(OrderSetting orderSetting);
}
