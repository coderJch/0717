package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.common.RedisConst;
import com.itheima.health.dao.SetmealMapper;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/21
 * @description ：
 * @version: 1.0
 */
@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {


    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private SetmealMapper setmealMapper;

    @Transactional
    @Override
    public void addSetmeal(Setmeal setmeal, Integer[] checkGroupIds) {


        log.debug("SetmealServiceImpl Setmeal setmeal:{}, Integer[] checkGroupIds:{}", setmeal, checkGroupIds);
        //新增套餐
        setmealMapper.add(setmeal);
        if (checkGroupIds != null && checkGroupIds.length != 0) {
            for (Integer checkGroupId : checkGroupIds) {
                Map map = new HashMap<>();
                map.put("setmealId", setmeal.getId());
                map.put("checkgroupId", checkGroupId);
                setmealMapper.addSetmealIdCheckGroupId(map);
            }
        }
        //把图片名称存到redis db  set 集合
        jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        //开启分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        Page<Setmeal> setmealPage = setmealMapper.findByQueryString(queryPageBean.getQueryString());

        return new PageResult(setmealPage.getTotal(), setmealPage.getResult());

    }

    @Override
    public List<Setmeal> findAllSetmeal() {


        return setmealMapper.findAllSetmeal();



    }

    @Override
    public Setmeal selectSetmealById(Integer id) {


        return  setmealMapper.findSetmealById(id);
    }
}
