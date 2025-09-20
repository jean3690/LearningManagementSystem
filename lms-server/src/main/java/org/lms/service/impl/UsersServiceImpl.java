package org.lms.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lms.Enum.AccountType;
import org.lms.Enum.UserType;
import org.lms.constant.RedisConstant;
import org.lms.dto.UsersDto;
import org.lms.dto.UsersUpdateDto;
import org.lms.entity.Users;
import org.lms.mapper.UsersMapper;
import org.lms.response.Result;
import org.lms.security.UserDetailsImpl;
import org.lms.service.UsersService;
import org.lms.utils.JwtUtil;
import org.lms.vo.UserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
* @author jeang
* @description 针对表【users(用户基本信息表)】的数据库操作Service实现
* @createDate 2025-09-13 22:53:03
*/
@Service
public class UsersServiceImpl implements UsersService {

    private UsersMapper usersMapper;
    private RedisTemplate<String,Object> redisTemplate;

    public UsersServiceImpl(UsersMapper usersMapper, RedisTemplate redisTemplate) {
        this.usersMapper = usersMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Result login(UsersDto usersDto) {
        String username = usersDto.getUsername();
        if(!StringUtils.hasText(username)){
            throw new RuntimeException("没有用户名");
        }
        Users u = usersMapper.findByUsername(username);
        if(u==null){
            throw new RuntimeException("用户名出错");
        }
        String password = usersDto.getPassword();
        if(!StringUtils.hasText(password)){
            throw new RuntimeException("没有密码");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password1 = u.getPassword();
        if(!bCryptPasswordEncoder.matches(password,password1)){
            throw new RuntimeException("密码错误");
        }
        UserLoginVo userLoginVo = new UserLoginVo();
        BeanUtils.copyProperties(u,userLoginVo);
        String token = JwtUtil.createJWT(u.getId().toString());
        userLoginVo.setToken(token);
        redisTemplate.opsForValue().set(RedisConstant.API_TOKEN+token,u.getId(),30, TimeUnit.MINUTES);
        return Result.success(userLoginVo);
    }

    @Override
    public Result addUser(UsersDto usersDto) {
        if(usersDto==null){
            return Result.error();
        }
        if(!StringUtils.hasText(usersDto.getUsername())||(usersDto.getUsername().length()<=3)){
            return Result.error();
        }
        if(!StringUtils.hasText(usersDto.getPassword())||(usersDto.getPassword().length()<=5)){
            return Result.error();
        }
        Users users = Users.builder()
                .isActive(1)
                .userType(UserType.BUSINESS)
                .accountType(AccountType.ENTERPRISE)
                .isActive(1)
                .emailVerified(0)
                .lastLoginAt(Instant.now())
                .build();
        BeanUtils.copyProperties(usersDto,users);
        users.setPassword(new BCryptPasswordEncoder().encode(usersDto.getPassword()));
        int insert = usersMapper.insert(users);
        if(insert<0){
            return Result.error("添加失败");
        }
        return Result.success();
    }

    @Override
    public Result updateUser(UsersUpdateDto updateDto) {
        Users users = new Users();
        BeanUtils.copyProperties(updateDto,users);
        int update = usersMapper.updateByPrimaryKey(users);
        if(update<0){
            return Result.error("修改失败");
        }
        return Result.success();
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete(RedisConstant.API_TOKEN+token);
        return Result.success();
    }

    @Override
    public Result pageQuery(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Users> usersList = usersMapper.queryAll();
        return Result.success(new PageInfo<Users>(usersList));
    }
    @Override
    public Result queryById(Long id) {
        Users users = usersMapper.selectByPrimaryKey(id);
        return Result.success(users);
    }

    @Override
    public Result list(List<Long> ids) {
        if(ids==null || ids.isEmpty()){
            return Result.success(Collections.emptyList());
        }
        List<Users> usersList = usersMapper.queryList(ids);
        return Result.success(usersList);
    }

    @Override
    public Result remove(List<Long> ids) {
        usersMapper.removeByList(ids);
        return Result.success();
    }
}




