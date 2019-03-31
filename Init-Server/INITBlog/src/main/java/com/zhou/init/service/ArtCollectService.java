package com.zhou.init.service;

import com.zhou.init.pojo.ArtCollect;
import com.zhou.init.pojo.BlogArticle;

import java.util.List;

/**
 * @Author: ZHOU
 * @Date: 2019/3/5 13:35
 */
public interface ArtCollectService {

    /**
     * 添加
     * @param artCollect
     */
    void add(ArtCollect artCollect);

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
