package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/21
 * @description ：
 * @version: 1.0
 */


@RequestMapping("setmeal")
@RestController
@Slf4j
public class SetMealController {


    @Autowired
    private JedisPool jedisPool;
    @Reference
    private SetmealService setmealService;

    /**
     * 图片上传
     *
     * @return
     */
    @RequestMapping("/upload")
    public Result uploadFile(@RequestParam("imgFile") MultipartFile multipartFile) {

        log.debug("SetMealController MultipartFile :{}", multipartFile);

        try {
            //获取文件原名称
            String originalFilename = multipartFile.getOriginalFilename();
            //生成存储唯一名称
            String keyName = originalFilename + "_" + UUID.randomUUID().toString().replace("-", "");
            //获取字节对象
            byte[] bytes = multipartFile.getBytes();
            //上传文件
            QiniuUtils.upload2Qiniu(bytes, keyName);


            //把图片名称存储到redis
            jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_RESOURCES,keyName);

            //返回访问路径
            return new Result(true, MessageConst.UPLOAD_SUCCESS, QiniuUtils.qiniu_img_url_pre + keyName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConst.PIC_UPLOAD_FAIL);
        }
    }


    @RequestMapping("add")
    public Result addSetmeal(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {
        log.debug("SetMealController setmeal:{},checkGroupIds:{}", setmeal, checkGroupIds);
        try {
            setmeal.setImg(setmeal.getImg().replace(QiniuUtils.qiniu_img_url_pre, ""));
            setmealService.addSetmeal(setmeal, checkGroupIds);
            return new Result(true, MessageConst.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {


        log.debug("SetMealController queryPageBean:{}", queryPageBean);

        try {
            return setmealService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L, new ArrayList());
        }


    }


}
