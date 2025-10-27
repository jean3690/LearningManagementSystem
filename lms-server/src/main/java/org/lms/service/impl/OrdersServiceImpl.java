package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.lms.constant.RedisConstant;
import org.lms.dto.OrdersDto;
import org.lms.entity.Courses;
import org.lms.entity.OrderItems;
import org.lms.entity.Orders;
import org.lms.event.CommonRedisCacheClearEvent;
import org.lms.exception.deleteFailException;
import org.lms.mapper.CoursesMapper;
import org.lms.mapper.OrderItemsMapper;
import org.lms.mapper.OrdersMapper;
import org.lms.response.Result;
import org.lms.service.OrdersService;
import org.lms.utils.JsonUtil;
import org.lms.vo.OrdersDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author jeang
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class OrdersServiceImpl implements OrdersService {
    private final OrdersMapper ordersMapper;
    private final OrderItemsMapper orderItemsMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final CoursesMapper coursesMapper;
    private final TransactionTemplate transactionTemplate;

    public OrdersServiceImpl(OrdersMapper ordersMapper,
                             OrderItemsMapper orderItemsMapper,
                             StringRedisTemplate stringRedisTemplate, ApplicationEventPublisher eventPublisher,
                             CoursesMapper coursesMapper,
                             TransactionTemplate transactionTemplate) {
        this.ordersMapper = ordersMapper;
        this.orderItemsMapper = orderItemsMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.eventPublisher = eventPublisher;
        this.coursesMapper = coursesMapper;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, OrdersDto ordersDto) {
        String key;
        if(ObjectUtils.isEmpty(ordersDto)){
            key = RedisConstant.ORDERS_DETAIL_PAGE + pageNum+":"+pageSize;
        }else {
            String hash = DigestUtils.md5DigestAsHex(JsonUtil.toJson(ordersDto).getBytes());
            key = RedisConstant.ORDERS_DETAIL_PAGE + pageNum+":"+pageSize+":"+hash;
        }
        String json = stringRedisTemplate.opsForValue().get(key);
        if(json!=null){
            if(json.equals("_NULL_")){
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            List<OrdersDetails> ordersDetailsList = JsonUtil.jsonToObj(json, List.class,OrdersDetails.class);
            if (Optional.ofNullable(ordersDetailsList).isEmpty()) {
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            return Result.success(new PageInfo<>(ordersDetailsList));
        }
        PageHelper.startPage(pageNum,pageSize);
        List<OrdersDetails> ordersDetailsList = ordersMapper.details(ordersDto);
        if (ObjectUtils.isEmpty(ordersDetailsList)) {
            stringRedisTemplate.opsForValue().set(key,"_NULL_", ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
            return Result.success(Collections.emptyList());
        }
        stringRedisTemplate.opsForValue().set(key,JsonUtil.toJson(ordersDetailsList), ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
        return Result.success(ordersDetailsList);
    }

    @Override
    @Transactional
    public Result add(OrdersDetails ordersDetails) {
        if(ObjectUtils.isEmpty(ordersDetails)){
            return Result.success();
        }
        if(ObjectUtils.isEmpty(ordersDetails.getOrderItemsList())){
            return Result.success();
        }
        List<Long> courseIds = new ArrayList<>();
        List<OrderItems> orderItemsList = ordersDetails.getOrderItemsList();
        for (OrderItems orderItems : orderItemsList) {
            if(ObjectUtils.isEmpty(orderItems.getFinalPrice())){
                throw new RuntimeException("价格为空");
            }
            if (ObjectUtils.isEmpty(orderItems.getUnitPrice())){
                throw new RuntimeException("单价为空");
            }
            if(!ObjectUtils.isEmpty(orderItems.getCourseId())){
                Long courseId = orderItems.getCourseId();
                courseIds.add(courseId);
            }
        }
        List<Courses> coursesList = coursesMapper.list(courseIds);
        Map<Long, Courses> map = coursesList.stream().collect(Collectors.toMap(Courses::getId, Function.identity()));
        List<Courses> list = courseIds.stream().map(map::get).filter(Objects::nonNull).toList();
        if(list.size()!=orderItemsList.size()){
            throw new RuntimeException("有不存在的courseId");
        }
        transactionTemplate.execute(status -> {
            try {
                List<List<OrderItems>> partition = Lists.partition(orderItemsList, 1000);
                for (List<OrderItems> orderItems : partition) {
                    orderItems.forEach(value-> value.setCreatedAt(Instant.now()));
                    orderItemsMapper.insertBatch(orderItems);
                }
                Orders orders = new Orders();
                BeanUtils.copyProperties(ordersDetails,orders);
                ordersMapper.insertSelective(orders);
                return 1;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("添加失败");
            }
        });
        clearPageCache();
        return Result.success();
    }

    @Override
    public Result update(OrdersDetails ordersDetails) {
        if (ObjectUtils.isEmpty(ordersDetails)) {
            return Result.success();
        }
        transactionTemplate.execute(status -> {
            try {
                if(!ObjectUtils.isEmpty(ordersDetails)){
                    List<Long> courseIds = new ArrayList<>();
                    List<OrderItems> orderItemsList = ordersDetails.getOrderItemsList();
                    for (OrderItems orderItems : orderItemsList) {
                        if(!ObjectUtils.isEmpty(orderItems.getCourseId())){
                            Long courseId = orderItems.getCourseId();
                            courseIds.add(courseId);
                        }
                    }
                    List<Courses> coursesList = coursesMapper.list(courseIds);
                    Map<Long, Courses> map = coursesList.stream().collect(Collectors.toMap(Courses::getId, Function.identity()));
                    List<Courses> list = courseIds.stream().map(map::get).filter(Objects::nonNull).toList();
                    if(list.size()!=orderItemsList.size()){
                        throw new RuntimeException("有不存在的courseId");
                    }
                    List<Long> itemIdList = orderItemsList.stream().map(OrderItems::getId).filter(Objects::nonNull).toList();
                    if(itemIdList.size()!=orderItemsList.size()){
                        throw new RuntimeException("有id为null");
                    }
                    orderItemsMapper.deleteBatch(itemIdList);
                    orderItemsMapper.insertBatch(orderItemsList);
                }
                Orders orders = new Orders();
                BeanUtils.copyProperties(ordersDetails,orders);
                ordersMapper.updateByPrimaryKeySelective(orders);
                return 1;
            }catch (Exception e){
                status.setRollbackOnly();
                throw new RuntimeException(e.getCause());
            }
        });
        clearPageCache();
        return Result.success();
    }

    @Override
    public Result delete(List<Long> idList) {
        transactionTemplate.execute(status -> {
          try {
              orderItemsMapper.deleteByOrderIdBatch(idList);
              ordersMapper.deleteBatch(idList);
              return 1;
          }catch (Exception e){
              throw new deleteFailException("删除失败");
          }
        });
        clearPageCache();
        return Result.success();
    }
    private void clearPageCache() {
        String prefix = RedisConstant.ORDERS_DETAIL_PAGE;
        eventPublisher.publishEvent(new CommonRedisCacheClearEvent(prefix));
    }
}




