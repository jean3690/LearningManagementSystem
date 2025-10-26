package org.lms.mapper;


import org.apache.ibatis.annotations.Param;
import org.lms.entity.LessonProgress;

import java.util.List;

/**
* @author jeang
* @description 针对表【lesson_progress(课时学习进度表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.LessonProgress
*/
public interface LessonProgressMapper {

    int deleteByPrimaryKey(Long id);

    int insert(LessonProgress record);

    int insertSelective(LessonProgress record);

    LessonProgress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LessonProgress record);

    int updateByPrimaryKey(LessonProgress record);

    List<LessonProgress> findByStaus(@Param("ids") List<Long> ids, @Param("lessonProgressStatus") String lessonProgressStatus);

    int deleteBatch(@Param("ids") List<Long> ids);

    List<LessonProgress> findAll(LessonProgress lessonProgress);

    List<LessonProgress> search(LessonProgress lessonProgress);
}
