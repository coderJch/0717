package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangchenhui
 * @date ：Created in 2023/7/24
 * @description ：
 * @version: 1.0
 */
public interface OrderSettingMapper {




    Long selectCountByOrderDate(@Param("orderDate") Date orderDate);





    void addOrderSetting(OrderSetting orderSetting);







    void editOrderSetting(OrderSetting orderSetting);




   List<OrderSetting> selectOrderSettingByQueryDate(Map map);





   OrderSetting selectOrderSettingByDate(Date orderDate);



   void editReservationsByOrderDate(OrderSetting orderSetting);

}
