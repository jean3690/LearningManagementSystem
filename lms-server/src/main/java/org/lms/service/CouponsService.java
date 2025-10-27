package org.lms.service;


import org.lms.dto.CouponsDto;
import org.lms.response.Result;

import java.util.List;

/**
* @author jeang
* @description 针对表【coupons(优惠券表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface CouponsService {

    Result page(Integer pageNum, Integer pageSize, CouponsDto couponsDto);

    Result add(CouponsDto couponsDto);

    Result update(CouponsDto couponsDto);

    Result delete(List<Long> ids);

    Result queryById(Long id);
}
