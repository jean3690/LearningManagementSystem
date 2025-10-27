package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.dto.CategoriesDto;
import org.lms.entity.Categories;
import org.lms.response.Result;
import org.lms.service.CategoriesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminCategoriesController")
@RequestMapping("/admin/categories")
@Slf4j
@Tag(name = "分类管理接口")
public class CategoriesController {
    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    /**
     *
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询")
    public Result queryById(@PathVariable("id") Long id){
        return categoriesService.queryById(id);
    }

    /**
     *
     */
    @PostMapping("/add")
    @Operation(summary = "添加分类")
    public Result add(@RequestBody CategoriesDto categoriesDto){
        return categoriesService.add(categoriesDto);
    }

    /**
     *
     */
    @PutMapping("/update")
    @Operation(summary = "修改分类")
    public Result update(@RequestBody CategoriesDto categoriesDto){
        categoriesService.update(categoriesDto);
        return Result.success();
    }

    /**
     *
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除分类")
    public Result delete(@PathVariable("id") Long id){
        return categoriesService.delete(id);
    }

    /**
     *
     */
    @GetMapping("/list")
    @Operation(summary = "根据list里的id获得分类")
    public Result list(@RequestParam("ids") List<Long> ids){
        List<Categories> categoriesList = categoriesService.list(ids);
        return Result.success(categoriesList);
    }

    /**
     *
     */
    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result pageQuery(@PathVariable("pageNum") Integer pageNum,
                            @PathVariable("pageSize") Integer pageSize,
                            @RequestBody CategoriesDto categoriesDto){
        return categoriesService.pageQuery(pageNum,pageSize,categoriesDto);
    }

}
