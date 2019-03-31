package com.zhou.init.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器目前没用
 * @author ZHOU
 * @create 2019-02-26 13:53
 */
public class ArticleViewFilter implements Filter {

//    private Logger logger = LoggerFactory.getLogger(ArticleViewFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String uri = request.getRequestURI();

        List<String> list = new ArrayList<String>();
        list.add(".css");
        list.add(".js");
        list.add(".woff");
        list.add(".map");
        list.add(".jpg");
        list.add(".png");
        list.add(".ico");
        list.add(".JPG");

        for (String str : list) {
            if(uri.endsWith(str)){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

//        String ua = request.getHeader("user-agent");
//        String ip = request.getRemoteAddr();
        filterChain.doFilter(servletRequest, servletResponse);
//        logger.info("uri:{},ip地址:{},ua:{}" , uri , ip , ua);

    }

}
