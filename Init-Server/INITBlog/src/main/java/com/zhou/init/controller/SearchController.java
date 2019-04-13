package com.zhou.init.controller;


import com.zhou.init.dto.PageBean;
import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.search.BlogArticle;
import com.zhou.init.search.SearchType;
import com.zhou.init.search.UserAccount;
import com.zhou.init.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 * @author ZHOU
 * @create 2019-04-11 23:14
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    /**
     * 前台搜索
     * @param queryName 搜索值
     * @param type 搜索类型
     * @param page 页号
     * @param size 页大小
     * @return 响应
     */
    @RequestMapping("/query")
    public Result query(String queryName, String type, Integer page, Integer size){

        // 判断搜索类型
        if(type.equals(SearchType.USER.getKey())){
            return new Result(StatusCode.SUCCESS, searchService.search(queryName, page, size));
        }
        if(type.equals(SearchType.All.getKey())){
            List<PageBean> pageBeans = new ArrayList<>();
            pageBeans.add(searchService.search(queryName, type, page, size));
            pageBeans.add(searchService.search(queryName, page, size));
            return new Result(StatusCode.SUCCESS, pageBeans);
        }else{
            return new Result(StatusCode.SUCCESS, searchService.search(queryName, type, page, size));
        }
    }

    /**
     * 创建索引库
     * @return
     */
    @RequestMapping("/addIndex")
    public Result addIndex(){
        searchService.createIndex(BlogArticle.class);
        // searchService.createIndex(UserAccount.class);
        return new Result(StatusCode.SUCCESS, "ok");
    }

    /**
     * 删除索引库
     * @return
     */
//    @RequestMapping("/removeIndex")
//    public Result removeIndex(){
//        searchService.deleteIndex(UserAccount.class);
//        searchService.deleteIndex(BlogArticle.class);
//        return new Result(StatusCode.SUCCESS, "ok");
//    }

    /**
     * 新增用户
     * @param id
     * @return
     */
    @RequestMapping("/addUser")
    public Result addUser(Integer id){
        try{
            searchService.addUser(id);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
        }
        return new Result(StatusCode.SUCCESS, "ok");
    }

    /**
     * 新增文章
     * @param id
     * @return
     */
    @RequestMapping("/addArticle")
    public Result addArticle(Integer id){
        try{
            searchService.addArticle(id);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
        }
        return new Result(StatusCode.SUCCESS, "ok");
    }



}
