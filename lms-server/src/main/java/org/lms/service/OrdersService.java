package org.lms.service;


import org.lms.dto.OrdersDto;
import org.lms.entity.Orders;
import org.lms.response.Result;
import org.lms.vo.OrdersDetails;

import java.util.List;

/**
* @author jeang
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface OrdersService  {

    Result page(Integer pageNum, Integer pageSize, OrdersDto ordersDto);

    Result search(Orders orders);

    Result add(OrdersDetails ordersDetails);

    Result update(OrdersDetails ordersDetails);

    Result delete(List<Long> idList);
}
