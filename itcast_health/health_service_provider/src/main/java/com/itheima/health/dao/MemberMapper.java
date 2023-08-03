package com.itheima.health.dao;

import com.itheima.health.pojo.Member;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/29
 * @description ：
 * @version: 1.0
 */
public interface MemberMapper {




   Member selectMemberByPhone(String phone);


   void addMember(Member member);
}
