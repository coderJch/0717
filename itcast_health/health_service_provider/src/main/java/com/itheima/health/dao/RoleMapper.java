package com.itheima.health.dao;

import com.itheima.health.pojo.Role;

import java.util.List;
import java.util.Set;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/8/3
 * @description ：
 * @version: 1.0
 */
public interface RoleMapper {



    Set<Role> findByUserId(Integer id);
}
