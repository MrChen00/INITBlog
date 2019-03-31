package com.zhou.init.controller;

import com.aliyun.oss.OSSClient;
import com.zhou.init.config.AliyunOSSConfig;
import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件上传
 * @author ZHOU
 * @create 2019-02-13 18:36
 */
@RestController
public class UploadController {

    @Autowired
    AliyunOSSConfig aliyunOSSConfig;

    @RequestMapping("/uploadTest")
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {
        try {
//            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
//            String url = "F:\\1\\" + fileName + suffix;
//            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(url));
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

}
