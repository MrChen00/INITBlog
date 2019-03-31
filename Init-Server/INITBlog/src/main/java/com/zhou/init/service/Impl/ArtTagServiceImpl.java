package com.zhou.init.service.Impl;

import com.zhou.init.mapper.ArtTagMapper;
import com.zhou.init.pojo.ArtTag;
import com.zhou.init.service.ArtTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ZHOU
 * @create 2019-02-14 20:36
 */
@Service
@Transactional
public class ArtTagServiceImpl implements ArtTagService {

    @Autowired
    ArtTagMapper artTagMapper;

    @Override
    public void add(ArtTag artTag) {
        artTagMapper.insert(artTag);
    }

    @Override
    public void update(ArtTag artTag) {
        artTagMapper.update(artTag);
    }

    @Override
    public List<String> getByName(Integer id) {
        String tags = artTagMapper.selectByName(id);
        String[] tag =  tags.split(",");
        return Arrays.asList(tag);
    }

    @Override
    public void remove(Integer id) {
        artTagMapper.delete(id);
    }

    @Override
    public List<String> listName() {
        return artTagMapper.selectName();
    }

    @Override
    public List<Map> listLikeName(String name) {
        return artTagMapper.selectLikeName("%" + name + "%");
    }

}
