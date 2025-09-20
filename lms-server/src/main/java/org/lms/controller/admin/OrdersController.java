package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminOrdersController")
@RequestMapping("/orders")
@Slf4j
@Tag(name = "订单管理")
public class OrdersController {
}
