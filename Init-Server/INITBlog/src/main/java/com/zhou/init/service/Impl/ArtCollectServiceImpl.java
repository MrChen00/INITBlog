package com.zhou.init.service.Impl;

import com.zhou.init.dto.StatusCode;
import com.zhou.init.exception.ResultException;
import com.zhou.init.mapper.ArtCollectMapper;
import com.zhou.init.pojo.ArtCollect;
import com.zhou.init.pojo.BlogArticle;
import com.zhou.init.service.ArtCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ZHOU
 * @create 2019-03-05 13:36
 */
@Service
@Transactional
public class ArtCollectServiceImpl implements ArtCollectService {

    @Autowired
    ArtCollectMapper artCollectMapper;

    @Override
    public void add(ArtCollect artCollect) {
        try{
            artCollectMapper.insert(artCollect);
        }catch (Exception e){
            e.printStackTrace();
            throw new ResultException("添加异常");
        }
    }

    @Override
    public void delete(ArtCollect artCollect) {
        try{
            artCollectMapper.delete(artCollect);
        }catch (Exception e){
            e.printStackTrace();
            throw new ResultException("删除异常");
        }
    }

    @Override
    public Integer count(Integer bid) {
        return artCollectMapper.count(bid);
    }

    @Override
    public List<BlogArticle> listArticle(Integer uid) {
        return artCollectMapper.listArticle(uid);
    }
}
