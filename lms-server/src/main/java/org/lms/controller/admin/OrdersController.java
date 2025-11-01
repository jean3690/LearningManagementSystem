package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.dto.OrdersDto;
import org.lms.response.Result;
import org.lms.service.OrdersService;
import org.lms.vo.OrdersDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("adminOrdersController")
@RequestMapping("/admin/orders")
@Slf4j
@Tag(name = "订单管理")
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }
    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize,
                       @ModelAttribute OrdersDto ordersDto){
        return ordersService.page(pageNum,pageSize,ordersDto);
    }
    @PostMapping("/add")
    @Operation(summary = "添加")
    public Result add(@RequestBody OrdersDetails ordersDetails){
        return ordersService.add(ordersDetails);
    }
    @PutMapping("/update")
    @Operation(summary = "修改")
    public Result update(@RequestBody OrdersDetails ordersDetails){
        return ordersService.update(ordersDetails);
    }
    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除")
    public Result delete(@PathVariable("ids")String ids){
        List<Long> idList = new ArrayList<>();
        for (String id : ids.split(",")) {
            idList.add(Long.parseLong(id));
        }
        return ordersService.delete(idList);
    }
}
