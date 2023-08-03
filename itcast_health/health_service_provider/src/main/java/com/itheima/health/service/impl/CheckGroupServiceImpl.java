package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupMapper;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/20
 * @description ：
 * @version: 1.0
 */
@Service
@Slf4j
public class CheckGroupServiceImpl implements CheckGroupService {


    @Autowired
    private CheckGroupMapper checkGroupMapper;


    @Transactional
    @Override
    public void addCheckGroup(CheckGroup checkGroup, Integer[] checkItemIds) {
        log.debug("CheckGroup checkGroup:{}, Integer[] checkItemIds:{}", checkGroup, checkItemIds);

        try {
            checkGroupMapper.addCheckGroup(checkGroup);
            addCheckIemForGroup(checkGroup.getId(), checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {


        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());


        Page<CheckGroup> byQueryString = checkGroupMapper.findByQueryString(queryPageBean.getQueryString());


        return new PageResult(byQueryString.getTotal(), byQueryString.getResult());


    }

    @Override
    public CheckGroup findById(Integer id) {


        return checkGroupMapper.findById(id);


    }

    @Override
    public List<Integer> findCheckItemIdByCheckGroupId(Integer checkGroupId) {

        return checkGroupMapper.findCheckItemIdByCheckGroupId(checkGroupId);


    }

    @Transactional
    @Override
    public void edit(CheckGroup checkGroup, Integer[] ids) {


        //更新检查组信息
        checkGroupMapper.edit(checkGroup);
        //删除检查组关联的检查项
        checkGroupMapper.deleByCheckGroupId(checkGroup.getId());
        //新增检查组关联的检查项
        addCheckIemForGroup(checkGroup.getId(), ids);

    }


    @Override
    public List<CheckGroup> findAllCheckGroup() {



        return checkGroupMapper.findAllCheckGroup();
    }


    private void addCheckIemForGroup(Integer CheckGroupId, Integer[] checkItemIds) {
        if (checkItemIds != null && checkItemIds.length > 0) {
            for (Integer checkItemId : checkItemIds) {
                Map map = new HashMap();
                map.put("checkGroupId", CheckGroupId);
                map.put("checkItemId", checkItemId);
                checkGroupMapper.addCheckGroupCheckItem(map);
            }
        }


    }
}
