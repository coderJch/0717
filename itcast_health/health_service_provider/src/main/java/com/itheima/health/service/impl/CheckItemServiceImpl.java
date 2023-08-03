package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckItemMapper;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/18
 * @description ：
 * @version: 1.0
 */

@Slf4j
@Service
public class CheckItemServiceImpl implements CheckItemService {


    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    public void add(CheckItem checkItem) {

        log.debug("CheckItemServiceImpl checkItem:{}", checkItem);
        checkItemMapper.add(checkItem);


    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {

        //
        log.debug("CheckItemServiceImpl queryPageBean: {}", queryPageBean);


        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        Page<CheckItem> page = checkItemMapper.findByPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {


        //查询检查组中有没有检查项


        Long count = checkItemMapper.findCountById(id);

        if (count > 0) {
            throw new RuntimeException("该检查项在检查组中存在,不能删除!");
        }

        checkItemMapper.deleteCheckItemById(id);
    }


    /**
     * 根据id查询检查项
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem findCheckItemById(Integer id) {


        return checkItemMapper.findCheckItemById(id);
    }


    @Transactional
    @Override
    public void edit(CheckItem checkItem) {

        log.debug("CheckItem checkItem:{}", checkItem);
        checkItemMapper.editCheckItem(checkItem);


    }

    @Override
    public List<CheckItem> findAllCheckItem() {
        return checkItemMapper.findALL();
    }

}
