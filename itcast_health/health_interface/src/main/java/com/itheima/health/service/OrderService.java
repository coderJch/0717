package com.itheima.health.service;

import com.itheima.health.entity.Result;

import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/29
 * @description ：
 * @version: 1.0
 */
public interface OrderService {


   Result add(Map<String,String> map);





   Map findById4Detail(Integer id);
}
