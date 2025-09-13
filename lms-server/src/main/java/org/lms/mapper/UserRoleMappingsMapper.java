package org.lms.mapper;


import org.lms.entity.UserRoleMappings;

/**
* @author jeang
* @description 针对表【user_role_mappings(用户与角色关联表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.UserRoleMappings
*/
public interface UserRoleMappingsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRoleMappings record);

    int insertSelective(UserRoleMappings record);

    UserRoleMappings selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleMappings record);

    int updateByPrimaryKey(UserRoleMappings record);

}
