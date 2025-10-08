package org.lms.mapper;


import org.lms.entity.UserRoles;
//import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
* @author jeang
* @description 针对表【user_roles(系统角色定义表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.UserRoles
*/
public interface UserRolesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRoles record);

    int insertSelective(UserRoles record);

    UserRoles selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoles record);

    int updateByPrimaryKey(UserRoles record);

    List<UserRoles> findListById(Integer roleId);
}
