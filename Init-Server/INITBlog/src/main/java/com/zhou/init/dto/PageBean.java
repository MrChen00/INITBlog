package com.zhou.init.dto;

import lombok.Data;

import java.util.List;

/**
 * 分页
 * @author ZHOU
 * @create 2019-04-12 8:19
 */
@Data
public class PageBean {

    /**
     * 当前页
     */
    private long page;

    /**
     * 页大小
     */
    private Integer size;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 总数量
     */
    private long totalElement;

    /**
     * 分页数据
     */
    private List lists;

}
