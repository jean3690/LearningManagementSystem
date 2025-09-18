package org.lms.constant;

public class SystemConstant {
    /**
     * 编码常量
     */
    public static class Charset {

        /**
         * 编码格式设置
         */
        public static final String JSON_TYPE_UTF8_CHARSET = "application/json;charset=UTF-8";

    }

    /**
     * 允许匿名访问的静态资源路径列表
     */
    public static final String[] STATIC_WITHE_PATH_LIST = new String[]{
            "/",
            "/js/**",
            "/css/**",
            "/img/**",
            "/fonts/**",
            "/index.html",
            "/favicon.ico",
            "/doc.html",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v3/**"
    };

    /**
     * 允许匿名访问的静态资源存放位置列表
     */
    public static final String[] STATIC_WITHE_LOCATION_LIST = new String[]{
            "classpath:/static/",
            "classpath:/public/",
            "classpath:/META-INF/resources/"
    };

}
