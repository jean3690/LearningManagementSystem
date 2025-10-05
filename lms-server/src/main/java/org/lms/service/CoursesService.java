package org.lms.service;


import org.lms.dto.CoursesDto;
import org.lms.response.Result;

import java.util.List;

/**
* @author jeang
* @description 针对表【courses(课程基本信息表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface CoursesService  {

    Result page(Integer pageNum, Integer pageSize);

    Result queryById(Long id);

    Result add(CoursesDto coursesDto);

    Result search(CoursesDto coursesDto);

    Result update(CoursesDto coursesDto);

    Result delete(List<Long> ids);
}
