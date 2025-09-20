package org.lms.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lms.Context.UserContextHolder;
import org.lms.dto.UsersDto;
import org.lms.dto.UsersUpdateDto;
import org.lms.entity.Users;
import org.lms.mapper.UsersMapper;
import org.lms.response.Result;
import org.lms.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminUserController")
@Slf4j
@RequestMapping("/user")
@Tag(name = "用户管理接口")
public class UserController {
    private UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public Result login(@RequestBody UsersDto usersDto){
        return usersService.login(usersDto);
    }
    @PostMapping("/add")
    public Result add(@RequestBody UsersDto usersDto){
        return usersService.addUser(usersDto);
    }
    @PutMapping("/update")
    public Result update(@RequestBody UsersUpdateDto updateDto){
        return usersService.updateUser(updateDto);
    }
    @GetMapping("/logout")
    public Result logout(@RequestParam String token){
        return usersService.logout(token);
    }
    @GetMapping("/page/{pageNum}/{pageSize}")
    public Result page(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize){
        return usersService.pageQuery(pageNum,pageSize);
    }
    @GetMapping("/{id}")
    public Result queryById(@PathVariable("id") Long id){
        return usersService.queryById(id);
    }
    @GetMapping("/list")
    public Result list(@RequestParam("ids")List<Long> ids){
        return usersService.list(ids);
    }
    @DeleteMapping("/delete")
    public Result delete(@RequestBody List<Long> ids){
        return usersService.remove(ids);
    }
}
