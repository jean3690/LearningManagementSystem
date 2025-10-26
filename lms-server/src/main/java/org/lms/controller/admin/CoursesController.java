package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.lms.dto.CoursesDto;
import org.lms.response.Result;
import org.lms.service.CoursesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminCoursesController")
@RequestMapping("/admin/courses")
@Tag(name = "课程基本信息管理")
public class CoursesController {
    private CoursesService coursesService;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }
    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize,
                       @RequestBody CoursesDto coursesDto){
        return coursesService.page(pageNum,pageSize,coursesDto);
    }
    @GetMapping("/query/{id}")
    @Operation(summary = "根据id查询课程信息")
    public Result queyrById(@PathVariable("id") Long id){
        return coursesService.queryById(id);
    }
    @PostMapping("/add")
    @Operation(summary = "添加课程信息")
    public Result add(@RequestBody CoursesDto coursesDto){
        return coursesService.add(coursesDto);
    }
    @PutMapping("/update")
    @Operation(summary = "修改课程信息")
    public Result update(@RequestBody CoursesDto coursesDto){
        return coursesService.update(coursesDto);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除课程信息")
    public Result delete(@RequestBody List<Long> ids){
        return coursesService.delete(ids);
    }
}
