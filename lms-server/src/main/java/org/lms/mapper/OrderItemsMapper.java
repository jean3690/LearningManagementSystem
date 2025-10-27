package org.lms.mapper;


import org.apache.ibatis.annotations.Param;
import org.lms.entity.OrderItems;

import java.util.List;

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

    List<OrderItems> list(@Param("ids") List<Long> ids);

    int insertBatch(List<OrderItems> orderItemsList);

    int deleteBatch(@Param("itemIdList") List<Long> itemIdList);

    int deleteByOrderIdBatch(@Param("idList") List<Long> idList);
}
