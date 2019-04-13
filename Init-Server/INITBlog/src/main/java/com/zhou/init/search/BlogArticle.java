package com.zhou.elasticsearch.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author ZHOU
 * @create 2019-04-11 15:26
 */
@Data
@Document(indexName = "initarticle", type = "article", shards = 1, replicas = 0)
public class BlogArticle {
    /**
     * 文章ID
     */
    @Id
    private long id;
    /**
     * 文章标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    /**
     * 文章摘要
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String contentShort;
    /**
     * 文章浏览量
     */
    @Field(index = false, type = FieldType.Keyword)
    private long pageViews;
    /**
     * 文章点赞
     */
    @Field(index = false, type = FieldType.Keyword)
    private long likes;
    /**
     * 文章标签
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String tag;
    /**
     * 搜索类型
     */
    @Field(index = false, type = FieldType.Keyword)
    private SearchType searchType;
}
