package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingMapper;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/24
 * @description ：
 * @version: 1.0
 */
@Service
@Slf4j
public class OrderSettingServiceImpl implements OrderSettingService {


    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Transactional
    @Override
    public void add(List<OrderSetting> list) {


        log.debug("OrderSettingServiceImpl List<OrderSetting>:{}", list);


        if (list != null && list.size() != 0) {
            for (OrderSetting orderSetting : list) {
                Long count = orderSettingMapper.selectCountByOrderDate(orderSetting.getOrderDate());
                if (count == 0) {
                    orderSettingMapper.addOrderSetting(orderSetting);
                } else {
                    orderSettingMapper.editOrderSetting(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> selectOrderSettingByQueryDate(String QueryDate) {

        String beginDate = QueryDate + "-1";
        String endDate = QueryDate + "-31";
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("beginDate", beginDate);
        stringMap.put("endDate", endDate);
        List<OrderSetting> list = orderSettingMapper.selectOrderSettingByQueryDate(stringMap);
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map<String, Object> orderSettingMap = new HashMap();
            // 预约日期（几号）
            orderSettingMap.put("date", orderSetting.getOrderDate().getDate());
            // 可预约人数
            orderSettingMap.put("number", orderSetting.getNumber());
            // 已预约人数
            orderSettingMap.put("reservations", orderSetting.getReservations());
            // 添加到集合
            data.add(orderSettingMap);
        }
        return data;
    }

    @Transactional
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {

        Long aLong = orderSettingMapper.selectCountByOrderDate(orderSetting.getOrderDate());
        if (aLong > 0) {
            orderSettingMapper.editOrderSetting(orderSetting);
        } else {
            orderSettingMapper.addOrderSetting(orderSetting);
        }


    }
}