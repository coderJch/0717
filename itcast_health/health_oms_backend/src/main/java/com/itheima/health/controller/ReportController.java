package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/8/5
 * @description ：
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/report")
public class ReportController {


    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {

        try {
            //提前6个月的月份时间列
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -6);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
            //String formatDate = simpleDateFormat.format(cal.getTime());
            List<String> monthList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                cal.add(Calendar.MONTH, 1);
                monthList.add(simpleDateFormat.format(cal.getTime()));
            }
            List<Integer> memberCountByMonth = memberService.findMemberCountByMonth(monthList);
            Map<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("months", monthList);
            stringObjectHashMap.put("memberCount", memberCountByMonth);
            return new Result(true, MessageConst.ACTION_SUCCESS, stringObjectHashMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ACTION_FAIL);
        }
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {

        try {
            List<Map<String, Object>> setmealForCount = setmealService.findSetmealForCount();

            List<String> setmealNames = new ArrayList<>();


            for (Map<String, Object> stringObjectMap : setmealForCount) {
                setmealNames.add((String) stringObjectMap.get("name"));

            }

            Map<String, Object> map = new HashMap<>();

            map.put("setmealNames",setmealNames);
            map.put("setmealCount",setmealForCount);

            return new Result(true,MessageConst.ACTION_SUCCESS,map);

        } catch (Exception e) {
            e.printStackTrace();

            return new Result(false,MessageConst.ACTION_FAIL,null);
        }

    }
}




