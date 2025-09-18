package org.lms.service.impl;


import org.lms.constant.RedisConstant;
import org.lms.dto.UsersDto;
import org.lms.entity.Users;
import org.lms.mapper.UsersMapper;
import org.lms.response.Result;
import org.lms.security.UserDetailsImpl;
import org.lms.service.UsersService;
import org.lms.utils.JwtUtil;
import org.lms.vo.UserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        String oldPassword = bCryptPasswordEncoder.encode(password);
        String password1 = u.getPassword();
        if(!oldPassword.equals(password1)){
            throw new RuntimeException("密码错误");
        }
        UserDetailsImpl userDetails = new UserDetailsImpl(u);
        UserLoginVo userLoginVo = new UserLoginVo();
        BeanUtils.copyProperties(u,userLoginVo);
        String token = JwtUtil.createJWT(u.getId().toString());
        userLoginVo.setToken(token);
        redisTemplate.opsForValue().set(RedisConstant.API_TOKEN+token,userDetails);
        return Result.success(userLoginVo);
    }
}




