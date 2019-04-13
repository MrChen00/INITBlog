package com.zhou.init.search;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 结合使用的
 *  shards 分片是针对集群的 我们只有一台服务器所以就1个
 *  replicas 副本就是对该索引库的副本, 当主分片挂了就用副本来处理.
 *  用户
 * @author ZHOU
 * @create 2019-04-11 15:49
 */
@Data
@Document(indexName = "inituser", type = "useraccount", shards = 1, replicas = 0)
public class UserAccount {
    /**
     * 用户ID
     */
    @Id
    private Integer id;
    /**
     * 用户昵称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String nickname;
    /**
     * 用户头像
     */
    @Field(index = false, type = FieldType.Keyword)
    private String hportrait;
    /**
     * 关注数量
     */
    @Field(index = false, type = FieldType.Keyword)
    private long follow;
    /**
     * 粉丝数量
     */
    @Field(index = false, type = FieldType.Keyword)
    private long likes;

    /**
     * 搜索类型
     */
    @Field(index = false, type = FieldType.Keyword)
    private SearchType searchType;

}
