package com.zhou.init.service.Impl;

import com.zhou.init.enums.ResultEnums;
import com.zhou.init.exception.ResultException;
import com.zhou.init.mapper.ArtTagMapper;
import com.zhou.init.mapper.BlogArticleMapper;
import com.zhou.init.mapper.UserAccountMapper;
import com.zhou.init.pojo.ArtTag;
import com.zhou.init.pojo.BlogArticle;
import com.zhou.init.service.BlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author ZHOU
 * @create 2019-02-15 11:01
 */
@Service
@Transactional
public class BlogArticleServiceImpl implements BlogArticleService {

    @Autowired
    BlogArticleMapper blogArticleMapper;

    @Autowired
    ArtTagMapper artTagMapper;

    @Autowired
    UserAccountMapper userAccountMapper;

    @Override
    public void add(BlogArticle blogArticle) {
        blogArticleMapper.insert(blogArticle);
    }

    @Override
    public void update(BlogArticle blogArticle) {
        try {
            blogArticleMapper.update(blogArticle);
            // 修改标签
            ArtTag artTag = new ArtTag();
            artTag.setBid(blogArticle.getId());
            artTag.setName(blogArticle.getTag());
            artTag.setStatus(blogArticle.getStatus());
            artTagMapper.update(artTag);
        }catch (Exception ex){
            ex.printStackTrace();
            throw  new ResultException(ResultEnums.ERROR);
        }
    }

    @Override
    public BlogArticle getByID(Integer id) {
        return blogArticleMapper.selectByID(id);
    }

    @Override
    public Integer countByTid(Integer tid) {
        return blogArticleMapper.countByTid(tid);
    }

    @Override
    public List<BlogArticle> listByTid(Integer tid) {
        return blogArticleMapper.selectByTid(tid);
    }

    @Override
    public void remove(Integer id) {
        // 删除标签
        artTagMapper.delete(id);
        // 删除文章
        blogArticleMapper.delete(id);
    }

    @Override
    public void updateCover(Integer id, String coverUrl) {
        try {
            blogArticleMapper.updateCover(id, coverUrl);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ResultException(ResultEnums.ERROR);
        }
    }

    @Override
    public void updateContentAndMd(Integer id, String content, String contentMd) {
        try {
            blogArticleMapper.updateContentAndMd(id, content, contentMd);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ResultException(ResultEnums.ERROR);
        }
    }

    @Override
    public Map grabById(Integer id) {
        // 抓取文章再获取用户信息
        Map map = blogArticleMapper.grabById(id);
        map.putAll(userAccountMapper.grabById(Integer.parseInt(map.get("uid").toString())));
        return map;
    }

    @Override
    public Map getByUid(Integer id) {
        return userAccountMapper.grabById(id);
    }

    @Override
    public void updateByViewsOrLikes(Integer id) {
        try {
            blogArticleMapper.updateByViewsOrLikes(id);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ResultException("修改错误");
        }
    }

    @Override
    public List<Map> listHotArticle() {
        List<Map> list = blogArticleMapper.selectHotArticle();
        for (Map map : list) {
            map.putAll(userAccountMapper.grabById(Integer.parseInt(map.get("uid").toString())));
        }
        return list;
    }

    @Override
    public void updateLike(Integer id) {
        try {
            blogArticleMapper.updateLike(id);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ResultException("修改错误");
        }
    }

    @Override
    public List<Map> listLikeTitle(String title) {
        return blogArticleMapper.selectLikeTitle("%" + title + "%");
    }

}
