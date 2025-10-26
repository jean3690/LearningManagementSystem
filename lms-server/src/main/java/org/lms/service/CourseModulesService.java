package org.lms.service;


import org.lms.dto.CourseModulesDto;
import org.lms.response.Result;

import java.util.List;

/**
* @author jeang
* @description 针对表【course_modules(课程模块/章节表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface CourseModulesService {

    Result page(Integer pageNum, Integer pageSize, CourseModulesDto courseModulesDto);

    Result search(CourseModulesDto courseModulesDto);

    Result add(CourseModulesDto courseModulesDto);

    Result delete(List<Long> ids);
}
