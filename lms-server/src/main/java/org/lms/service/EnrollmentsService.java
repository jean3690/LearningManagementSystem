package org.lms.service;


import org.lms.entity.Enrollments;
import org.lms.response.Result;

import java.util.List;

/**
* @author jeang
* @description 针对表【enrollments(课程报名表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface EnrollmentsService {

    Result page(Integer pageNum, Integer pageSize);

    Result search(Enrollments enrollments);

    Result update(Enrollments enrollments);

    Result delete(List<Long> ids);
}
