package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.lms.constant.RedisConstant;
import org.lms.dto.InvoicesDto;
import org.lms.entity.Invoices;
import org.lms.entity.Orders;
import org.lms.mapper.InvoicesMapper;
import org.lms.mapper.OrdersMapper;
import org.lms.response.Result;
import org.lms.service.InvoicesService;
import org.lms.util.RedisUtil;
import org.lms.utils.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
* @author jeang
* @description 针对表【invoices(发票表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
@Slf4j
public class InvoicesServiceImpl implements InvoicesService {
    private InvoicesMapper invoicesMapper;
    private OrdersMapper ordersMapper;
    private RedisUtil redisUtil;
    public InvoicesServiceImpl(InvoicesMapper invoicesMapper, OrdersMapper ordersMapper,RedisUtil redisUtil) {
        this.invoicesMapper = invoicesMapper;
        this.ordersMapper = ordersMapper;
        this.redisUtil = redisUtil;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, InvoicesDto invoicesDto) {
        String key = RedisConstant.INVOICE_PAGE + pageNum+":"+ pageSize;
        String json = redisUtil.getString(key);
        if(json!=null){
            if("_NULL_".equals(json)){
                return Result.success(Collections.emptyList());
            }
            log.info("invoice:page");
            return Result.success(JsonUtil.jsonToObj(json, PageInfo.class));
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Invoices> invoicesList = invoicesMapper.findAll(invoicesDto);
        if(CollectionUtils.isEmpty(invoicesList)){
            redisUtil.setString(key,"_NULL_", ThreadLocalRandom.current().nextLong(8,10), TimeUnit.MINUTES);
            return Result.success(Collections.emptyList());
        }
        PageInfo<Invoices> pageInfo = new PageInfo<>(invoicesList);
        redisUtil.setString(key,JsonUtil.toJson(pageInfo),ThreadLocalRandom.current().nextLong(8,10),TimeUnit.MINUTES);
        return Result.success(pageInfo);
    }

    @Override
    public Result queryById(Long id) {
        String key = RedisConstant.INVOICE_ID+id;
        String json = redisUtil.getString(key);
        if(json!=null){
            if("_NULL_".equals(json)){
                return Result.success(Optional.empty());
            }
            return Result.success(JsonUtil.jsonToObj(json,Invoices.class));
        }
        Invoices invoices = invoicesMapper.selectByPrimaryKey(id);
        if(Objects.isNull(invoices)){
            redisUtil.setString(key,"_NULL_",ThreadLocalRandom.current().nextLong(8,10),TimeUnit.MINUTES);
            return Result.success(Optional.empty());
        }
        redisUtil.setString(key,JsonUtil.toJson(invoices),ThreadLocalRandom.current().nextLong(8,10),TimeUnit.MINUTES);
        return Result.success(invoices);
    }

    @Override
    public Result add(InvoicesDto invoicesDto) {
        Long orderId = invoicesDto.getOrderId();
        Orders orders = ordersMapper.selectByPrimaryKey(orderId);
        if(orders==null){
            return Result.error();
        }
        Invoices invoices = new Invoices();
        BeanUtils.copyProperties(invoicesDto,invoices);
        invoicesMapper.insertSelective(invoices);
        redisUtil.deleteByPattern(RedisConstant.INVOICE_PAGE);
        return Result.success();
    }

    @Override
    public Result search(InvoicesDto invoicesDto) {
        List<Invoices> invoicesList = invoicesMapper.search(invoicesDto);
        return Result.success(invoicesList);
    }

}




