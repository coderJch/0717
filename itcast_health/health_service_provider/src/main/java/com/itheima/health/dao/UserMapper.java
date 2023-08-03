package com.itheima.health.dao;

import com.itheima.health.pojo.User;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/8/3
 * @description ：
 * @version: 1.0
 */
public interface UserMapper {


    /**
     * 基于名字，获取用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

}
