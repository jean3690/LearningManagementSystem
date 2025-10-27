package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.Enum.LessonProgressStatus;
import org.lms.entity.*;
import org.lms.mapper.*;
import org.lms.response.Result;
import org.lms.service.EnrollmentsService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

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

    public EnrollmentsServiceImpl(EnrollmentsMapper enrollmentsMapper,
                                  UsersMapper usersMapper,
                                  CoursesMapper coursesMapper,
                                  OrderItemsMapper orderItemsMapper,
                                  LessonProgressMapper lessonProgressMapper,
                                  TransactionTemplate transactionTemplate,
                                  StringRedisTemplate stringRedisTemplate) {
        this.enrollmentsMapper = enrollmentsMapper;
        this.usersMapper = usersMapper;
        this.coursesMapper = coursesMapper;
        this.orderItemsMapper = orderItemsMapper;
        this.lessonProgressMapper = lessonProgressMapper;
        this.transactionTemplate = transactionTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Result page(Integer pageNum, Integer pageSize, Enrollments enrollments) {
        PageHelper.startPage(pageNum,pageSize);
        List<Enrollments> enrollmentsList = enrollmentsMapper.findAll(enrollments);
        return Result.success(new PageInfo<>(enrollmentsList));
    }

    @Override
    public Result search(Enrollments enrollments) {
        List<Enrollments> enrollmentsList = enrollmentsMapper.search(enrollments);
        return Result.success(enrollmentsList);
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
        return Result.success();
    }

    @Override
    public Result delete(List<Long> ids) {
        List<LessonProgress> lessonProgressList = lessonProgressMapper.findByStaus(ids, LessonProgressStatus.NOT_STARTED.toString());
        if(lessonProgressList!=null&&!lessonProgressList.isEmpty()){
            return Result.error();
        }
        enrollmentsMapper.deleteBatch(ids);
        return Result.success();
    }
}




