package org.lms.mapper;


import org.apache.ibatis.annotations.Param;
import org.lms.Enum.OpreationType;
import org.lms.annotation.AutoFill;
import org.lms.dto.CategoriesDto;
import org.lms.entity.Categories;

import java.util.ArrayList;
import java.util.List;

/**
* @author jeang
* @description 针对表【categories(课程分类表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:44
* @Entity .entity.Categories
*/
public interface CategoriesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Categories record);
    @AutoFill(value = {OpreationType.CREATEDAT})
    int insertSelective(Categories record);

    Categories selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Categories record);

    int updateByPrimaryKey(Categories record);

    ArrayList<Categories> list(List<Long> ids);

    List<Categories> selectByPage(CategoriesDto categoriesDto);

    void deleteBatch(@Param("ids") List<Long> ids);
}
