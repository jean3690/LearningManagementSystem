package org.lms.mapper;


import org.apache.ibatis.annotations.Param;
import org.lms.entity.Lessons;

import java.util.List;

/**
* @author jeang
* @description 针对表【lessons(课程课时内容表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.Lessons
*/
public interface LessonsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Lessons record);

    int insertSelective(Lessons record);

    Lessons selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Lessons record);

    int updateByPrimaryKey(Lessons record);

    List<Lessons> list(@Param("ids") List<Long> ids);
}
