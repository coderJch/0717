package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberMapper;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/30
 * @description ：
 * @version: 1.0
 */
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Transactional
    @Override
    public void smsLogin(String telephone) {
        // 根据手机号获取会员信息
        Member member = memberMapper.selectMemberByPhone(telephone);
        if (member == null) {
            // 不是会员，自动注册
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            // 调用Service保存会员
            memberMapper.addMember(member);
        }

    }
}
