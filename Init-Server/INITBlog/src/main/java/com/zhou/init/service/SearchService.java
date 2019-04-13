package com.zhou.init.service;

import com.zhou.init.dto.PageBean;
import com.zhou.init.search.BlogArticle;
import com.zhou.init.search.UserAccount;

import java.util.List;

/**
 * @Author: ZHOU
 * @Date: 2019/4/11 22:33
 */
public interface SearchService {

    /**
     * 创建索引
     * @param indexClass
     */
    void createIndex(Class indexClass);

    /**
     * 删除索引
     * @param indexClass
     */
    void deleteIndex(Class indexClass);

    /**
     * 添加文章信息
     */
    void addArticle(Integer articleId);

    /**
     * 添加用户信息
     */
    void addUser(Integer uid);

    /**
     * 标题/摘要/标签查询
     * @param queryName
     * @param type
     * @return
     */
    PageBean search(String queryName, String type, Integer page, Integer size);

    /**
     * 查询用户
     * @param queryName
     * @return
     */
    PageBean search(String queryName, Integer page, Integer size);

}
