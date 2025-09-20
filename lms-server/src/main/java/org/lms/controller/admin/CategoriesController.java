package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminCategoriesController")
@RequestMapping("/categories")
@Slf4j
@Tag(name = "分类管理接口")
public class CategoriesController {
}
