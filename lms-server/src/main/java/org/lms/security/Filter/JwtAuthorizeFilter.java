package org.lms.security.Filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.lms.constant.RedisConstant;
import org.lms.entity.Users;
import org.lms.mapper.UsersMapper;
import org.lms.security.UserDetailsImpl;
import org.lms.utils.JwtUtil;
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
    private final ExecutorService executorService;

    public JwtAuthorizeFilter(UsersMapper usersMapper, RedisTemplate<String,Object> redisTemplate, @Qualifier("taskExecutor") ExecutorService executorService) {
        this.usersMapper = usersMapper;
        this.redisTemplate = redisTemplate;
        this.executorService = executorService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(!StringUtils.hasText(token)||!token.startsWith("Bearer ")){
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
        String redisKey = RedisConstant.API_TOKEN +token;
        UserDetailsImpl userDetails = (UserDetailsImpl) redisTemplate.opsForValue().get(redisKey);
        if(userDetails==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not logged in");
            return;
        }
        CompletableFuture.runAsync(()->{
            try {
                redisTemplate.expire(redisKey,30,TimeUnit.MINUTES);
            }catch (Exception e){
                log.error("续期失败");
            }
        },executorService);
        Users users = userDetails.getUsers();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
