package org.lms.mapper;


import org.lms.entity.Coupons;

/**
* @author jeang
* @description 针对表【coupons(优惠券表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:44
* @Entity .entity.Coupons
*/
public interface CouponsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Coupons record);

    int insertSelective(Coupons record);

    Coupons selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Coupons record);

    int updateByPrimaryKey(Coupons record);

}
