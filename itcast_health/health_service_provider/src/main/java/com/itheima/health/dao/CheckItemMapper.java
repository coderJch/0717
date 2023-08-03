package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/18
 * @description ：
 * @version: 1.0
 */
public interface CheckItemMapper {



  void add(CheckItem checkItem);




  Page<CheckItem> findByPage(@Param("queryString") String queryString);


  /**
   * 根据检查xaingId
   * @param id
   * @return
   */
  Long findCountById(@Param("id") Integer id);



  void deleteCheckItemById(@Param("id") Integer id);



  CheckItem findCheckItemById(@Param("id") Integer id);



  void editCheckItem(CheckItem checkItem);



  List<CheckItem> findALL();

}
