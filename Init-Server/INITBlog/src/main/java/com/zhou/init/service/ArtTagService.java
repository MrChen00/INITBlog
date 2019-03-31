package com.zhou.init.service;

import com.zhou.init.pojo.ArtTag;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZHOU
 * @Date: 2019/2/14 20:34
 */
public interface ArtTagService {

    /**
     * 添加
     * @param artTag
     */
    void add(ArtTag artTag);

    /**
     * 修改
     * @param artTag
     */
    void update(ArtTag artTag);

    /**
     * 文章ID查询标签
     * @param id
     */
    List<String> getByName(Integer id);

    /**
     * 删除标签
    * @param id
     */
    void remove(Integer id);

    /**
     * 获取前20的标签
     * @return
     */
    List<String> listName();

    /**
     * 模糊查询标签
     * @param name
     * @return
     */
    List<Map> listLikeName(String name);

}
