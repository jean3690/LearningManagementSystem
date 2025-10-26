package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.dto.InvoicesDto;
import org.lms.response.Result;
import org.lms.service.InvoicesService;
import org.springframework.web.bind.annotation.*;

@RestController("adminInvoicesController")
@RequestMapping("/admin/invoices")
@Slf4j
@Tag(name = "发票管理")
public class InvoicesController {
    private InvoicesService invoicesService;

    public InvoicesController(InvoicesService invoicesService) {
        this.invoicesService = invoicesService;
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize,
                       @RequestBody InvoicesDto invoicesDto){
        return invoicesService.page(pageNum,pageSize,invoicesDto);
    }
    @GetMapping("/query/{id}")
    @Operation(summary = "根据id查询发票")
    public Result queryById(@PathVariable("id") Long id){
        return invoicesService.queryById(id);
    }
    @PostMapping("/add")
    @Operation(summary = "添加发票")
    public Result add(@RequestBody InvoicesDto invoicesDto){
        return invoicesService.add(invoicesDto);
    }
}
