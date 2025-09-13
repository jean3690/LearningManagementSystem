package org.lms.mapper;


import org.lms.entity.Enrollments;

/**
* @author jeang
* @description 针对表【enrollments(课程报名表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.Enrollments
*/
public interface EnrollmentsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Enrollments record);

    int insertSelective(Enrollments record);

    Enrollments selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Enrollments record);

    int updateByPrimaryKey(Enrollments record);

}
