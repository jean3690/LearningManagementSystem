package org.lms.mapper;


import org.apache.ibatis.annotations.Param;
import org.lms.entity.Enrollments;

import java.util.List;

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

    List<Enrollments> list(@Param("ids") List<Long> list);

    List<Enrollments> findAll(Enrollments enrollments);

    List<Enrollments> search(Enrollments enrollments);

    int deleteBatch(List<Long> ids);
}
