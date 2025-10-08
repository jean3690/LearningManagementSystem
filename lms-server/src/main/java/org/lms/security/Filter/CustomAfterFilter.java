package org.lms.security.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.lms.Context.UserContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Slf4j
public class CustomAfterFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/doc.html") ||
                path.startsWith("/webjars/") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui/") ||
                path.equals("/favicon.ico") ||
                path.equals("/swagger-ui.html") ||
                path.startsWith("/img.icons/") ||
                path.startsWith("/admin/user/");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserContextHolder.remove();
        filterChain.doFilter(request,response);
    }
}
