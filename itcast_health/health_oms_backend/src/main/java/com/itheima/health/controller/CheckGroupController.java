package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/20
 * @description ：
 * @version: 1.0
 */

@RestController
@RequestMapping("CheckGroup")
public class CheckGroupController {


    @Reference
    private CheckGroupService checkGroupService;


    @RequestMapping("/addCheckGroup")
    public Result addCheckGroup(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {


        try {
            checkGroupService.addCheckGroup(checkGroup, checkItemIds);
            return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKGROUP_FAIL);
        }


    }

    @RequestMapping("/findPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean) {


        try {
            return checkGroupService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L, new ArrayList());
        }


    }

    @RequestMapping("/findCheckGroupById")
    public Result findById(Integer id) {

        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true, MessageConst.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findCheckItemIdByCheckGroupId")
    public Result findCheckItemIdByCheckGroupId(Integer id) {


        try {
            List<Integer> checkItemIdByCheckGroupId = checkGroupService.findCheckItemIdByCheckGroupId(id);
            return new Result(true, MessageConst.ACTION_SUCCESS, checkItemIdByCheckGroupId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {


        try {
            checkGroupService.edit(checkGroup, checkItemIds);


            return new Result(true, MessageConst.EDIT_CHECKGROUP_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();


            return new Result(false, MessageConst.EDIT_CHECKGROUP_FAIL);
        }


    }


    @RequestMapping("/findAllCheckGroup")
    public Result findAllCheckGroup() {
        try {
            return new Result(true, MessageConst.QUERY_CHECKGROUP_SUCCESS, checkGroupService.findAllCheckGroup());
        } catch (Exception e) {

            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_CHECKGROUP_FAIL);

        }


    }


}
