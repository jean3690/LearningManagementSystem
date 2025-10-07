package org.lms.mapper;


import org.lms.entity.Orders;
import org.lms.vo.OrdersDetails;

import java.util.List;

/**
* @author jeang
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.Orders
*/
public interface OrdersMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    List<OrdersDetails> details();

    List<OrdersDetails> search(Orders orders);
}
