package com.itheima.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/8/2
 * @description ：
 * @version: 1.0
 */
public class SecurityUserDetailsService implements UserDetailsService {
    static Map<String, User> userDb = new HashMap<>();

    @Reference
    private UserService userService;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 模拟数据库的用户记录，如下User类是health_common中的自定义实体类User
    // 修改Role、Permission，为其增加不带参、带参构造方法，完成如下模拟过程
    static {
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("123");
        // 用户权限与角色
        Role role1 = new Role("系统管理员", "ROLE_ADMIN");
        role1.getPermissions().add(new Permission("添加权限", "add"));
        role1.getPermissions().add(new Permission("删除权限", "delete"));
        role1.getPermissions().add(new Permission("更新权限", "update"));
        role1.getPermissions().add(new Permission("查询权限", "find"));
        user1.getRoles().add(role1);
        userDb.put(user1.getUsername(), user1);

        User userZhangSan = new User();
        userZhangSan.setUsername("zhangsan");
        userZhangSan.setPassword("123");
        Role role2 = new Role("数据分析员", "ROLE_READER");
        ;
        role2.getPermissions().add(new Permission("查询权限", "find"));
        userZhangSan.getRoles().add(role2);
        userDb.put(userZhangSan.getUsername(), userZhangSan);

        User userLisi = new User();
        userLisi.setUsername("lisi");
        userLisi.setPassword("123");
        Role role3 = new Role("运营管理员", "ROLE_OMS");
        ;
        role3.getPermissions().add(new Permission("添加权限", "add"));
        role3.getPermissions().add(new Permission("更新权限", "update"));
        userLisi.getRoles().add(role3);
        userDb.put(userLisi.getUsername(), userLisi);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User currUser = userService.findByUsername(username);

        if (currUser == null) {
            return null;
        }
        // 获取用户角色及权限
        List<GrantedAuthority> authList = new ArrayList<>();
        for (Role role : currUser.getRoles()) {
            // 角色关键词
            authList.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            // 权限关键词
            for (Permission p : permissions) {
                authList.add(new SimpleGrantedAuthority(p.getKeyword()));
            }
        }
        // 如果密码未加密，必须用默认加密规则加密
        // 如果密码已加密，则不用使用默认规则加密
        String passwordInDb = currUser.getPassword();
        String encode = passwordEncoder.encode(passwordInDb);
        System.out.println("encode:"+encode);
        // 构建UserDetails对象
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, passwordInDb, authList);
        return userDetails;

    }
}
