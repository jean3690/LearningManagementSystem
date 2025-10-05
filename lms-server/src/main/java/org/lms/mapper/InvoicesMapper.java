package org.lms.mapper;


import org.lms.Enum.OpreationType;
import org.lms.annotation.AutoFill;
import org.lms.dto.InvoicesDto;
import org.lms.entity.Invoices;

import java.util.List;

/**
* @author jeang
* @description 针对表【invoices(发票表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.Invoices
*/
public interface InvoicesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Invoices record);
    @AutoFill({OpreationType.CREATEDAT,OpreationType.UPDATEDAT})
    int insertSelective(Invoices record);

    Invoices selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Invoices record);

    int updateByPrimaryKey(Invoices record);

    List<Invoices> findAll();

    List<Invoices> search(InvoicesDto invoicesDto);
}
