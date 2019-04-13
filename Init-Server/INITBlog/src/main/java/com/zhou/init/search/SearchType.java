package com.zhou.elasticsearch.pojo;

/**
 * 查询类型
 * @Author: ZHOU
 * @Date: 2019/4/11 17:21
 */
public enum SearchType {

    ARTICLE("博客"),
    TAG("标签"),
    USER("用户");

    String value;

    SearchType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
