package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.dto.CouponsDto;
import org.lms.entity.Coupons;
import org.lms.mapper.CouponsMapper;
import org.lms.response.Result;
import org.lms.service.CouponsService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
* @author jeang
* @description 针对表【coupons(优惠券表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class CouponsServiceImpl implements CouponsService {
    private CouponsMapper couponsMapper;
    private TransactionTemplate transactionTemplate;
    private StringRedisTemplate stringRedisTemplate;

    public CouponsServiceImpl(CouponsMapper couponsMapper, TransactionTemplate transactionTemplate, StringRedisTemplate stringRedisTemplate) {
        this.couponsMapper = couponsMapper;
        this.transactionTemplate = transactionTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, CouponsDto couponsDto) {
        PageHelper.startPage(pageNum,pageSize);
        List<Coupons> couponsList = couponsMapper.findAll(couponsDto);
        return Result.success(new PageInfo<>(couponsList));
    }

    @Override
    public Result add(CouponsDto couponsDto) {
        Coupons coupons = new Coupons();
        BeanUtils.copyProperties(couponsDto,coupons);
        couponsMapper.insertSelective(coupons);
        return Result.success();
    }

    @Override
    public Result update(CouponsDto couponsDto) {

        Coupons coupons = new Coupons();
        BeanUtils.copyProperties(couponsDto,coupons);
        transactionTemplate.execute(status -> {
            try {
                int updateCount = couponsMapper.updateByPrimaryKeySelective(coupons);
                return updateCount;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return Result.success();
    }

    @Override
    public Result delete(List<Long> ids) {
        transactionTemplate.execute(status -> {
            try {
                couponsMapper.deleteBatch(ids);
                return 1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return Result.success();
    }

    @Override
    public Result queryById(Long id) {
        Coupons coupons = couponsMapper.selectByPrimaryKey(id);
        return Result.success(coupons);
    }

    @Override
    public Result search(CouponsDto couponsDto) {
        List<CouponsDto> couponsDtoList = couponsMapper.searchByCouponsDto(couponsDto);
        return Result.success(couponsDtoList);
    }
}




