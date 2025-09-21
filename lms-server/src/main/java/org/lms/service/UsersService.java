package org.lms.service;


import jakarta.servlet.http.HttpSession;
import org.lms.dto.UsersDto;
import org.lms.dto.UsersUpdateDto;
import org.lms.response.Result;

import java.util.List;

/**
* @author jeang
* @description 针对表【users(用户基本信息表)】的数据库操作Service
* @createDate 2025-09-13 22:53:03
*/
public interface UsersService  {

    Result login(UsersDto usersDto, HttpSession httpSession);

    Result addUser(UsersDto usersDto);

    Result updateUser(UsersUpdateDto updateDto);

    Result logout(String token);

    Result pageQuery(Integer pageNum, Integer pageSize);

    Result queryById(Long id);

    Result list(List<Long> ids);

    Result remove(List<Long> ids);
}
