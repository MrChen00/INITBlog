package com.zhou.init.mapper;

import com.zhou.init.pojo.ArtCollect;
import com.zhou.init.pojo.BlogArticle;

import java.util.List;

/**
 * @Author: ZHOU
 * @Date: 2019/3/5 13:29
 */
public interface ArtCollectMapper {

    /**
     * 添加
     * @param artCollect
     */
    void insert(ArtCollect artCollect);

    /**
     * 删除
     * @param artCollect
     */
    void delete(ArtCollect artCollect);

    /**
     * 文章收藏数量
     * @param bid
     * @return
     */
    Integer count(Integer bid);

    /**
     * 获取收藏文章
     * @param uid
     * @return
     */
    List<BlogArticle> listArticle(Integer uid);

}
