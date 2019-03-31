package com.zhou.init.controller;

import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.ArtTag;
import com.zhou.init.service.ArtTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ZHOU
 * @create 2019-02-14 20:45
 */
@RestController
@RequestMapping("/article/tag")
public class ArtTagController {

    @Autowired
    ArtTagService artTagService;

    /**
     * 添加
     * @param artTag
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody ArtTag artTag){
        try {
            artTagService.add(artTag);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 修改
     * @param artTag
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody ArtTag artTag){
        try{
            artTagService.update(artTag);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 文章ID 标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/getByName", method = RequestMethod.POST)
    public Result getByName(Integer id){
        return new Result(StatusCode.SUCCESS, artTagService.getByName(id));
    }

    /**
     * 查询前20的标签
     * @return
     */
    @RequestMapping(value = "/listName", method = RequestMethod.GET)
    public Result listName(){
        List<String> strings = artTagService.listName();
        // 由于我们的标签是针对文章的, 所以只能去除冗余的标签
        Set<String> stringSet = new HashSet<>();
        for (String string : strings) {
            String[] strs = string.split(",");
            for (String str : strs) {
                stringSet.add(str);
            }
        }
        return new Result(StatusCode.SUCCESS, stringSet);
    }

}
