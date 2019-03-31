package com.zhou.init.service;

import com.zhou.init.pojo.AccAttention;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZHOU
 * @Date: 2019/3/8 9:42
 */
public interface AccAttentionService {

    /**
     * 添加关注
     * @param accAttention
     */
    void add(AccAttention accAttention);

    /**
     * 取消关注
     * @param accAttention
     */
    void delete(AccAttention accAttention);

    /**
     * 关注数量
     * @param uid
     * @return
     */
    Integer countFollow(Integer uid);

    /**
     * 粉丝数量
     * @param uid
     * @return
     */
    Integer countLike(Integer uid);

    /**
     * 判断已关注
     * @return
     */
    Boolean checkFollow(AccAttention accAttention);

    /**
     * 获取所有关注信息
     * @param uid
     * @return
     */
    List<Map> listFollow(Integer uid);

    /**
     * 获取所有粉丝信息
     * @param uid
     * @return
     */
    List<Map> listLike(Integer uid);

}
