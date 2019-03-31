package com.zhou.init.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 文章评论
 * @author ZHOU
 * @create 2019-02-26 16:21
 */
@Data
public class ArtComment {

    /**
     * 评论ID
     */
    long id;
    /**
     * 文章ID
     */
    long bid;
    /**
     * 给谁评论的ID
     */
    Integer uid;
    /**
     * 回复的ID
     */
    long pid;
    /**
     * 评论的用户ID
     */
    Integer cid;
    /**
     * 评论内容
     */
    String content;
    /**
     * 评论时间
     */
    Date time;

    /**
     * 文章标题
     * 为了消息而添加的字段,
     */
    String title;

}
