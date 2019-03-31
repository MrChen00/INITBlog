package com.zhou.init.service;

import com.zhou.init.pojo.AccProfile;

/**
 * @Author: ZHOU
 * @Date: 2019/2/15 16:30
 */
public interface AccProfileService {

    /**
     * 只添加ID: 用于用户注册
     * @param id
     */
    void add(Integer id);

    /**
     * 修改
     * @param accProfile
     */
    void update(AccProfile accProfile);

    /**
     * 主键获取
     * @param uid
     * @return
     */
    AccProfile get(Integer uid);

}
