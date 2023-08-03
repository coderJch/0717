package com.itheima.health.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/8/2
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/test")
public class TestSecurityController {

    @PreAuthorize("hasAuthority('add')")
    @RequestMapping("/addData")
    public String addData(){
        return "add ok";
    }

    @PreAuthorize("hasAuthority('update')")
    @RequestMapping("/updateData")
    public String updateData(){
        return "update ok";
    }

    @PreAuthorize("hasAuthority('delete')")
    @RequestMapping("/delData")
    public String deleteData(){
        return "delete ok";
    }

    @PreAuthorize("hasAuthority('find')")
    @RequestMapping("/findData")
    public String findAll(){
        return "find ok";
    }








}
