package com.zhou.init.service.Impl;

import com.zhou.init.mapper.ArtTypeMapper;
import com.zhou.init.pojo.ArtType;
import com.zhou.init.service.ArtTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ZHOU
 * @create 2019-02-14 11:41
 */
@Service
@Transactional
public class ArtTypeServiceImpl implements ArtTypeService {

    @Autowired
    ArtTypeMapper artTypeMapper;

    @Override
    public Boolean add(ArtType artType) {
        Boolean bear = false;
        Integer count = artTypeMapper.insert(artType);
        if(count > 0){
            bear = true;
        }
        return bear;
    }

    @Override
    public List<ArtType> listByUid(Integer uid) {
        return artTypeMapper.selectByUid(uid);
    }

}
