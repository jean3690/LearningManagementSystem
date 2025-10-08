package org.lms.security.Filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.lms.Context.UserContextHolder;
import org.lms.constant.RedisConstant;
import org.lms.dto.UsersDto;
import org.lms.entity.Users;
import org.lms.mapper.UsersMapper;
import org.lms.security.UserDetailsImpl;
import org.lms.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JwtAuthorizeFilter extends OncePerRequestFilter {
    private UsersMapper usersMapper;
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/doc.html") ||
                path.startsWith("/webjars/") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/swagger-resources/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/v3/api-docs/") ||
                path.startsWith("/swagger-ui/") ||
                path.equals("/favicon.ico") ||
                path.equals("/swagger-ui.html") ||
                path.startsWith("/admin/user/");
    }

    public JwtAuthorizeFilter(UsersMapper usersMapper, RedisTemplate<String,Object> redisTemplate) {
        this.usersMapper = usersMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(!StringUtils.hasText(token)||!token.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
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
        String redisKey = RedisConstant.API_TOKEN +token;
        Long id  = (Long) redisTemplate.opsForValue().get(redisKey);
        if(id==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not logged in");
            return;
        }
        Long ttl = redisTemplate.getExpire(redisKey, TimeUnit.MINUTES);
        if(ttl!=null && ttl <15){
            redisTemplate.expire(redisKey,30,TimeUnit.MINUTES);
        }

        Users users = usersMapper.selectByPrimaryKey(id);
        UsersDto usersDto = new UsersDto();
        usersDto.setToken(token);
        BeanUtils.copyProperties(users,usersDto);
        UserContextHolder.set(usersDto);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(new UserDetailsImpl(users), null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
