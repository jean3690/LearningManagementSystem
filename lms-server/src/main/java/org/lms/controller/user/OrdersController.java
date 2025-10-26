package org.lms.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.entity.Orders;
import org.lms.response.Result;
import org.lms.service.OrdersService;
import org.springframework.web.bind.annotation.*;

@RestController("userOrdersController")
@RequestMapping("/user/orders")
@Slf4j
@Tag(name = "订单管理")
public class OrdersController {
    private OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }
    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize){
        return ordersService.page(pageNum,pageSize, ordersDto);
    }
    @GetMapping("/search")
    @Operation(summary = "搜索")
    public Result search(@RequestBody Orders orders){
        return ordersService.search(orders);
    }
}
