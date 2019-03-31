package com.zhou.init.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.exception.ResultException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IT爬虫资讯缓存
 *      并定时更新缓存
 * @author ZHOU
 * @create 2019-03-12 9:27
 */
@Component
public class ITArticleCache {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 实时获取IT之家的资讯
     * @return
     */
    public List<Map> getNowIthome() {
        List<Map> lists = new ArrayList<Map>();
        Document document = null;
        try {
            document = Jsoup.connect("https://www.ithome.com").get();
            Element div = document.selectFirst("div[class='con-block']");
            Element div2 = div.selectFirst("div[class='nlst']");
            Elements elements = div2.select("ul li[class='new']");
            for (int i = 0; i < 12; i++) {
                Element element1 = elements.get(i).selectFirst("a");
                Map map = new HashMap();
                map.put("title", element1.text());
                map.put("href", element1.attr("href"));
                lists.add(map);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException("获取错误");
        }
    }

    /**
     * 获取IT之家资讯到缓存中
     */
    public  void setIthome(){
        try {
            List<Map> lists = getNowIthome();
            stringRedisTemplate.opsForValue().set("INITBlogItArticles",  JSONObject.toJSON(lists).toString());
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ResultException("文章缓存失败");
        }
    }

    /**
     * 获取IT之家资讯的缓存
     * @return
     */
    public  List<Map> getIthome(){
        // 设置字符串 键值对
        String str = stringRedisTemplate.opsForValue().get("INITBlogItArticles");
        List<Map> list = (List<Map>) JSONArray.parse(str);
        return list;
    }

}
