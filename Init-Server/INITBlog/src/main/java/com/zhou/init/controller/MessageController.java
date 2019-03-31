package com.zhou.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.MessageTypeEnum;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.Message;
import com.zhou.init.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author ZHOU
 * @create 2019-03-12 19:58
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 获取我的消息
     * @return
     */
    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public Result getMessage(Integer id){
        try {
            List<String> strings = stringRedisTemplate.opsForList().range(id.toString(), 0, -1);
            List<Message> messages = new ArrayList<>();
            for (String string : strings) {
                messages.add(JSONObject.parseObject(string, Message.class));
            }
            return new Result(StatusCode.SUCCESS, messages);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 删除消息
     * @param uid
     * @param index
     * @return
     */
    @RequestMapping(value = "/deleteMessage", method = RequestMethod.GET)
    public Result deleteMessage(Integer uid, Integer index){
        try{
            // 没有直接索引删除
            // 先更改索引位置为0, 在将为0的值删除.
            stringRedisTemplate.opsForList().set(uid.toString(), index , "0");
            stringRedisTemplate.opsForList().remove(uid.toString(), 0, "0");
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 获取消息数量
     *      这里可以使用发布订阅
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCountMessage", method = RequestMethod.GET)
    public Result getCountMessage(Integer id){
        // 实时消息
        String count = (String) stringRedisTemplate.opsForHash().get("INITBlogUsers", id.toString());
        List<String> strings = stringRedisTemplate.opsForList().range(id.toString(), 0, -1);
        // 未读消息
        Integer unreadCount = 0;
        for (String string : strings) {
            Message message = JSONObject.parseObject(string, Message.class);
            if(message.getStatus().equals(MessageTypeEnum.UNREAD.getType())){
                unreadCount++;
            }
        }
        Map<String, String> map = new HashMap();
        map.put("count", count);
        map.put("unreadCount", unreadCount.toString());
        return new Result(StatusCode.SUCCESS, map);
    }

    /**
     * 修改未读状态
     * @param id
     * @param index
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
    public void updateStatus(Integer id, Integer index){
        String str = stringRedisTemplate.opsForList().index(id.toString(), index);
        Message message = JSONObject.parseObject(str, Message.class);
        message.setStatus("已读");
        stringRedisTemplate.opsForList().set(id.toString(), index, JSONObject.toJSONString(message));
    }

    /**
     * 修改实时消息数量
     * @param id
     */
    @RequestMapping(value = "/updateCountMessage", method = RequestMethod.GET)
    public void updateCountMessage(String id){
        stringRedisTemplate.opsForHash().put("INITBlogUsers", id, "0");
    }

    /**
     * 发送官方消息
     * @param messages
     * @param title
     * @return
     */
    @RequestMapping(value = "/sendOfficialMessage", method = RequestMethod.GET)
    public Result sendOfficialMessage(String messages, String title, long bid){
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.setBid(bid);
                    message.setMessage(messages);
                    message.setType(MessageTypeEnum.SYSTEM.getType());
                    message.setUid(218);
                    message.setUname("INIT-Blog");
                    message.setHportrait("https://init-blog-oss.oss-cn-beijing.aliyuncs.com/init-blog/image/user/hportrait.jpg");
                    message.setTitle(title);
                    message.setTime(new Date());
                    message.setStatus(MessageTypeEnum.UNREAD.getType());
                    // 这里使用管道技术
                    // 缓存信息
                    String json = JSONObject.toJSON(message).toString();
                    // 获取所有用户
                    Set<Object> users = stringRedisTemplate.opsForHash().keys("INITBlogUsers");
                    for (Object user : users) {
                        // 发送消息
                        stringRedisTemplate.opsForList().leftPush(user.toString(), json);
                        // 未读消息增加
                        stringRedisTemplate.opsForHash().increment("INITBlogUsers", user.toString(), 1);
                    }
                }
            }).start();
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

}
