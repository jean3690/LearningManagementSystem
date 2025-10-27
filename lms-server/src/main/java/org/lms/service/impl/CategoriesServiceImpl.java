package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.dto.CategoriesDto;
import org.lms.entity.Categories;
import org.lms.entity.Courses;
import org.lms.mapper.CategoriesMapper;
import org.lms.mapper.CoursesMapper;
import org.lms.response.Result;
import org.lms.service.CategoriesService;
import org.lms.utils.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.lms.constant.RedisConstant.*;

/**
* @author jeang
* @description 针对表【categories(课程分类表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesMapper categoriesMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final TransactionTemplate transactionTemplate;
    private final CoursesMapper coursesMapper;

    public CategoriesServiceImpl(CategoriesMapper categoriesMapper,
                                 StringRedisTemplate stringRedisTemplate,
                                 TransactionTemplate transactionTemplate,
                                 CoursesMapper coursesMapper) {
        this.categoriesMapper = categoriesMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.transactionTemplate = transactionTemplate;
        this.coursesMapper = coursesMapper;
    }

    @Override
    public Result queryById(Long id) {
        String str = (String) stringRedisTemplate.opsForHash().get(CATEGORY_HASH_TOKEN,id);
        if(StringUtils.hasText(str)){
            Categories categories = JsonUtil.jsonToObj(str, Categories.class);
            return Result.success(categories);
        }
        Categories categories = categoriesMapper.selectByPrimaryKey(id);
        stringRedisTemplate.opsForHash().put(CATEGORY_HASH_TOKEN,id,JsonUtil.toJson(categories));
        return Result.success(categories);
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
        stringRedisTemplate.opsForHash().delete(CATEGORY_HASH_TOKEN,categories.getId());
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
        stringRedisTemplate.opsForHash().delete(CATEGORY_HASH_TOKEN,id);
        return Result.success();
    }

    @Override
    public ArrayList<Categories> list(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        List<String> strIds = ids.stream().map(String::valueOf).toList();
        List<Object> cacheResults = stringRedisTemplate.opsForHash().multiGet(CATEGORY_HASH_TOKEN, Collections.singleton(strIds));
        ArrayList<Categories> categoriesList = new ArrayList<>();
        List<Long> idsToQuery = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            String json = (String) cacheResults.get(i);
            if(json==null){
                idsToQuery.add(id);
            }else {
                Categories categories = JsonUtil.jsonToObj(json, Categories.class);
                categoriesList.add(categories);
            }
        }
        if(!idsToQuery.isEmpty()){
            ArrayList<Categories> list = categoriesMapper.list(idsToQuery);
            categoriesList.addAll(list);
            HashMap<String, String> map = new HashMap<>();
            for (Categories categories : list) {
                map.put(String.valueOf(categories.getId()),JsonUtil.toJson(categories));
            }
            stringRedisTemplate.opsForHash().putAll(CATEGORY_HASH_TOKEN,map);
            stringRedisTemplate.expire(CATEGORY_HASH_TOKEN, ThreadLocalRandom.current().nextInt(10,30), TimeUnit.MINUTES);
        }
        return categoriesList;
    }

    @Override
    public Result pageQuery(Integer pageNum, Integer pageSize, CategoriesDto categoriesDto) {
        if(pageNum==null||pageSize==null){
            return Result.error("不能为null");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Categories> categoriesList = categoriesMapper.selectByPage(categoriesDto);
        return Result.success(new PageInfo<>(categoriesList));
    }
}




