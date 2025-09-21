package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminCoursesController")
@RequestMapping("/admin/courses")
@Tag(name = "课程基本信息管理")
public class CoursesController {
}
