package com.zhou.init.search;

/**
 * 查询类型
 * @Author: ZHOU
 * @Date: 2019/4/11 17:21
 */
public enum SearchType {

    All("All", "全部"),
    ARTICLE("ARTICLE", "博客"),
    TAG("TAG", "标签"),
    USER("USER", "用户");

    String key;
    String value;


    SearchType(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

}
