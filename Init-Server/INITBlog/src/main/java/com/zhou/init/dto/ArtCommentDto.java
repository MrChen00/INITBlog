package com.zhou.init.dto;

import com.zhou.init.pojo.ArtComment;
import lombok.Data;

/**
 * 评论封装
 * @author ZHOU
 * @create 2019-02-27 18:03
 */
@Data
public class ArtCommentDto {

    // 评论人名称
    String nickName;

    // 评论人头像
    String hportrait;

    // 评论信息
    ArtComment artComment;

    // 评论回复数量
    Integer countReply;

    // 是否有回复
    boolean reply = false;

}
