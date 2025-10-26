package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.dto.LessonsDto;
import org.lms.entity.Lessons;
import org.lms.response.Result;
import org.lms.service.LessonsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminLessonController")
@RequestMapping("/admin/lesson")
@Slf4j
@Tag(name = "课程课时内容管理")
public class LessonController {
    private LessonsService lessonsService;

    public LessonController(LessonsService lessonsService) {
        this.lessonsService = lessonsService;
    }
    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize,
                       @RequestBody LessonsDto lessonsDto){
        return lessonsService.page(pageNum,pageSize,lessonsDto);
    }
    @GetMapping("/query/{id}")
    @Operation(summary = "根据id查询")
    public Result queryById(@PathVariable("id") Long id){
        return lessonsService.queryById(id);
    }
    @PostMapping("/add")
    @Operation(summary = "添加课程课时内容")
    public Result add(@RequestBody Lessons lessons){
        return lessonsService.add(lessons);
    }
    @PutMapping("/update")
    @Operation(summary = "修改课程课时内容")
    public Result update(@RequestBody Lessons lessons){
        return lessonsService.update(lessons);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除课程课时内容")
    public Result delete(@RequestBody List<Long> ids){
        return lessonsService.delete(ids);
    }
}
