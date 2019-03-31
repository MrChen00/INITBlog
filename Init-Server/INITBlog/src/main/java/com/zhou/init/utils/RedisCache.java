package com.zhou.init.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Redis缓存
 * @author ZHOU
 * @create 2019-03-12 13:59
 */
@Component
public class RedisCache {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

}
