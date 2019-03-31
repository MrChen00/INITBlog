package com.zhou.init.controller;

import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.ArtType;
import com.zhou.init.service.ArtTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 文章分类
 * @author ZHOU
 * @create 2019-02-14 13:19
 */
@RestController
@RequestMapping("/article/type")
public class ArtTypeController {

    @Autowired
    ArtTypeService artTypeService;

    /**
     * 添加文章分类
     */
    @RequestMapping("/add")
    public Result addType(Integer uid, String name){
        try {
            ArtType artType = new ArtType();
            artType.setUid(uid);
            artType.setName(name);
            artType.setCreateTime(new Date());
            artTypeService.add(artType);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }
    /**
     * 查询所有文章分类
     * @param uid
     * @return
     */
    @RequestMapping("/list")
    public Result listType(Integer uid){
        return new Result(StatusCode.SUCCESS ,artTypeService.listByUid(uid));
    }

}
