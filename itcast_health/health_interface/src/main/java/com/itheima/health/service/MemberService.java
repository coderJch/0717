package com.itheima.health.service;

import java.util.List;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/30
 * @description ：
 * @version: 1.0
 */
public interface MemberService {


    void smsLogin(String telephone);



    List<Integer> findMemberCountByMonth(List<String> monthList);
}
