package com.zhou.init.mapper;

import com.zhou.init.pojo.AccProfile;

/**
 * @Author: ZHOU
 * @Date: 2019/2/15 16:08
 */
public interface AccProfileMapper {

    /**
     * 由于通常注册用户时, 不会填写其他信息, 所以我们这里只需要一个记录即可.
     *
     * 添加
     *
     * @param id
     */
    void insert(Integer id);

    /**
     * 修改
     * @param accProfile
     */
    void updateByUid(AccProfile accProfile);

    /**
     * UID获取
     * @return
     */
    AccProfile selectByUid(Integer uid);



}
