package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.Enum.LessonProgressStatus;
import org.lms.constant.RedisConstant;
import org.lms.entity.*;
import org.lms.event.CommonRedisCacheClearEvent;
import org.lms.mapper.*;
import org.lms.response.Result;
import org.lms.service.EnrollmentsService;
import org.lms.utils.JsonUtil;
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
* @description 针对表【enrollments(课程报名表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class EnrollmentsServiceImpl implements EnrollmentsService {
    private final EnrollmentsMapper enrollmentsMapper;
    private final UsersMapper usersMapper;
    private final CoursesMapper coursesMapper;
    private final OrderItemsMapper orderItemsMapper;
    private final LessonProgressMapper lessonProgressMapper;
    private final TransactionTemplate transactionTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public EnrollmentsServiceImpl(EnrollmentsMapper enrollmentsMapper,
                                  UsersMapper usersMapper,
                                  CoursesMapper coursesMapper,
                                  OrderItemsMapper orderItemsMapper,
                                  LessonProgressMapper lessonProgressMapper,
                                  TransactionTemplate transactionTemplate,
                                  StringRedisTemplate stringRedisTemplate, ApplicationEventPublisher eventPublisher) {
        this.enrollmentsMapper = enrollmentsMapper;
        this.usersMapper = usersMapper;
        this.coursesMapper = coursesMapper;
        this.orderItemsMapper = orderItemsMapper;
        this.lessonProgressMapper = lessonProgressMapper;
        this.transactionTemplate = transactionTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, Enrollments enrollments) {
        String key;
        if(ObjectUtils.isEmpty(enrollments)){
            key = RedisConstant.ENROLLMENTS_PAGE + pageNum+":"+pageSize;
        }else {
            String hash = DigestUtils.md5DigestAsHex(JsonUtil.toJson(enrollments).getBytes());
            key = RedisConstant.ENROLLMENTS_PAGE + pageNum+":"+pageSize+":"+hash;
        }
        String json = stringRedisTemplate.opsForValue().get(key);
        if(json!=null){
            if(json.equals("_NULL_")){
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            List<Enrollments> enrollmentsList = JsonUtil.jsonToObj(json,List.class,Enrollments.class);
            if (Optional.ofNullable(enrollmentsList).isEmpty()) {
                return Result.success(new PageInfo<>(Collections.emptyList()));
            }
            return Result.success(new PageInfo<>(enrollmentsList));
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Enrollments> enrollmentsList = enrollmentsMapper.findAll(enrollments);
        if (ObjectUtils.isEmpty(enrollmentsList)) {
            stringRedisTemplate.opsForValue().set(key,"_NULL_", ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
            return Result.success(Collections.emptyList());
        }
        stringRedisTemplate.opsForValue().set(key,JsonUtil.toJson(enrollmentsList), ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
        return Result.success(new PageInfo<>(enrollmentsList));
    }

    @Override
    public Result update(Enrollments enrollments) {
        transactionTemplate.execute(status -> {
            try {
                Long courseId = enrollments.getCourseId();
                Long userId = enrollments.getUserId();
                Long orderItemId = enrollments.getOrderItemId();
                Courses courses = coursesMapper.selectByPrimaryKey(courseId);
                if(courses==null){
                    return Result.error();
                }
                Users users = usersMapper.selectByPrimaryKey(userId);
                if(users==null){
                    return Result.error();
                }
                OrderItems orderItems = orderItemsMapper.selectByPrimaryKey(orderItemId);
                if(orderItems==null){
                    return Result.error();
                }
                return enrollmentsMapper.updateByPrimaryKeySelective(enrollments);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        clearPageCache();
        return Result.success();
    }

    @Override
    public Result delete(List<Long> ids) {
        List<LessonProgress> lessonProgressList = lessonProgressMapper.findByStaus(ids, LessonProgressStatus.NOT_STARTED.toString());
        if(lessonProgressList!=null&&!lessonProgressList.isEmpty()){
            return Result.error();
        }
        enrollmentsMapper.deleteBatch(ids);
        clearPageCache();
        return Result.success();
    }

    @Override
    public Result add(Enrollments enrollments) {
        Long courseId = enrollments.getCourseId();
        Long userId = enrollments.getUserId();
        Long orderItemId = enrollments.getOrderItemId();
        Courses courses = coursesMapper.selectByPrimaryKey(courseId);
        if(courses==null){
            return Result.error();
        }
        Users users = usersMapper.selectByPrimaryKey(userId);
        if(users==null){
            return Result.error();
        }
        OrderItems orderItems = orderItemsMapper.selectByPrimaryKey(orderItemId);
        if(orderItems==null){
            return Result.error();
        }
        enrollmentsMapper.insertSelective(enrollments);
        clearPageCache();
        return Result.success();
    }

    private void clearPageCache() {
        String prefix = RedisConstant.ENROLLMENTS_PAGE;
        eventPublisher.publishEvent(new CommonRedisCacheClearEvent(prefix));
    }
}




