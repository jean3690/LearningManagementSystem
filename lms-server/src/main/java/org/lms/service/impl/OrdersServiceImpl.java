package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import org.lms.entity.Orders;
import org.lms.mapper.OrdersMapper;
import org.lms.response.Result;
import org.lms.service.OrdersService;
import org.lms.vo.OrdersDetails;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author jeang
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class OrdersServiceImpl implements OrdersService {
    private OrdersMapper ordersMapper;
    private StringRedisTemplate stringRedisTemplate;

    public OrdersServiceImpl(OrdersMapper ordersMapper, StringRedisTemplate stringRedisTemplate) {
        this.ordersMapper = ordersMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<OrdersDetails> ordersDetailsList = ordersMapper.details();
        return Result.success(ordersDetailsList);
    }

    @Override
    public Result search(Orders orders) {
        List<OrdersDetails> ordersDetailsList = ordersMapper.search(orders);
        return Result.success(ordersDetailsList);
    }
}




