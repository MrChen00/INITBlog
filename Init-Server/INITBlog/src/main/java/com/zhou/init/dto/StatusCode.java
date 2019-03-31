package com.zhou.init.dto;

/**
 * 操作状态码
 * @author ZHOU
 * @create 2019-02-14 14:34
 */
public class StatusCode {

    public static final int SUCCESS = 20000; // 成功
    public static final int ERROR = 20001; // 失败
    public static final int LOGIN_ERROR = 20002; // 用户名或密码错误
    public static final int ACCESS_ERROR = 20003; // 权限不足
    public static final int PRPEE_ERROR = 20004; // 重复操作
    public static final int PRRAMTER_ERROR = 20005; // 入参错误

}
