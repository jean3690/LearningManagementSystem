package org.lms.service;


import org.lms.entity.Orders;
import org.lms.response.Result;

/**
* @author jeang
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface OrdersService  {

    Result page(Integer pageNum, Integer pageSize);

    Result search(Orders orders);
}
