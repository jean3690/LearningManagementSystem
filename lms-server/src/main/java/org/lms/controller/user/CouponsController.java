package org.lms.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.lms.dto.CouponsDto;
import org.lms.response.Result;
import org.lms.service.CouponsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userCouponsController")
@RequestMapping("/user/coupons")
@Tag(name = "优惠卷管理")
public class CouponsController {
    private CouponsService couponsService;

    public CouponsController(CouponsService couponsService) {
        this.couponsService = couponsService;
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize){
        return couponsService.page(pageNum,pageSize);
    }
    @GetMapping("/query/{id}")
    @Operation(summary = "根据id查询优惠卷")
    public Result queryById(@PathVariable("id") Long id){
        return couponsService.queryById(id);
    }
    @GetMapping("/search")
    @Operation(summary = "查询优惠卷")
    public Result serach(@RequestBody CouponsDto couponsDto){
        return couponsService.search(couponsDto);
    }
    @PostMapping("/add")
    @Operation(summary = "添加优惠卷")
    public Result add(@RequestBody CouponsDto couponsDto){
        return couponsService.add(couponsDto);
    }
    @PutMapping("/update")
    @Operation(summary = "修改优惠卷")
    public Result update(@RequestBody CouponsDto couponsDto){
        return couponsService.update(couponsDto);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除优惠卷")
    public Result delete(@RequestBody List<Long> ids){
        return couponsService.delete(ids);
    }
}
