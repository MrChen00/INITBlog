package com.zhou.init.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签
 * @author ZHOU
 * @create 2019-02-14 20:27
 */
@Data
@NoArgsConstructor
public class ArtTag {

    Integer bid; // 文章ID
    String name; // 标签名称
    Integer status; // 文章状态

}
