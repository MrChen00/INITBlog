package com.zhou.init.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.zhou.init.config.AliyunOSSConfig;
import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.MultipartFileTS;
import com.zhou.init.service.BlogArticleService;
import com.zhou.init.utils.PhotoCompression;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * aliyunOSS
 * @author ZHOU
 * @create 2019-02-17 20:09
 */
@RestController
@RequestMapping("/aliyunoss")
public class UploadOSSController {

    @Autowired
    AliyunOSSConfig aliyunOSSConfig;

    @Autowired
    BlogArticleService blogArticleService;

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile multipartFile, String folder){
         return aliyunOSSConfig.upload(multipartFile, folder);
    }

    /**
     * 多文件上传
     *      主要用于文章图片的上传
     * Map<String, Multpart>
     *
     * 问题解析：由于前台数据是以键值对的集合存在的, 所以使用 MultipartRequest 具体看源码.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadOne", method = RequestMethod.POST)
    public Result uploadOne(MultipartRequest request){
        // 上传完之后的文件数据
        List<Object> obj = new ArrayList<Object>();
        try{
            // 获取文件数据
            Map<String, MultipartFile> filesMap = request.getFileMap();
            Set<String> keySet = filesMap.keySet();
            // 遍历键获取
            for(String key: keySet){
                MultipartFile file = filesMap.get(key);

                // 压缩
                InputStream inputStream = PhotoCompression.getInputStream(file, null);
                String fileName = file.getOriginalFilename();

                // 调用上传方法
                String fileUrl = aliyunOSSConfig.uploadByFileAndFolder(inputStream, "init-blog/image/article/"+  MultipartFileTS.getInstance().getId() +"/", fileName);
                if(fileUrl != null){
                    Map<String, String> map = new HashMap<String, String>();
                    // 存储图片信息
                    map.put("key", key);
                    map.put("url", fileUrl);
                    // 添加到返回数据中
                    obj.add(map);
                }else{
                    // 跳出循环, 不再往下执行, 直接报错.
                    break;
                }
            }
            //
            return new Result(StatusCode.SUCCESS, obj);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(String fileUrl){
        String deleteUrl = aliyunOSSConfig.getFileRelativePath(fileUrl);
        // 默认初始化
        OSSClient ossClient = aliyunOSSConfig.getOSSClient();
        try{
            // 获取存储空间
            String bucketName = aliyunOSSConfig.getBucketName();
            // 删除
            ossClient.deleteObject(bucketName, deleteUrl);
            // 关闭实例
            ossClient.shutdown();
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, "删除失败");
        }
    }

    /**
     * 文章封面修改空处理
     * @param file
     * @param url
     */
    @RequestMapping(value = "/updatess", method = RequestMethod.POST)
    public void updatess(MultipartFile file, String url){ }
    /**
     * 文章封面修改处理
     * @param file
     * @param url
     */
    @RequestMapping(value = "/updateUrl")
    public Result updateUrl(MultipartFile file, String url){
        try {
            String updateFileUrl = "init-blog/image/article/" + url + "/";

            // 压缩
            InputStream inputStream = PhotoCompression.getInputStream(file, null);
            String fileName = file.getOriginalFilename();

            // 文件上传
            String fileUrl = aliyunOSSConfig.uploadByFileAndFolder(inputStream, updateFileUrl, fileName);
            // 数据库修改
            blogArticleService.updateCover(Integer.parseInt(url.toString()), fileUrl);
            return new Result(StatusCode.SUCCESS, fileUrl);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 更新/替换
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestParam("file") MultipartFile file, String updateFileUrl){
        System.out.println(file.getOriginalFilename());
        String updateUrl = aliyunOSSConfig.getFileRelativePath(updateFileUrl);
        String bucketName = aliyunOSSConfig.getBucketName();
        // 创建实例
        OSSClient ossClient = aliyunOSSConfig.getOSSClient();
        try{
            // 获取图片输出流
            InputStream inputStream = file.getInputStream();
            // 对象请求头
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            // 文件格式头
            String contentType = getContentType(updateUrl);
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentDisposition("inline;filename=" + updateUrl);
            // 会替换该url 的 文件
            ossClient.putObject(bucketName, updateUrl, inputStream, objectMetadata);
            ossClient.shutdown();
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, "更新失败");
        }
    }

    /**
     * 获取文件格式头
     */
    public String getContentType(String fileName){
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        // 判断文件类型请求头
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png"
                .equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        // 视频文件的上传等
        return "image/jpeg";
    }

}
