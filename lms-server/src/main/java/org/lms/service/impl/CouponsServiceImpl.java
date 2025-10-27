package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.constant.RedisConstant;
import org.lms.dto.CouponsDto;
import org.lms.entity.Coupons;
import org.lms.event.CommonRedisCacheClearEvent;
import org.lms.mapper.CouponsMapper;
import org.lms.response.Result;
import org.lms.service.CouponsService;
import org.lms.utils.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
* @author jeang
* @description 针对表【coupons(优惠券表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class CouponsServiceImpl implements CouponsService {
    private final CouponsMapper couponsMapper;
    private final TransactionTemplate transactionTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public CouponsServiceImpl(CouponsMapper couponsMapper,
                              TransactionTemplate transactionTemplate,
                              StringRedisTemplate stringRedisTemplate,
                              ApplicationEventPublisher eventPublisher) {
        this.couponsMapper = couponsMapper;
        this.transactionTemplate = transactionTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, CouponsDto couponsDto) {
        String key;
        if(ObjectUtils.isEmpty(couponsDto)){
            key = RedisConstant.COUPONS_PAGE + pageNum + ":" + pageSize;
        }else {
            String hash = DigestUtils.md5DigestAsHex(JsonUtil.toJson(couponsDto).getBytes());
            key = RedisConstant.COUPONS_PAGE + pageNum + ":" + pageSize+":"+hash;
        }
        String json = stringRedisTemplate.opsForValue().get(key);
        if(json!=null){
            if(json.equals("_NULL_")){
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            List<Coupons> couponsList = JsonUtil.jsonToObj(json, List.class,Coupons.class);
            boolean present = Optional.ofNullable(couponsList).isPresent();
            if(present){
                return Result.success(new PageInfo<>(couponsList));
            }
            return Result.success(new PageInfo<>(Collections.emptyList()));
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Coupons> couponsList = couponsMapper.findAll(couponsDto);
        if (ObjectUtils.isEmpty(couponsList)) {
            stringRedisTemplate.opsForValue().set(key,"_NULL_",ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
            return Result.success(Collections.emptyList());
        }
        PageInfo<Coupons> pageInfo = new PageInfo<>(couponsList);
        stringRedisTemplate.opsForValue().set(key,JsonUtil.toJson(couponsList), ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
        return Result.success(pageInfo);
    }

    @Override
    public Result add(CouponsDto couponsDto) {
        Coupons coupons = new Coupons();
        BeanUtils.copyProperties(couponsDto,coupons);
        couponsMapper.insertSelective(coupons);
        clearPageCache();
        return Result.success();
    }

    @Override
    public Result update(CouponsDto couponsDto) {
        Coupons coupons = new Coupons();
        BeanUtils.copyProperties(couponsDto,coupons);
        transactionTemplate.execute(status -> {
            try {
                return couponsMapper.updateByPrimaryKeySelective(coupons);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        clearPageCache();
        return Result.success();
    }

    private void clearPageCache() {
        String prefix = RedisConstant.COUPONS_PAGE;
        eventPublisher.publishEvent(new CommonRedisCacheClearEvent(prefix));
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
        clearPageCache();
        return Result.success();
    }

    @Override
    public Result queryById(Long id) {
        Coupons coupons = couponsMapper.selectByPrimaryKey(id);
        return Result.success(coupons);
    }
}




