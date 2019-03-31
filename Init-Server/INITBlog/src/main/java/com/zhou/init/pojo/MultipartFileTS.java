package com.zhou.init.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件暂存
 * Temporary Storage
 *
 * 这里使用到了单例模式, 实现了数据共享,
 *      只用于记录 文章的封面
 *
 * @author ZHOU
 * @create 2019-02-21 10:17
 */
@Getter
@Setter
public class MultipartFileTS {
    /**
     * 文章ID
     */
    private Integer id;
    /**
     * 文件名/文件
     */
    private Map<String, InputStream> mapFile = new HashMap<String, InputStream>();

    /**
     * 饿汉式
     *  也就是立即加载, 直接静态调用
     * 懒汉式
     *  延迟加载, 先将静态对象=null, 判断是否为 null, 当调用时在实例化,
     */
    private MultipartFileTS(){}

    private static MultipartFileTS multipartFileTS = new MultipartFileTS();

    public static MultipartFileTS getInstance(){
        return multipartFileTS;
    }

    // 介绍
    // https://www.cnblogs.com/garryfu/p/7976546.html

}
