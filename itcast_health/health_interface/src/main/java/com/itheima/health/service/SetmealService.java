package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/21
 * @description ：
 * @version: 1.0
 */
public interface SetmealService {




   void addSetmeal(Setmeal setmeal,Integer[] checkGroupIds);


    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
   PageResult findPage(QueryPageBean queryPageBean);



   List<Setmeal> findAllSetmeal();



   Setmeal selectSetmealById(Integer id);
}
