package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.dto.InvoicesDto;
import org.lms.entity.Invoices;
import org.lms.entity.Orders;
import org.lms.mapper.InvoicesMapper;
import org.lms.mapper.OrdersMapper;
import org.lms.response.Result;
import org.lms.service.InvoicesService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author jeang
* @description 针对表【invoices(发票表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class InvoicesServiceImpl implements InvoicesService {
    private InvoicesMapper invoicesMapper;
    private OrdersMapper ordersMapper;

    @Override
    public Result page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Invoices> invoicesList = invoicesMapper.findAll();
        return Result.success(new PageInfo<>(invoicesList));
    }

    @Override
    public Result queryById(Long id) {
        Invoices invoices = invoicesMapper.selectByPrimaryKey(id);
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
        return Result.success();
    }

    @Override
    public Result search(InvoicesDto invoicesDto) {
        List<Invoices> invoicesList = invoicesMapper.search(invoicesDto);
        return Result.success(invoicesList);
    }

}




