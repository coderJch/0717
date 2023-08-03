package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import jdk.nashorn.internal.runtime.Debug;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.yetus.audience.InterfaceAudience;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/18
 * @description ：
 * @version: 1.0
 */


@RestController
@Slf4j
@RequestMapping("checkitem")
public class CheckitemController {


    @Reference
    private CheckItemService checkItemService;


    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        log.debug("CheckitemController checkItem:{}", checkItem);

        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConst.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            return new Result(true, MessageConst.ADD_CHECKITEM_FAIL);
        }

    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        log.debug("CheckitemController queryPageBean:{}", queryPageBean);
        try {
            return checkItemService.findByPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();

            return new PageResult(0L, new ArrayList());
        }
    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id) {
        log.debug("CheckitemController deleteById:{}", id);

        try {
            checkItemService.deleteById(id);
            return new Result(true, MessageConst.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping("/findCheckItemById")
    public Result findCheckItemById(Integer id) {


        try {
            CheckItem data = checkItemService.findCheckItemById(id);

            return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConst.QUERY_CHECKITEM_FAIL);
        }

    }


    @RequestMapping("/editCheckItem")
    public Result editCheckItem(@RequestBody CheckItem checkItem) {


        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConst.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.EDIT_CHECKITEM_FAIL);
        }


    }

    @RequestMapping("/findAllCheckItem")
    public Result findAllCheckItem() {


        try {
            List<CheckItem> allCheckItem = checkItemService.findAllCheckItem();
            return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, allCheckItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_CHECKITEM_FAIL);
        }

    }
}
