package com.zhou.init.controller;

import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.AccAttention;
import com.zhou.init.service.AccAttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHOU
 * @create 2019-03-08 9:48
 */
@RestController
@RequestMapping("/follow")
public class AccAttentionController {

    @Autowired
    AccAttentionService accAttentionService;

    /**
     * 添加关注
     * @param accAttention
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Result add(AccAttention accAttention){
        try{
            accAttentionService.add(accAttention);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 取消关注
     * @param accAttention
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(AccAttention accAttention){
        try{
            accAttentionService.delete(accAttention);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 关注数量
     * @param uid
     * @return
     */
    @RequestMapping(value = "/countFollow", method = RequestMethod.GET)
    public Result countFollow(Integer uid){
        return new Result(StatusCode.SUCCESS, accAttentionService.countFollow(uid));
    }

    /**
     * 粉丝数量
     * @param uid
     * @return
     */
    @RequestMapping(value = "/countLike", method = RequestMethod.GET)
    public Result countLike(Integer uid){
        return new Result(StatusCode.SUCCESS, accAttentionService.countLike(uid));
    }

    /**
     * 是否已关注
     * @param accAttention
     * @return
     */
    @RequestMapping(value = "/checkFollow", method = RequestMethod.GET)
    public Result checkFollow(AccAttention accAttention){
        return new Result(StatusCode.SUCCESS, accAttentionService.checkFollow(accAttention));
    }

    /**
     * 获取所有关注
     * @param id
     * @return
     */
    @RequestMapping(value = "/listFollow", method = RequestMethod.GET)
    public Result listFollow(Integer id){
        return new Result(StatusCode.SUCCESS, accAttentionService.listFollow(id));
    }

    @RequestMapping(value = "/listLike", method = RequestMethod.GET)
    public Result listLike(Integer id){
        return new Result(StatusCode.SUCCESS, accAttentionService.listLike(id));
    }

}
