package org.lms.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.lms.dto.UsersDto;
import org.lms.dto.UsersUpdateDto;
import org.lms.response.Result;
import org.lms.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userUserController")
@Slf4j
@RequestMapping("/user/user")
@Tag(name = "用户管理接口")
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     *
     * @param usersDto
     * @param httpSession
     * @return
     */
    @GetMapping("/login")
    @Operation(summary = "登录")
    public Result login(@RequestBody UsersDto usersDto, HttpSession httpSession){
        return usersService.login(usersDto,httpSession);
    }

    /**
     *
     * @param usersDto
     * @return
     */
    @PostMapping("/add")
    @Operation(summary = "添加用户")
    public Result add(@RequestBody UsersDto usersDto){
        log.info("{}",usersDto);
        return usersService.addUser(usersDto);
    }

    /**
     *
     * @param updateDto
     * @return
     */
    @PutMapping("/update")
    @Operation(summary = "修改用户")
    public Result update(@RequestBody UsersUpdateDto updateDto){
        return usersService.updateUser(updateDto);
    }

    /**
     *
     * @param token
     * @return
     */
    @GetMapping("/logout")
    @Operation(summary = "退出")
    public Result logout(@RequestParam String token){
        return usersService.logout(token);
    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page/{pageNum}/{pageSize}")
    @Operation(summary = "分页查询")
    public Result page(@PathVariable("pageNum") Integer pageNum,
                       @PathVariable("pageSize") Integer pageSize,
                       @RequestBody UsersDto usersDto){
        return usersService.pageQuery(pageNum,pageSize,usersDto);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询用户")
    public Result queryById(@PathVariable("id") Long id){
        return usersService.queryById(id);
    }

    /**
     *
     * @param ids
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "根据list查询用户集合")
    public Result list(@RequestParam("ids")List<Long> ids){
        return usersService.list(ids);
    }

    /**
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除用户")
    public Result delete(@RequestBody List<Long> ids){
        return usersService.remove(ids);
    }
}
