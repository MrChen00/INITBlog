package com.zhou.init.mapper;

import com.zhou.init.pojo.AccAttention;

import java.util.List;

/**
 * @author ZHOU
 * @create 2019-03-08 9:31
 */
public interface AccAttentionMapper {

    /**
     * 关注即成为别人的粉丝
     * @param accAttention
     */
    void add(AccAttention accAttention);

    /**
     * 取消关注
     * @param accAttention
     */
    void delete(AccAttention accAttention);

    /**
     * 获取关注数量
     * @param uid
     * @return
     */
    Integer countFollow(Integer uid);

    /**
     * 获取粉丝数量
     * @return
     */
    Integer countLike(Integer uid);

    /**
     * 判断已关注
     */
    Integer checkFollow(AccAttention accAttention);

    /**
     * 获取所有关注
     * @param uid
     * @return
     */
    List<Integer> listFollow(Integer uid);

    /**
     * 获取所有粉丝
     * @param uid
     * @return
     */
    List<Integer> listLike(Integer uid);

}
