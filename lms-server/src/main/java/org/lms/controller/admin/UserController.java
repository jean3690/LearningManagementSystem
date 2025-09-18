package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.dto.UsersDto;
import org.lms.entity.Users;
import org.lms.mapper.UsersMapper;
import org.lms.response.Result;
import org.lms.service.UsersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminUserController")
@Slf4j
@RequestMapping("/user")
@Tag(name = "用户管理接口")
public class UserController {
    private UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody UsersDto usersDto){
        return Result.success(usersService.login(usersDto));
    }
}
