package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.entity.LessonProgress;
import org.lms.response.Result;
import org.lms.service.LessonProgressService;
import org.springframework.web.bind.annotation.*;

@RestController("adminLessonProgressController")
@RequestMapping("/admin/lessonProgress")
@Slf4j
@Tag(name = "课时学习进度管理")
public class LessonProgressController {
    private LessonProgressService lessonProgressService;

    public LessonProgressController(LessonProgressService lessonProgressService) {
        this.lessonProgressService = lessonProgressService;
    }
    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize){
        return lessonProgressService.page(pageNum,pageSize);
    }
    @GetMapping("/search")
    @Operation(summary = "搜索")
    public Result search(@RequestBody LessonProgress lessonProgress){
        return lessonProgressService.search(lessonProgress);
    }
}
