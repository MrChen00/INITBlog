package com.zhou.init.controller;

import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.AccProfile;
import com.zhou.init.service.AccProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHOU
 * @create 2019-02-15 16:34
 */
@RestController
@RequestMapping("/user/profile")
public class AccProfileController {

    @Autowired
    AccProfileService accProfileService;

    /**
     * 添加
     * @param id
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(Integer id){
        try {
            accProfileService.add(id);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
        }
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody  AccProfile accProfile){
        try {
            accProfileService.update(accProfile);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 用户ID获取
     * @param uid
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result get(Integer uid){
        return new Result(StatusCode.SUCCESS, accProfileService.get(uid));
    }


}
