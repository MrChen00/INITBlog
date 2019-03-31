package com.zhou.init.service.Impl;

import com.zhou.init.exception.ResultException;
import com.zhou.init.mapper.AccAttentionMapper;
import com.zhou.init.mapper.BlogArticleMapper;
import com.zhou.init.mapper.UserAccountMapper;
import com.zhou.init.pojo.AccAttention;
import com.zhou.init.service.AccAttentionService;
import com.zhou.init.service.BlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ZHOU
 * @create 2019-03-08 9:44
 */
@Service
@Transactional
public class AccAttentionServiceImpl implements AccAttentionService {

    @Autowired
    AccAttentionMapper accAttentionMapper;

    @Autowired
    UserAccountMapper userAccountMapper;

    @Override
    public void add(AccAttention accAttention) {
        try{
            accAttentionMapper.add(accAttention);
        }catch (Exception e){
            e.printStackTrace();
            throw new ResultException("添加错误");
        }
    }

    @Override
    public void delete(AccAttention accAttention) {
        try{
            accAttentionMapper.delete(accAttention);
        }catch (Exception e){
            e.printStackTrace();
            throw new ResultException("删除错误");
        }
    }

    @Override
    public Integer countFollow(Integer uid) {
        return accAttentionMapper.countFollow(uid);
    }

    @Override
    public Integer countLike(Integer uid) {
        return accAttentionMapper.countLike(uid);
    }

    @Override
    public Boolean checkFollow(AccAttention accAttention) {
        Boolean bear = false;
        if(accAttentionMapper.checkFollow(accAttention) == 0 ){
            bear = true;
        }
        return bear;
    }

    @Override
    public List<Map> listFollow(Integer uid) {
        List<Map> mapList = new ArrayList<>();
        List<Integer> lists = accAttentionMapper.listFollow(uid);
        for (Integer list : lists) {
            Map map = userAccountMapper.grabById(list);
            map.put("id", list);
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public List<Map> listLike(Integer uid) {
        List<Map> mapList = new ArrayList<>();
        List<Integer> lists = accAttentionMapper.listLike(uid);
        for (Integer list : lists) {
            Map map = userAccountMapper.grabById(list);
            map.put("id", list);
            mapList.add(map);
        }
        return mapList;
    }
}
