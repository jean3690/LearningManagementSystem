package org.lms.service;


import org.lms.dto.CategoriesDto;
import org.lms.entity.Categories;
import org.lms.response.Result;

import java.util.ArrayList;
import java.util.List;

/**
* @author jeang
* @description 针对表【categories(课程分类表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface CategoriesService  {

    Result queryById(Long id);

    Result add(CategoriesDto categoriesDto);

    void update(CategoriesDto categoriesDto);

    Result delete(List<Long> ids);

    ArrayList<Categories> list(List<Long> ids);

    Result pageQuery(Integer pageNum, Integer pageSize, CategoriesDto categoriesDto);
}
