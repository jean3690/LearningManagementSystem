package org.lms.security.Filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lms.entity.Users;
import org.lms.mapper.UsersMapper;
import org.lms.security.UserDetailsImpl;
import org.lms.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthorizeFilter extends OncePerRequestFilter {
    private UsersMapper usersMapper;
    private RedisTemplate<String,Object> redisTemplate;

    private static final String prefix = "login:token:";

    public JwtAuthorizeFilter(UsersMapper usersMapper, RedisTemplate<String,Object> redisTemplate) {
        this.usersMapper = usersMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(!StringUtils.hasText(token)||token.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
        }
        token = token.substring(7);
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            // JWT 解析失败（过期、篡改等）
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            return;
        }
        String redisKey = prefix+token;
        UserDetailsImpl userDetails = (UserDetailsImpl) redisTemplate.opsForValue().get(redisKey);
        if(userDetails==null){
            Users users = usersMapper.selectByPrimaryKey(Long.valueOf(userId));
            if(users==null){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("User not logged in");
                return;
            }
            userDetails = new UserDetailsImpl(users);
            redisTemplate.opsForValue().set(redisKey,userDetails);
        }
        redisTemplate.expire(redisKey,30,TimeUnit.MINUTES);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
