package org.lms.config;

import org.lms.Interceptor.UserInterceptor;
import org.lms.constant.SystemConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.lms.constant.SystemConstant.STATIC_WITHE_LOCATION_LIST;
import static org.lms.constant.SystemConstant.STATIC_WITHE_PATH_LIST;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final UserInterceptor userInterceptor = new UserInterceptor();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(STATIC_WITHE_PATH_LIST)
                .addResourceLocations(STATIC_WITHE_LOCATION_LIST)
                .setCachePeriod(0);

    }
}
