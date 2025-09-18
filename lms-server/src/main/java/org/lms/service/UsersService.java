package org.lms.service;


import org.lms.dto.UsersDto;
import org.lms.response.Result;

/**
* @author jeang
* @description 针对表【users(用户基本信息表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface UsersService  {

    Result login(UsersDto usersDto);
}
