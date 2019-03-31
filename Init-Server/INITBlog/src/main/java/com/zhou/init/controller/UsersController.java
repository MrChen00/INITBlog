package com.zhou.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.MessageTypeEnum;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.Message;
import com.zhou.init.service.AccProfileService;
import com.zhou.init.service.ArtTagService;
import com.zhou.init.service.BlogArticleService;
import com.zhou.init.service.UserAccountService;
import com.zhou.init.utils.ITArticleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author ZHOU
 * @create 2019-02-26 11:14
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    BlogArticleService blogArticleService;
    @Autowired
    UserAccountService userAccountService;
    @Autowired
    AccProfileService accProfileService;
    @Autowired
    ArtTagService artTagService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ITArticleCache itArticleCache;

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Result getUser(Integer id){
        // 如果有字段为空, 会没有该字段的键
        Map<String,Object> objectMap =  userAccountService.getById(id);
        objectMap.remove("password");
        return new Result(StatusCode.SUCCESS, objectMap);
    }

    /**
     * 主页文章抓取测试
     * @return
     */
    @RequestMapping("/grabById")
    public List<Map> grabById(){
        return blogArticleService.listHotArticle();
    }

    /**
     * 实时资讯获取
     */
    @RequestMapping(value = "/getItArticle", method = RequestMethod.GET)
    public Result getJsoupIT() {
        try {
            return new Result(StatusCode.SUCCESS, itArticleCache.getIthome());
        }catch (Exception ex){
            return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
        }
    }

    /**
     * 缓存资讯获取
     * @return
     */
    @RequestMapping(value = "/getItRedis", method = RequestMethod.GET)
    public Result getItRedis(){
        try {
            return new Result(StatusCode.SUCCESS, itArticleCache.getIthome());
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }


    /**
     * 定时任务存储在Redis中
     *      每4个小时更新IT资讯
     */
    @Scheduled(cron = "0 0 0/4 * * *")
    public void getScheduled(){
        try {
            itArticleCache.setIthome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 主页搜索
     * @param name
     * @return
     */
    @RequestMapping(value = "/listLikeName", method = RequestMethod.GET)
    public Result listLikeName(String name){
        List<Map> list = new ArrayList<>();
        // 用户
        for (Map map : userAccountService.listLikeName(name)) {
            map.put("type", "User");
            list.add(map);
        }
        // 文章
        for (Map map : blogArticleService.listLikeTitle(name)) {
            map.put("type", "Article");
            list.add(map);
        }
        // 标签
        for (Map map : artTagService.listLikeName(name)) {
            map.put("type", "Tag");
            list.add(map);
        }
        return new Result(StatusCode.SUCCESS, list);
    }

}
