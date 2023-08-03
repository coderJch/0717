package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.PermissionMapper;
import com.itheima.health.dao.RoleMapper;
import com.itheima.health.dao.UserMapper;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


@Service(interfaceClass = UserService.class)
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private PermissionMapper permissionMapper;





	@Override
	public boolean login(String username, String password) {
		log.debug("service_provide...u:{},p:{}",username,password);
		if("admin".equals(username) && "123".equals(password)){
			return true;
		}
		return false;
	}

	@Override
	public User findByUsername(String username) {


		User user = userMapper.findByUsername(username);

		Set<Role> roles = roleMapper.findByUserId(user.getId());


		for (Role role : roles) {

			Integer roleId = role.getId();
			// 获取权限列表
			Set<Permission> permissions = permissionMapper.findByRoleId(roleId);
			if (permissions != null && permissions.size() > 0){
				role.setPermissions(permissions);
			}

		}
		if(roles !=null && roles.size()>0){
			user.setRoles(roles);
		}
		return user;
	}
}
