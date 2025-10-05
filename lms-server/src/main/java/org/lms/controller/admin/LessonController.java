package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.service.LessonsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminLessonController")
@RequestMapping("/admin/lesson")
@Slf4j
@Tag(name = "课程课时内容管理")
public class LessonController {
    private LessonsService lessonsService;
}
