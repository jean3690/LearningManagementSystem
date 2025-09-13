package org.lms.mapper;


import org.lms.entity.OrderItems;

/**
* @author jeang
* @description 针对表【order_items(订单项表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.OrderItems
*/
public interface OrderItemsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OrderItems record);

    int insertSelective(OrderItems record);

    OrderItems selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderItems record);

    int updateByPrimaryKey(OrderItems record);

}
