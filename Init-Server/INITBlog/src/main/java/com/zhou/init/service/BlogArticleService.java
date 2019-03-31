package com.zhou.init.service;

import com.zhou.init.pojo.BlogArticle;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZHOU
 * @Date: 2019/2/15 11:00
 */
public interface BlogArticleService {

    /**
     * 添加
     */
    void add(BlogArticle blogArticle);

    /**
     * 修改
     * @param blogArticle
     */
    void update(BlogArticle blogArticle);

    /**
     * 查询
     */
    BlogArticle getByID(Integer id);

    /**
     * 分类文章数量
     */
    Integer countByTid(Integer tid);

    /**
     * 分类文章
     */
    List<BlogArticle> listByTid(Integer tid);

    /**
     * 删除文章
     * @param id
     */
    void remove(Integer id);

    /**
     * 修改封面路径
     * @param id
     * @param coverUrl
     */
    void updateCover(Integer id, String coverUrl);

    /**
     * 修改文章内容/md
     * @param id
     * @param content
     * @param contentMd
     */
    void updateContentAndMd(Integer id, String content, String contentMd);

    /**
     * 抓取测试
     * @param id
     * @return
     */
    Map grabById(Integer id);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    Map getByUid(Integer id);

    /**
     * 修改浏览或点赞
     * @param id
     */
    void updateByViewsOrLikes(Integer id);

    /**
     * 获取热门文章
     * @return
     */
    List<Map> listHotArticle();

    /**
     * 修改点赞
     * @param id
     */
    void updateLike(Integer id);

    /**
     * 模糊查询文章标题
     * @param title
     * @return
     */
    List<Map> listLikeTitle(String title);

}
