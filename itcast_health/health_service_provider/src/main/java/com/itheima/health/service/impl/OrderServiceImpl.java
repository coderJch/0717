package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.dao.MemberMapper;
import com.itheima.health.dao.OrderMapper;
import com.itheima.health.dao.OrderSettingMapper;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderService;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/29
 * @description ：
 * @version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderSettingMapper orderSettingMapper;


    @Autowired
    MemberMapper memberMapper;

    @Autowired
    OrderMapper orderMapper;

    /**
     * 新增订单信息
     *
     * @param map
     * @return
     */
    @Transactional
    @Override
    public Result add(Map<String, String> map) {


        try {
            //根据预约时间查询是否 这一天有预约设置
            Date orderDate = DateUtils.parseString2Date(map.get("orderDate"));
            OrderSetting orderSetting = orderSettingMapper.selectOrderSettingByDate(orderDate);
            // 如果没有 返回没有预约设置
            if (orderSetting == null) {
                return new Result(false, MessageConst.SELECTED_DATE_CANNOT_ORDER);
            }
            //如果有 看是否已经约满
            int reservations = orderSetting.getReservations();
            int number = orderSetting.getNumber();
            if (reservations >= number) {
                return new Result(false, MessageConst.ORDER_FULL);
            }
            //根据电话号码查询会员
            Member member = memberMapper.selectMemberByPhone(map.get("telephone"));
            int setmealId = Integer.parseInt(map.get("setmealId"));    // 套餐ID;
            if (member != null) {
                Integer memberId = member.getId();
                // 根据memberId setmealId orderDate 查询Order信息
                Order order = new Order(null, memberId, orderDate, null, null, setmealId);
                List<Order> orders = orderMapper.selectOrderByCondition(order);
                //如果有订单信息
                if (orders != null && orders.size() > 0) {
                    return new Result(false, MessageConst.HAS_ORDERED);
                }
            } else {
                //保存会员信息
                member = new Member();
                member.setName(map.get("name"));
                member.setPhoneNumber(map.get("telephone"));
                member.setSex(map.get("sex"));
                member.setRegTime(new Date());
                member.setIdCard(map.get("idCard"));
                memberMapper.addMember(member);
            }
            //更新预约设置
            orderSetting.setReservations(reservations + 1);
            orderSettingMapper.editReservationsByOrderDate(orderSetting);
            //保存订单
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(orderDate);
            order.setOrderType(Order.ORDERTYPE_WEIXIN);
            order.setOrderStatus(Order.ORDERSTATUS_NO);
            order.setSetmealId(setmealId);
            orderMapper.addOrder(order);
            return new Result(true, MessageConst.ORDER_SUCCESS,order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map findById4Detail(Integer id) {
        Map map = orderMapper.findById4Details(id);
        try {
            map.put("orderDate",DateUtils.parseDate2String((Date) map.get("orderDate")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;


    }
}
