package com.zhou.init.mapper;

import com.zhou.init.pojo.ArtComment;

import java.util.List;

/**
 * @Author: ZHOU
 * @Date: 2019/2/26 16:27
 */
public interface ArtCommentMapper {

    /**
     * 添加
     * @param artComment 评论信息
     */
    void insert(ArtComment artComment);

    /**
     * 获取所有首条评论
     * @param bid 文章ID
     * @return
     */
    List<ArtComment> selectByBid(Integer bid);

    /**
     * 是否有回复评论
     * @param id
     * @return
     */
    Integer countByPid(long id);

    /**
     * 获取回复评论
     * @param pid
     * @return
     */
    List<ArtComment> selectByPid(long pid);

    /**
     * 删除文章评论
     */
    void delete(long id);

}
