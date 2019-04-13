package com.zhou.init.controller;

import com.zhou.init.config.AliyunOSSConfig;
import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.BlogArticle;
import com.zhou.init.pojo.MultipartFileTS;
import com.zhou.init.service.ArtTagService;
import com.zhou.init.service.BlogArticleService;
import com.zhou.init.service.SearchService;
import com.zhou.init.utils.PhotoCompression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.*;

/**
 * @author ZHOU
 * @create 2019-02-15 11:03
 */
@RestController
@RequestMapping("/article")
public class BlogArticleController {

    @Autowired
    BlogArticleService blogArticleService;
    @Autowired
    ArtTagService artTagService;
    @Autowired
    AliyunOSSConfig aliyunOSSConfig;
    @Autowired
    SearchService searchService;

    // JSoup爬取img
//    @RequestMapping("/testImg")
//    public Result testImg(@RequestBody String str){
//        Document doc = Jsoup.parse(str);
//        Elements elements = doc.select("img[src]");
//        for (Element element : elements) {
//            System.out.println(element.attr("src"));
//        }
//        return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
//
//    }

    /**
     * 添加文章
     * @param blogArticle
     * @return
     */
    @RequestMapping(value = "/add", method= RequestMethod.POST)
    public Result add(@RequestBody BlogArticle blogArticle){
        // 创建时间
        blogArticle.setCreateTime(new Date());
        // 发布时间
        if(blogArticle.getStatus() == 1){
            blogArticle.setPublishTime(new Date());
        }
        try {
            // 添加成功之后获取文章ID 添加文章标签
            blogArticleService.add(blogArticle);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    setCover(blogArticle.getId());
                }
            }).start();

            // 保存主键/文章文件夹
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("id", blogArticle.getId().toString());

            // 返回文章路径, 并保存文章内容内的图片
            return new Result(StatusCode.SUCCESS, resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 上传文章封面
     * @param id
     */
    public void setCover(Integer id){
        // 文章路径
        String folderUrl = "init-blog/image/article/" + id + "/";

        MultipartFileTS multipartFileTS = MultipartFileTS.getInstance();

        // 存储文章主键
        multipartFileTS.setId(id);

        InputStream inputStream = (InputStream) multipartFileTS.getMapFile().values().toArray()[0];
        String fileName = multipartFileTS.getMapFile().keySet().toArray()[0].toString();

        // 压缩
        inputStream = PhotoCompression.getInputStream(inputStream, fileName);

        // 保存封面
        String fileUrl = aliyunOSSConfig.uploadByFileAndFolder(inputStream, folderUrl, fileName);

        // 设置为空
        Map<String, InputStream> map = multipartFileTS.getMapFile();
        map.clear();
        multipartFileTS.setMapFile(map);

        // 数据库更新
        blogArticleService.updateCover(id, fileUrl);
    }

    /**
     * 修改
     * @param blogArticle
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody BlogArticle blogArticle){
        try{
            blogArticleService.update(blogArticle);
            // 修改ES索引库数据
            searchService.addArticle(blogArticle.getId());
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 查询文章
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Result get(Integer id){
        try {
            BlogArticle blogArticle = blogArticleService.getByID(id);
            return new Result(StatusCode.SUCCESS, blogArticle);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 查询分类文章数量
     */
    @RequestMapping(value = "/countByTid", method = RequestMethod.GET)
    public Result countByTid(Integer tid){
        return new Result(StatusCode.SUCCESS, blogArticleService.countByTid(tid));
    }

    /**
     * 分类文章
     */
    @RequestMapping(value = "/listByTid", method = RequestMethod.GET)
    public Result listByTid(Integer tid){
        return new Result(StatusCode.SUCCESS, blogArticleService.listByTid(tid));
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(Integer id){
        try{
            // 删除数据库
            blogArticleService.remove(id);
            // 删除OSS的上的图片
            String folderUrl = "init-blog/image/article/" + id +"/";
            aliyunOSSConfig.delete(folderUrl);
            return new Result(StatusCode.SUCCESS, "删除成功");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
        }
    }

    /**
     * 内容修改
     *
     * @return
     */
    @RequestMapping(value = "/updateContent", method = RequestMethod.POST)
    public Result updateContent(@RequestBody List<String> list){
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    blogArticleService.updateContentAndMd(Integer.parseInt(list.get(0)), list.get(2), list.get(1));
                    // 保存到ES中
                    searchService.addArticle(Integer.parseInt(list.get(0)));
                    // 如果有图片, 这里就是最后一步了

                    if(MultipartFileTS.getInstance().getId() != null){
                        MultipartFileTS.getInstance().setId(null);
                    }
                }
            }).start();


            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }
    /**
     * 修改文章浏览量
     * @param id
     * @return
     */
    @RequestMapping(value = "/updateViews", method = RequestMethod.GET)
    public Result updateViews(Integer id){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("pageViews", 1);
        blogArticleService.updateByViewsOrLikes(id);
        return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
    }

    /**
     * 添加文章点赞
     * @param bid
     * @return
     */
    @RequestMapping(value = "/updateLike", method = RequestMethod.GET)
    public Result updateLike(Integer bid){
        try {
            blogArticleService.updateLike(bid);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

}
