package com.zhou.init.controller;

import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.MultipartFileTS;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 暂存控制器
 *      暂时只用于保存文章封面
 * @author ZHOU
 * @create 2019-02-21 10:25
 */
@RestController
@RequestMapping("/multipartFileTS")
public class MultipartFileTSController {
    /**
     * 添加文件
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestParam("file") MultipartFile multipartFile){
        // 暂存文件
        try{
            Map<String, InputStream> map = MultipartFileTS.getInstance().getMapFile();
            map.put(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
            MultipartFileTS.getInstance().setMapFile(map);
            System.out.println(map.size());
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestParam("file") MultipartFile multipartFile){
        try{
            Map<String, InputStream> map = MultipartFileTS.getInstance().getMapFile();
            map.remove(multipartFile.getOriginalFilename());
            MultipartFileTS.getInstance().setMapFile(map);
            System.out.println(map.size());
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }
}
