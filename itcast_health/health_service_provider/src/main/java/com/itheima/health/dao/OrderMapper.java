package com.itheima.health.dao;

import com.itheima.health.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/29
 * @description ：
 * @version: 1.0
 */
public interface OrderMapper {




   List<Order> selectOrderByCondition(Order order);


   void addOrder(Order order);



   Map<String,Object> findById4Details(Integer id);
}
