package com.zhou.init;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhou.init.filter.ArticleViewFilter;
import com.zhou.init.filter.ShiroUserFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 开启扫描定时器注解
@MapperScan("com.zhou.init.mapper") // MyBatis接口扫描
@PropertySource("classpath:druidconfig.properties") // Druid配置文件
public class InitBlogApplication {

    /**
     * FastJson 配置
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonConfigure(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //日期格式化
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(converter);
    }

//    @Bean
//    public FilterRegistrationBean filterRegiste(){
//        FilterRegistrationBean regFilter = new FilterRegistrationBean();
//        regFilter.setFilter(new ArticleViewFilter());
//        regFilter.addUrlPatterns("/*");
//        regFilter.setName("ArticleView");
//        regFilter.setOrder(1);
//        return regFilter;
//    }

    @Bean
    public FilterRegistrationBean shiroUserFilter(){
        FilterRegistrationBean regFilter = new FilterRegistrationBean();
        regFilter.setFilter(new ShiroUserFilter());
        regFilter.addUrlPatterns("/*");
        regFilter.setName("ShiroUserFilter");
        regFilter.setOrder(2);
        return regFilter;
    }


    public static void main(String[] args) {
        SpringApplication.run(InitBlogApplication.class, args);
    }

}

