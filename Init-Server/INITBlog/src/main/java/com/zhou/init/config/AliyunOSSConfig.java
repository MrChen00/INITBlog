package com.zhou.init.config;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * 阿里云对象存储配置
 * @author ZHOU
 * @create 2019-02-17 20:56
 */
@Component
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSConfig {

    // 参数信息

    /**
     * 地域节点 记住是外网的
     */
    private  String endpoint;
    /**
     * AccessKey
     * 需要使用RAM访问控制 进行添加并且设置权限 https://help.aliyun.com/document_detail/61950.html?spm=a2c4g.11186623.6.1087.3b762eaaLqWuKn
     */
    private  String accessKeyId;
    private  String accessKeySecret;
    /**
     * 空间名称 有命名规范
     */
    private  String bucketName;

    // OSSClient 初始化加载

    /**
     * 默认参数加载
     */
    public OSSClient getOSSClient(){
        // 初始化实例 OSSClient
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
    /**
     * 自定义参数加载
     */
    public OSSClient getOSSClient(String endpoint, String accessKeyId, String accessKeySecret) {
        // 初始化实例 OSSClient
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    // 上传方法

    /**
     * 上传文件携带文件夹
     */
    public Result upload(@RequestParam("file") MultipartFile multipartFile, String folder){
        // 文件夹 如果没有请为 ""
        // 为空代表文章封面上传
        if(folder == null) {
            folder = "init-blog/image/article/";
        }
        // 创建文件夹直接加 / 即可
        // 文件/文件夹 返回 上传url
        String fileUrl = uploadByFileAndFolder(multipartFile, folder, null);
        if(fileUrl != null){
            return new  Result(StatusCode.SUCCESS, fileUrl);
        }
        return new Result(StatusCode.ERROR, "上传错误");
    }



    /**
     * 上传单个文件 包括文件夹
     * @param obj  上传文件
     * @param folder 文件夹参数
     * @return 上传之后的URL或错误为null
     */
    public String uploadByFileAndFolder(Object obj, String folder, String fileNameParam){
        // 默认初始化
        OSSClient ossClient = getOSSClient();
        try{
            // 获取图片名称
            String fileName = fileNameParam;
            InputStream inputStream = null;
            if(obj instanceof  MultipartFile) {
                MultipartFile multipartFile = (MultipartFile) obj;
                // 获取图片名称
                fileName = multipartFile.getOriginalFilename();
                // 获取图片输出流
                inputStream = multipartFile.getInputStream();
            }else if(obj instanceof  InputStream){
                fileName = fileNameParam;
                inputStream = (InputStream) obj;
            }
            // 对象请求头
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            // 文件格式头
            String contentType = getContentType(fileName);
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 避免重复图片名称 这里需要加上登录用户名的id
            String uuid = UUID.randomUUID().toString().replace("-","").toLowerCase();
            fileName = uuid + fileName;
            // 上传文件
            ossClient.putObject(bucketName, folder + fileName, inputStream, objectMetadata);
            // 关闭实例
            ossClient.shutdown();
            // 返回图片url
            return "https://" + bucketName + "." + endpoint + "/" + folder  + fileName;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * 通过文件夹遍历删除文件
     * @param folderUrl
     */
    public void delete(String folderUrl){
        OSSClient ossClient = getOSSClient();
        // 获取文件集合
        ObjectListing objectListing = ossClient.listObjects(getBucketName(), folderUrl);
        // 获取所有文件描述
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        // 遍历删除
        for (OSSObjectSummary s: sums){
            ossClient.deleteObject(bucketName, s.getKey());
        }
        ossClient.shutdown();
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
        if(".mp4".equalsIgnoreCase(fileExtension)){
            return "video/mp4";
        }
        // 视频文件的上传等
        return "image/jpeg";
    }

    /**
     * 获取文件相对路径
     *      针对OSS的文件
     * @param fileUrl
     * @return
     */
    public String getFileRelativePath(String fileUrl){
        String middleUrl = fileUrl.substring(fileUrl.indexOf("//") + 2, fileUrl.length());
        String updateUrl = middleUrl.substring(middleUrl.indexOf("/") + 1, middleUrl.length());
        return updateUrl;
    }
}
