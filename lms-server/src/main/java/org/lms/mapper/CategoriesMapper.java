package org.lms.mapper;


import org.lms.entity.Categories;

/**
* @author jeang
* @description 针对表【categories(课程分类表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:44
* @Entity .entity.Categories
*/
public interface CategoriesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Categories record);

    int insertSelective(Categories record);

    Categories selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Categories record);

    int updateByPrimaryKey(Categories record);

}
