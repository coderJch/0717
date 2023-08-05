package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/21
 * @description ：
 * @version: 1.0
 */
public interface SetmealMapper {



   void add(Setmeal setmeal);



   void addSetmealIdCheckGroupId(Map map);


   Page<Setmeal> findByQueryString(@Param("queryString") String queryString);


   List<Setmeal> findAllSetmeal();



   Setmeal findSetmealById(Integer id);



   List<Map<String,Object>> findSetmealForCount();
}
