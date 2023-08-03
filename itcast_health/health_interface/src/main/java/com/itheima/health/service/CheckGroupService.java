package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/20
 * @description ：
 * @version: 1.0
 */
public interface CheckGroupService {

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkItemIds
     */
    void addCheckGroup(CheckGroup checkGroup,Integer[] checkItemIds);



    PageResult findPage(QueryPageBean queryPageBean);


    /**
     * 根据Id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);



    List<Integer> findCheckItemIdByCheckGroupId(Integer checkGroupId);



    void edit(CheckGroup checkGroup,Integer[] ids);


    /**
     * 查询所有检查组信息
     * @return
     */

    List<CheckGroup> findAllCheckGroup();


}
