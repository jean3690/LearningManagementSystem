package org.lms.service;


import org.lms.entity.Lessons;
import org.lms.response.Result;

import java.util.List;

/**
* @author jeang
* @description 针对表【lessons(课程课时内容表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface LessonsService {

    Result page(Integer pageNum, Integer pageSize);

    Result search(Lessons lessons);

    Result queryById(Long id);

    Result add(Lessons lessons);

    Result update(Lessons lessons);

    Result delete(List<Long> ids);
}
