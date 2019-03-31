package com.zhou.init.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhou.init.pojo.UserAccount;
import org.apache.catalina.User;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZHOU
 * @Date: 2019/1/26 21:48
 */
public interface UserAccountService {

    /**
     * 用户登录
     * @param userAccount
     * @return
     */
    UserAccount getUserLogin(UserAccount userAccount);

    /**
     * 名称查询用户
     * @param userName
     * @return
     */
    UserAccount getByName(String userName);

    /**
     * ID查询用户信息
     * @param id
     * @return
     */
    Map getById(Integer id);

    /**
     * 用户是否重名
     * @param username
     * @return
     */
    Boolean countUserName(String username);

    /**
     * 用户注册
     */
    void addUserAccount(UserAccount userAccount);

    /**
     * 模糊查询名称
     * @param name
     * @return
     */
    List<Map> listLikeName(String name);

    /**
     * 修改基本信息
     *         昵称, 头像, 手机号, 密码
     * @param map
     */
    void updateBasic(Map map);

}
