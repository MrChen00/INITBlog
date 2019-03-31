package com.zhou.init.mapper;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhou.init.pojo.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZHOU
 * @Date: 2019/1/26 21:29
 */
public interface UserAccountMapper {

    /**
     * 登录
     * @param userAccount
     * @return
     */
    UserAccount selectByEmailAndPwd(UserAccount userAccount);

    /**
     * 名称查询用户
     * @param userName
     * @return
     */
    UserAccount selectByName(String userName);

    /**
     * ID查询用户
     * @param id
     * @return
     */
    Map selectById(Integer id);

    /**
     * 用户名是否唯一
     * @param username
     * @return
     */
    Integer selectByUserNameUnique(String username);

    /**
     * 注册
     * @return
     */
    void insertUserAccount(UserAccount userAccount);

    /**
     * 抓取测试
     * @param id
     * @return
     */
    Map grabById(Integer id);

    /**
     * 模糊查询名称
     * @return
     */
    List<Map> selectLikeName(String name);

    /**
     * 修改基本信息
     *      昵称, 头像, 手机号, 密码
     * @param map
     */
    void updateBasic(Map map);

}
