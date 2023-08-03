package com.itheima.health.dao;

import com.itheima.health.pojo.Permission;

import java.util.Set;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/8/3
 * @description ：
 * @version: 1.0
 */
public interface PermissionMapper {



    Set<Permission> findByRoleId(Integer id);
}
