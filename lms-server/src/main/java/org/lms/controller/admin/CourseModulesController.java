package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.lms.dto.CourseModulesDto;
import org.lms.response.Result;
import org.lms.service.CourseModulesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminCourseModulesController")
@RequestMapping("/admin/courseModules")
@Tag(name = "课程模块管理")
public class CourseModulesController {
    private final CourseModulesService courseModulesService;

    public CourseModulesController(CourseModulesService courseModulesService) {
        this.courseModulesService = courseModulesService;
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize,
                        @RequestBody CourseModulesDto courseModulesDto){
        return courseModulesService.page(pageNum,pageSize,courseModulesDto);
    }
    @PostMapping("/add")
    @Operation(summary = "添加课程模块")
    public Result add(@RequestBody CourseModulesDto courseModulesDto){
        return courseModulesService.add(courseModulesDto);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除课程模块")
    public Result delete(@RequestBody List<Long> ids){
        return courseModulesService.delete(ids);
    }
}
