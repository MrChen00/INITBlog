package com.zhou.init.enums;

/**
 * 消息类型枚举
 * @Author: ZHOU
 * @Date: 2019/3/12 11:21
 */
public enum MessageTypeEnum {

    SYSTEM("官方消息"),
    LIKE("点赞"),
    ARTICLECOMMENT("文章评论"),
    REPLY("文章回复"),
    WORDS("留言"),
    // 消息状态
    UNREAD("未读"),
    READ("已读");

    private String type;

    public String getType() {
        return type;
    }
    MessageTypeEnum(String type){
        this.type = type;
    }



}
