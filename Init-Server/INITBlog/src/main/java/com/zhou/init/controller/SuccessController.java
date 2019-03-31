package com.zhou.init.controller;

import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZHOU
 * @create 2019-02-23 21:54
 */
@RestController
public class SuccessController {

    @RequestMapping("/")
    public Result home(){
        Subject subject = SecurityUtils.getSubject();
        System.out.println("记住我:" + subject.isRemembered());
        System.out.println("登录:" + subject.isAuthenticated());
//        System.out.println(subject.getPrincipal().toString());
        return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
    }

}
