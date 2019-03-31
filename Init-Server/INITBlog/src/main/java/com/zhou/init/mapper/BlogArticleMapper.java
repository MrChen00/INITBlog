package com.zhou.init.mapper;

import com.zhou.init.pojo.BlogArticle;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZHOU
 * @Date: 2019/2/15 10:54
 */
public interface BlogArticleMapper {

    /**
     * 添加
     * @param blogArticle
     */
    void insert(BlogArticle blogArticle);

    /**
     * 修改
     * @param blogArticle
     */
    void update(BlogArticle blogArticle);

    /**
     * 根据文章ID查询
     * @param id
     * @return
     */
    BlogArticle selectByID(Integer id);


    /**
     * 分类文章数量
     * @param tid
     * @return
     */
    Integer countByTid(Integer tid);

    /**
     * 分类文章ID
     * @param tid
     * @return
     */
    List<BlogArticle> selectByTid(Integer tid);

    /**
     * 删除文章
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改封面路径
     * @param id
     * @param coverUrl
     */
    void updateCover(@Param("id") Integer id, @Param("coverUrl") String coverUrl);

    /**
     * 修改文章内容/Md
     * @param content
     * @param contentMd
     */
    void updateContentAndMd(@Param("id") Integer id, @Param("content") String content, @Param("contentMd") String contentMd);

    /**
     * 修改浏览量或者点赞
     * @param id
     */
    void updateByViewsOrLikes(Integer id);

    /**
     * 主页对文章数据的抓取
     *      现在只是查询出来测试
     * @param id
     * @return
     */
    Map grabById(Integer id);

    /**
     * 热门文章提取 之后会放在会 Redis 中
     * @return
     */
    List<Map> selectHotArticle();

    /**
     * 修改点赞
     * @param bid
     */
    void updateLike(Integer bid);

    /**
     * 模糊查询文章标题
     * @param title
     * @return
     */
    List<Map> selectLikeTitle(String title);

}
