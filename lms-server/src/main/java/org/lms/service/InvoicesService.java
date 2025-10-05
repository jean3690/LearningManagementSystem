package org.lms.service;


import org.lms.dto.InvoicesDto;
import org.lms.entity.Invoices;
import org.lms.response.Result;

/**
* @author jeang
* @description 针对表【invoices(发票表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface InvoicesService {

    Result page(Integer pageNum, Integer pageSize);

    Result queryById(Long id);

    Result add(InvoicesDto invoicesDto);

    Result search(InvoicesDto invoicesDto);
}
