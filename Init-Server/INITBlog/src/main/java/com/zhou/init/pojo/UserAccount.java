package com.zhou.init.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户账户表
 * @author ZHOU
 * @create 2019-01-26 21:01
 */
@Data
@NoArgsConstructor
public class UserAccount {

    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 昵称:
     * 1. 并不用于登录
     */
    private String nickName;
    /**
     * 头像
     */
    private String hportrait;
    /**
     * 号码:
     * 1. 唯一
     * 2. 如果后续上了短信服务会使用号码登录
     */
    private String phone;
    /**
     * 邮箱:
     * 1. 唯一
     * 2. 使用邮箱验证进行: 邮箱注册和登录
     */
    private String email;
    /**
     * 密码
     */
    private String password;

}
