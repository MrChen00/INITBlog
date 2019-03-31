package com.zhou.init.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Cors跨域配置
 * @author ZHOU
 * @create 2019-01-28 20:46
 */
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        // CORS 配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许访问的域
        corsConfiguration.addAllowedOrigin("*");
        // 允许访问的头信息
        corsConfiguration.addAllowedHeader("*");
        // 允许的请求方式
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        // 映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 拦截一切请求
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}