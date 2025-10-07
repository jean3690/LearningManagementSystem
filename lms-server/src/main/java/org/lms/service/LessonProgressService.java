package org.lms.service;


import org.lms.entity.LessonProgress;
import org.lms.response.Result;

/**
* @author jeang
* @description 针对表【lesson_progress(课时学习进度表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface LessonProgressService {

    Result page(Integer pageNum, Integer pageSize);

    Result search(LessonProgress lessonProgress);
}
