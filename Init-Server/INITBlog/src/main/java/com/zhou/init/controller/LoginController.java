package com.zhou.init.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhou.init.config.AliyunOSSConfig;
import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.UserAccount;
import com.zhou.init.service.AccProfileService;
import com.zhou.init.service.UserAccountService;
import com.zhou.init.utils.PhotoCompression;
import com.zhou.init.utils.SendEmail;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录注册相关
 * @author ZHOU
 * @create 2019-01-26 21:56
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    SendEmail sendEmail;

    @Autowired
    AliyunOSSConfig aliyunOSSConfig;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
//    @PostMapping("/login")
//    public Result login(String username, String password){
//        UserAccount userLogin = null;
//        try {
//            userLogin = new UserAccount();
//            userLogin.setEmail(username);
//            userLogin.setPassword(password);
//            // 登录
//            userLogin = userAccountService.getUserLogin(userLogin);
//            if(userLogin == null) {
//                return new Result(StatusCode.LOGIN_ERROR, ResultEnums.LOGIN_ERROR);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
//        }
//        return new Result(StatusCode.SUCCESS, userLogin);
//    }

    /**
     * 用户名是否重名
     */
    @GetMapping("/checkUserName")
    public Result checkUserName(String username){
        Boolean bear = userAccountService.countUserName(username);
        if(!bear){
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
        return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Result register(String username, String password){
        try {
            // 密码加密 MD5
            String hashAlgorithmName = "MD5";
            // 密码
            Object credentials = password;
            // salt
            Object salt = ByteSource.Util.bytes(username);
            // 过滤次数
            int hashIterations = 1024;
            // 加密
            Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);

            UserAccount user = new UserAccount();
            user.setEmail(username);
            user.setPassword(result.toString());
            userAccountService.addUserAccount(user);

            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
        }
    }

    /**
     * 发送邮件
     * @param email
     * @return
     */
    @GetMapping("/securityCode")
    public Result getSecurityCode(String email){
        String securityCode = null;
        try{
            securityCode = sendEmail.SecurityCode(email, " INIT Blog 注册", " 欢迎您("+ email +")注册INIT Blog博客网站, 期待您能我一起并肩同行, 创造更好的未来. ");
            System.out.println("用户验证码: " + securityCode);
            return new Result(StatusCode.SUCCESS, securityCode);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }


    @PostMapping("/login")
    public Result loginShiro(String username, String password,
                             String rememberMe){
        if(username != null && password != null){
            Subject subject = SecurityUtils.getSubject();
            // 认证
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // 判断记住我
            if(rememberMe != null){
                if(rememberMe.equals("true")){
                    token.setRememberMe(true);
                }else{
                    token.setRememberMe(false);
                }
            }else{
                token.setRememberMe(false);
            }

            try{
                // 登录
                UserAccount userAccount = userAccountService.getByName(token.getUsername());
                subject.login(token);
                System.out.println("用户是否登录： " + subject.isAuthenticated());
                System.out.println("用户是否记住我： " + subject.isRemembered());
                return new Result(StatusCode.SUCCESS, userAccount.getId());
            }catch (UnknownAccountException e){
                e.printStackTrace();
                return new Result(StatusCode.ERROR, ResultEnums.LOGIN_UNKNOWN);
            }catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                return new Result(StatusCode.ERROR, ResultEnums.LOGIN_ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
            }
        }else{
            return new Result(StatusCode.ERROR, ResultEnums.INPUT_ERROR);
        }
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        System.out.println(subject.isAuthenticated());
        return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
    }


    /**
     * 修改基本信息
     *        昵称, 头像, 电话, 密码
     * @param map
     * @return
     */
    @RequestMapping(value = "/updateBasic", method = RequestMethod.POST)
    public Result updateBasic(@RequestBody  Map map){
        try {
            // 密码修改需要加密
            userAccountService.updateBasic(map);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 上传头像
     * @param file
     * @param id
     * @return
     */
    @RequestMapping(value = "/uploadHportrait", method =  RequestMethod.POST)
    public Result uploadHportrait(@RequestParam("file") MultipartFile file, @RequestParam(required = false) Integer id){
        try {

            // 压缩
            InputStream inputStream = PhotoCompression.getInputStream(file, null);

            Map map = new HashMap();
            map.put("id", id);

            // 历史头像把
            String fileName = file.getOriginalFilename();
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));

            // 获取上传URL
            String url = (String) aliyunOSSConfig.uploadByFileAndFolder(inputStream, "init-blog/image/user/" + id + "/", id.toString() + fileExtension );
            map.put("hportrait", url);
            // 执行数据库修改
            userAccountService.updateBasic(map);
            return new Result(StatusCode.SUCCESS, url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

}
