package org.lms.Interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lms.utils.JwtUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserInterceptor implements HandlerInterceptor {

    private static final String BEARER = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        // 如果 header 为空或不是 Bearer 类型，放行（或可根据需求拦截）
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(BEARER)) {
            // 可选择返回 false 并返回 401，如果要求必须登录
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"code\":401,\"msg\":\"未提供有效 token\"}");
            return false;
        }

        String token = authHeader.substring(BEARER.length()).trim();

        try {
            Claims claims = JwtUtil.parseJWT(token);

            // 将用户信息放入 request，供后续使用
//            request.setAttribute("userClaims", claims);
            // 或提取 userId/username 单独存放
            // request.setAttribute("userId", claims.getSubject());

            return true; // 放行请求

        } catch (Exception e) {
            // JWT 解析失败：过期、签名错误等
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"code\":401,\"msg\":\"token无效或已过期\"}");
            return false;
        }
    }
}