package com.zhou.init.mapper;

import com.zhou.init.pojo.ArtTag;
import com.zhou.init.pojo.ArtType;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZHOU
 * @Date: 2019/2/14 20:28
 */
public interface ArtTagMapper {

    /**
     * 添加
     * @param artTag
     */
    void insert(ArtTag artTag);

    /**
     * 修改
     * @param artTag
     */
    void update(ArtTag artTag);

    /**
     * 查找标签
     * @param id
     * @return
     */
    String selectByName(Integer id);

    /**
     * 删除标签
     * @param id
     */
    void delete(Integer id);

    /**
     * 获取前二十的标签
     * @return
     */
    List<String> selectName();

    /**
     * 模糊查询标签
     * @param name
     * @return
     */
    List<Map> selectLikeName(String name);

}
