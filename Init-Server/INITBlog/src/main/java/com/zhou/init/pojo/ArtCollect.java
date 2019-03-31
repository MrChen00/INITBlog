package com.zhou.init.pojo;

import lombok.Data;

/**
 * 文章收藏
 * @author ZHOU
 * @create 2019-03-05 13:27
 */
@Data
public class ArtCollect {

    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 收藏文章ID
     */
    private Integer bid;

}
