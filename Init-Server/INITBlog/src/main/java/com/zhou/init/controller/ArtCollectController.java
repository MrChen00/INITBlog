package com.zhou.init.controller;

import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.ArtCollect;
import com.zhou.init.service.ArtCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章收藏
 * @author ZHOU
 * @create 2019-03-05 13:38
 */
@RestController
@RequestMapping("/collect")
public class ArtCollectController {

    @Autowired
    ArtCollectService artCollectService;

    /**
     * 添加
     * @param artCollect
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Result add(ArtCollect artCollect){
        try {
            artCollectService.add(artCollect);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
        }
    }

    /**
     * 删除
     * @param artCollect
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(ArtCollect artCollect){
        try {
            artCollectService.delete(artCollect);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
        }
    }

    /**
     * 文章收藏数量
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Result count(Integer bid){
        Integer count = artCollectService.count(bid);
        return new Result(StatusCode.SUCCESS, count);
    }

    /**
     * 获取收藏文章
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getArticle", method = RequestMethod.GET)
    public Result getArticle(Integer uid){
        return new Result(StatusCode.SUCCESS, artCollectService.listArticle(uid));
    }


}
