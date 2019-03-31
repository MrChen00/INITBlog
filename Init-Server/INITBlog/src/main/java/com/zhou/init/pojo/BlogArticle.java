package com.zhou.init.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 博客文章
 * @author ZHOU
 * @create 2019-02-14 23:20
 */
@Data
@NoArgsConstructor
public class BlogArticle {

    Integer uid; // 用户ID
    Integer id; // 文章ID
    String title; // 标题
    String cover; // 封面
    String contentShort; // 摘要
    String content; // 页面展示内容
    String contentMd; // 原始内容
    Integer pageViews; // 浏览量
    Integer likes; // 点赞
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date createTime; // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date publishTime; // 发布时间
    Integer tid; // 分类ID
    Integer status; // 状态

    String tag; // 附加的标签内容

}
