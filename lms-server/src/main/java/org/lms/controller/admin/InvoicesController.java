package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminInvoicesController")
@RequestMapping("/admin/invoices")
@Slf4j
@Tag(name = "发票管理")
public class InvoicesController {
}
