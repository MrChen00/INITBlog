package com.zhou.init.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhou.init.enums.MessageTypeEnum;
import com.zhou.init.mapper.AccProfileMapper;
import com.zhou.init.mapper.UserAccountMapper;
import com.zhou.init.pojo.Message;
import com.zhou.init.pojo.UserAccount;
import com.zhou.init.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ZHOU
 * @create 2019-01-26 21:49
 */
@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    UserAccountMapper userAccountMapper;

    @Autowired
    AccProfileMapper accProfileMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public UserAccount getUserLogin(UserAccount userAccount) {
        return  userAccountMapper.selectByEmailAndPwd(userAccount);
    }

    @Override
    public UserAccount getByName(String userName) {
        return userAccountMapper.selectByName(userName);
    }

    @Override
    public Map getById(Integer id) {
        return userAccountMapper.selectById(id);
    }

    @Override
    public Boolean countUserName(String username) {
        Boolean bear = true;
        int count = userAccountMapper.selectByUserNameUnique(username);
        if(count == 1){
            bear = false;
        }
        return bear;
    }

    @Override
    public void addUserAccount(UserAccount userAccount) {
        userAccountMapper.insertUserAccount(userAccount);
        accProfileMapper.insert(userAccount.getId());

        // 存储注册用户的ID, 用来接收消息
        stringRedisTemplate.opsForHash().put("INITBlogUsers", userAccount.getId().toString() ,"0");

        // 发送用户注册消息
        Message message = new Message();
        // 这里得改
        message.setBid(23);
        message.setMessage("欢迎您注册INIT-Blog, 希望和您一起创建更好的环境.");
        message.setType(MessageTypeEnum.SYSTEM.getType());
        message.setUid(218);
        message.setUname("INIT-Blog");
        message.setHportrait("https://init-blog-oss.oss-cn-beijing.aliyuncs.com/init-blog/image/user/hportrait.jpg");
        message.setTitle("INIT-Blog Welcome");
        message.setTime(new Date());
        message.setStatus(MessageTypeEnum.UNREAD.getType());
        // 缓存信息
        String json = JSONObject.toJSON(message).toString();
        // 发送消息
        stringRedisTemplate.opsForList().leftPush(userAccount.getId().toString(), json);
         // 未读消息增加
        stringRedisTemplate.opsForHash().increment("INITBlogUsers", userAccount.getId().toString(), 1);
    }

    @Override
    public List<Map> listLikeName(String name) {
        return userAccountMapper.selectLikeName("%" + name + "%");
    }

    @Override
    public void updateBasic(Map map) {
        try{
            userAccountMapper.updateBasic(map);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("修改异常");
        }
    }

}
