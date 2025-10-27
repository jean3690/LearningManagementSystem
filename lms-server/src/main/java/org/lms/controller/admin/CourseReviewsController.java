package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.lms.entity.CourseReviews;
import org.lms.response.Result;
import org.lms.service.CourseReviewsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminCourseReviewsController")
@RequestMapping("/admin/courseReviews")
@Tag(name = "课程评价管理")
public class CourseReviewsController {
    private final CourseReviewsService courseReviewsService;

    public CourseReviewsController(CourseReviewsService courseReviewsService) {
        this.courseReviewsService = courseReviewsService;
    }
    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize,
                       @RequestBody CourseReviews courseReviews){
        return courseReviewsService.page(pageNum,pageSize,courseReviews);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除课程评价")
    public Result delete(@RequestBody List<Long> ids){
        return courseReviewsService.delete(ids);
    }
}
