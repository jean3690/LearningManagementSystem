package org.lms.mapper;


import org.lms.entity.Users;

/**
* @author jeang
* @description 针对表【users(用户基本信息表)】的数据库操作Mapper
* @createDate 2025-09-13 23:04:45
* @Entity .entity.Users
*/
public interface UsersMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Users findByUsername(String username);
}
