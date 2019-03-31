package com.zhou.init.service.Impl;

import com.zhou.init.mapper.AccProfileMapper;
import com.zhou.init.pojo.AccProfile;
import com.zhou.init.service.AccProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ZHOU
 * @create 2019-02-15 16:30
 */
@Service
@Transactional
public class AccProfileServiceImpl implements AccProfileService{

    @Autowired
    AccProfileMapper accProfileMapper;

    @Override
    public void add(Integer id) {
        accProfileMapper.insert(id);
    }

    @Override
    public void update(@RequestBody AccProfile accProfile) {
        accProfileMapper.updateByUid(accProfile);
    }

    @Override
    public AccProfile get(Integer uid) {
        return accProfileMapper.selectByUid(uid);
    }

}
