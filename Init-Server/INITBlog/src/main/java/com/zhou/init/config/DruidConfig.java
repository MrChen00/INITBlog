package com.zhou.init.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Druid 配置
 * @author ZHOU
 * @create 2019-02-13 13:37
 */
@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid(){
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    // 注册后台界面 Servlet Bean, 用于显示后台界面
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> param = new HashMap<String,String>();
        param.put("loginUsername", "admin");
        param.put("loginPassword", "123123");
        bean.setInitParameters(param);
        return bean;
    }

}
