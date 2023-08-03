package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/7
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = UserService.class)
@Slf4j
public class UserServiceImpl implements UserService {
	@Override
	public boolean login(String username, String password) {
		log.debug("service_provide...u:{},p:{}",username,password);
		if("admin".equals(username) && "123".equals(password)){
			return true;
		}
		return false;
	}
}
