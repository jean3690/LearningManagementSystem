package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminCouponsController")
@RequestMapping("/admin/coupons")
@Tag(name = "优惠卷管理")
public class CouponsController {
}
