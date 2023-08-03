package com.itheima.health.jobs;

import com.itheima.health.common.RedisConst;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/22
 * @description ：
 * @version: 1.0
 */
public class ClearImageJob {

    @Autowired
    private JedisPool jedisPool;



    public void clearImageJob(){


        //计算redis中两个集合的差值，获取垃圾图片名称
        Set<String> set  = jedisPool.getResource()
                .sdiff(RedisConst.SETMEAL_PIC_RESOURCES,
                        RedisConst.SETMEAL_PIC_DB_RESOURCES);

        for (String s : set) {

            QiniuUtils.deleteFileFromQiniu(s);

            //删除redis中的数据
            jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES,s);

        }

    }
}
