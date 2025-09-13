package org.lms.mapper;

import org.lms.entity.CourseModules;

/**
* @author jeang
* @description 针对表【course_modules(课程模块/章节表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.CourseModules
*/
public interface CourseModulesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CourseModules record);

    int insertSelective(CourseModules record);

    CourseModules selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CourseModules record);

    int updateByPrimaryKey(CourseModules record);

}
