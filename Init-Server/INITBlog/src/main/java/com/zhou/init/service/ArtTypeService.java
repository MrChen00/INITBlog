package com.zhou.init.service;

import com.zhou.init.pojo.ArtType;

import java.util.List;

/**
 * @Author: ZHOU
 * @Date: 2019/2/14 11:40
 */
public interface ArtTypeService {

    /**
     * 添加
     * @param artType
     * @return
     */
    Boolean add(ArtType artType);

    /**
     * 查询所有
     * @param uid
     * @return
     */
    List<ArtType> listByUid(Integer uid);

}
