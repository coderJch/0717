package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/18
 * @description ：
 * @version: 1.0
 */
public interface CheckItemService {


  void add(CheckItem checkItem);


  /**
   * 分页查询检查项
   * @param queryPageBean
   * @return
   */
  PageResult findByPage(QueryPageBean queryPageBean);



  void deleteById(Integer id);





  CheckItem findCheckItemById(Integer id);





  void edit(CheckItem checkItem);






  List<CheckItem> findAllCheckItem();



}
