package org.lms.mapper;


import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.lms.Enum.OpreationType;
import org.lms.annotation.AutoFill;
import org.lms.dto.CouponsDto;
import org.lms.entity.Coupons;

import java.util.List;

/**
* @author jeang
* @description 针对表【coupons(优惠券表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:44
* @Entity .entity.Coupons
*/
public interface CouponsMapper {

    int deleteByPrimaryKey(Long id);
    @AutoFill({OpreationType.CREATEDAT})
    int insert(Coupons record);
    @AutoFill({OpreationType.CREATEDAT})
    int insertSelective(Coupons record);

    Coupons selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Coupons record);

    int updateByPrimaryKey(Coupons record);
    List<Coupons> findAll();

    void deleteBatch(@Param("ids") List<Long> ids);

    List<CouponsDto> searchByCouponsDto(CouponsDto couponsDto);
}
