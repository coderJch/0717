package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/20
 * @description ：
 * @version: 1.0
 */
public interface CheckGroupMapper {


    void addCheckGroup(CheckGroup checkGroup);


    void addCheckGroupCheckItem(Map map);


    Page<CheckGroup> findByQueryString(@Param("queryString") String queryString);


    CheckGroup findById(@Param("id") Integer id);


    List<Integer> findCheckItemIdByCheckGroupId(@Param("checkGroupId") Integer checkGroupId);


    void edit(CheckGroup checkGroup);



    void deleByCheckGroupId(@Param("checkGroupId") Integer checkGroupId);



    List<CheckGroup> findAllCheckGroup();




}
