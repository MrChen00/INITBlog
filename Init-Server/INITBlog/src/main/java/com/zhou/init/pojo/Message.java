package com.zhou.init.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhou.init.enums.MessageTypeEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ZHOU
 * @create 2019-03-12 11:13
 */
@Data
public class Message {
    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 用户名称
     */
    private String uname;
    /**
     * 用户头像
     */
    private String hportrait;
    /**
     * 文章ID
     */
    private long bid;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 消息类型
     */
    private String type;
    /**
     * 消息
     */
    private String message;
    /**
     * 时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    /**
     * 消息状态
     *      未读
     *      已读
     */
    private String status;
}
