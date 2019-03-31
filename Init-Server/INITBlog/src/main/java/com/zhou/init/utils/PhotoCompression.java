package com.zhou.init.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 图片压缩
 * @author ZHOU
 * @create 2019-02-22 10:56
 */
public class PhotoCompression {

//    private static final String fileFolder = "C://Users//zhou//Desktop//testUpload//";
    // 线上设置
    private static final String fileFolder = "/www/server/uploadFile/";

    /**
     * 获取上传文件
     *      返回压缩后的输入流
     * @param file
     * @return
     */
    public static InputStream getInputStream(Object file, String fileNameParam) {
        // 压缩上传
        try {
            InputStream inputStream = null;
            String fileName = null;

            if(file instanceof  InputStream){
                inputStream = (InputStream) file;
                fileName = fileNameParam;
            }
            if(file instanceof  MultipartFile){
                MultipartFile multipartFile = (MultipartFile) file;
                // 只能在之前生成一个文件名进行拼接, 在压缩完之后在进行上传
                fileName = multipartFile.getOriginalFilename();
                inputStream = multipartFile.getInputStream();
            }

            // 图片压缩暂时只能主页
            File files = new File(fileFolder + fileName);
            // 文件压缩
            Thumbnails.of(inputStream)
                    .scale(1f)
                    .outputQuality(0.4f)
                    .toFile(files);
            InputStream outInputStream = new FileInputStream(files);
            return outInputStream;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
