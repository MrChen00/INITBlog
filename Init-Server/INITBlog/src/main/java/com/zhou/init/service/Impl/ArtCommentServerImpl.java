package com.zhou.init.service.Impl;

import com.zhou.init.enums.ResultEnums;
import com.zhou.init.exception.ResultException;
import com.zhou.init.mapper.ArtCommentMapper;
import com.zhou.init.pojo.ArtComment;
import com.zhou.init.service.ArtCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ZHOU
 * @create 2019-02-26 16:35
 */
@Service
@Transactional
public class ArtCommentServerImpl implements ArtCommentService {

    @Autowired
    ArtCommentMapper artCommentMapper;

    @Override
    public void add(ArtComment artComment) {
        try {
            artCommentMapper.insert(artComment);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ResultException(ResultEnums.ERROR);
        }
    }

    @Override
    public List<ArtComment> getByBid(Integer bid) {
        return artCommentMapper.selectByBid(bid);
    }

    @Override
    public Integer countByPid(long id) {
        return artCommentMapper.countByPid(id);
    }

    @Override
    public List<ArtComment> listByPid(long pid) {
        return artCommentMapper.selectByPid(pid);
    }

    @Override
    public void delete(long id) {
        try {
            artCommentMapper.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            throw  new ResultException(ResultEnums.ERROR);
        }
    }

}
