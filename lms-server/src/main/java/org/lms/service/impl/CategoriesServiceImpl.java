package org.lms.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.dto.CategoriesDto;
import org.lms.entity.Categories;
import org.lms.entity.Courses;
import org.lms.mapper.CategoriesMapper;
import org.lms.mapper.CoursesMapper;
import org.lms.response.Result;
import org.lms.service.CategoriesService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

/**
* @author jeang
* @description 针对表【categories(课程分类表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class CategoriesServiceImpl implements CategoriesService {
    private CategoriesMapper categoriesMapper;
    private RedisTemplate<String,Object> redisTemplate;
    private TransactionTemplate transactionTemplate;
    private CoursesMapper coursesMapper;

    public CategoriesServiceImpl(CategoriesMapper categoriesMapper,
                                 RedisTemplate<String, Object> redisTemplate,
                                 TransactionTemplate transactionTemplate,
                                 CoursesMapper coursesMapper) {
        this.categoriesMapper = categoriesMapper;
        this.redisTemplate = redisTemplate;
        this.transactionTemplate = transactionTemplate;
        this.coursesMapper = coursesMapper;
    }

    @Override
    public Result queryById(Long id) {
        return Result.success(categoriesMapper.selectByPrimaryKey(id));
    }

    @Override
    public Result add(CategoriesDto categoriesDto) {
        Categories categories = new Categories();
        BeanUtils.copyProperties(categoriesDto,categories);
        categoriesMapper.insertSelective(categories);
        return Result.success();
    }

    @Override
    public void update(CategoriesDto categoriesDto) {
        Categories categories = new Categories();
        BeanUtils.copyProperties(categoriesDto,categories);
        transactionTemplate.execute(status->{
            try {
                categoriesMapper.updateByPrimaryKeySelective(categories);
            } catch (Exception e) {
                status.setRollbackOnly();
            }
            return null;
        });
    }

    @Override
    public Result delete(Long id) {
        Courses courses = coursesMapper.findByCategoryId(id);
        if(courses!=null){
            return Result.error("有课程有这个分类");
        }
        transactionTemplate.execute(status -> {
            try {
                categoriesMapper.deleteByPrimaryKey(id);
            } catch (Exception e) {
                status.setRollbackOnly();
            }
            return null;
        });
        return Result.success();
    }

    @Override
    public ArrayList<Categories> list(List<Long> ids) {
        return categoriesMapper.list(ids);
    }

    @Override
    public Result pageQuery(Integer pageNum, Integer pageSize) {
        if(pageNum==null||pageSize==null){
            return Result.error("不能为null");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Categories> categoriesList = categoriesMapper.selectByPage();
        return Result.success(new PageInfo<Categories>(categoriesList));
    }
}




