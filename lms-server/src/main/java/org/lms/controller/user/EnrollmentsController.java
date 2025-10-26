package org.lms.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.entity.Enrollments;
import org.lms.response.Result;
import org.lms.service.EnrollmentsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userEnrollmentController")
@RequestMapping("/user/enroll")
@Slf4j
@Tag(name = "课程报名管理")
public class EnrollmentsController {
    private EnrollmentsService enrollmentsService;

    public EnrollmentsController(EnrollmentsService enrollmentsService) {
        this.enrollmentsService = enrollmentsService;
    }
    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize){
        return enrollmentsService.page(pageNum,pageSize, enrollments);
    }
    @GetMapping("/search")
    @Operation(summary = "搜索")
    public Result search(@RequestBody Enrollments enrollments){
        return enrollmentsService.search(enrollments);
    }
    @PutMapping("/update")
    @Operation(summary = "修改课程报名")
    public Result update(@RequestBody Enrollments enrollments){
        return enrollmentsService.update(enrollments);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除课程报名")
    public Result delete(@RequestBody List<Long> ids){
        return enrollmentsService.delete(ids);
    }
}
