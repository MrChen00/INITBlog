package com.zhou.init.service;

import com.zhou.init.pojo.ArtComment;

import java.util.List;

/**
 * @Author: ZHOU
 * @Date: 2019/2/26 16:33
 */
public interface ArtCommentService {

    /**
     * 添加
     * @param artComment
     */
    void add(ArtComment artComment);

    /**
     * 文章ID获取评论
     * @param bid
     * @return
     */
    List<ArtComment> getByBid(Integer bid);

    /**
     * 是否有回复评论
     * @param pid
     * @return
     */
    Integer countByPid(long pid);

    /**
     * 获取回复评论
     * @param pid
     * @return
     */
    List<ArtComment> listByPid(long pid);

    /**
     * 删除评论
     * @param id
     */
    void delete(long id);

}
