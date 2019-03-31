package com.zhou.init.mapper;

import com.zhou.init.pojo.ArtType;

import java.util.List;

/**
 * @Author: ZHOU
 * @Date: 2019/2/14 11:35
 */
public interface ArtTypeMapper {

    /**
     * 添加
     * @param artType
     * @return
     */
    Integer insert(ArtType artType);

    /**
     * 查询用户所有的分类
     * @return
     */
    List<ArtType> selectByUid(Integer uid);

}
