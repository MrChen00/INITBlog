package com.zhou.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhou.init.dto.ArtCommentDto;
import com.zhou.init.dto.Result;
import com.zhou.init.dto.StatusCode;
import com.zhou.init.enums.MessageTypeEnum;
import com.zhou.init.enums.ResultEnums;
import com.zhou.init.pojo.ArtComment;
import com.zhou.init.pojo.Message;
import com.zhou.init.pojo.UserAccount;
import com.zhou.init.service.ArtCommentService;
import com.zhou.init.service.BlogArticleService;
import com.zhou.init.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ZHOU
 * @create 2019-02-26 16:37
 */
@RestController
@RequestMapping("/comment")
public class ArtCommentController {

    @Autowired
    ArtCommentService artCommentService;

    @Autowired
    BlogArticleService blogArticleService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 添加
     * @param artComment
     * @return
     */    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody ArtComment artComment){
        try {
            artComment.setTime(new Date());
            artCommentService.add(artComment);

            Integer uid =  artComment.getUid();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 这里记得将IP和端口分离出来
                    // 缓存消息
                    Message message = new Message();
                    message.setUid(artComment.getCid());
                    message.setTitle(artComment.getTitle());
                    // 获取消息人
                    Map map = blogArticleService.getByUid(artComment.getCid());
                    message.setUname(map.get("nickname").toString());
                    message.setHportrait(map.get("hportrait").toString());
                    // 评论/回复
                    if(artComment.getPid() != 0){
                        message.setType(MessageTypeEnum.REPLY.getType());
                    }else{
                        message.setType(MessageTypeEnum.ARTICLECOMMENT.getType());
                    }
                    message.setMessage("评论: " + artComment.getContent());
                    message.setBid(artComment.getBid());
                    message.setTime(artComment.getTime());
                    message.setStatus(MessageTypeEnum.UNREAD.getType());

                    // 发送消息
                    stringRedisTemplate.opsForList().leftPush(uid.toString(), JSONObject.toJSON(message).toString());

                    // 消息未读增加
                    stringRedisTemplate.opsForHash().increment("INITBlogUsers", uid.toString(), 1);
                }
            }).start();

            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.ERROR);
        }
    }

    /**
     * 文章ID获取评论
     * @param bid
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(Integer bid){

        // 获取文章评论
        List<ArtComment> artCommentList = artCommentService.getByBid(bid);
        List<ArtCommentDto> artCommentDtos = new ArrayList<ArtCommentDto>();
        for (ArtComment artComment : artCommentList) {
            ArtCommentDto artCommentDto = new ArtCommentDto();
            artCommentDto.setArtComment(artComment);
            // 获取评论人信息
            Map map = userAccountService.getById(artComment.getCid());
            artCommentDto.setNickName(map.get("nickname").toString());
            artCommentDto.setHportrait(map.get("hportrait").toString());
            if(artComment.getId() != 0){
                // 判断是否有回复评论
                Integer count = artCommentService.countByPid(artComment.getId());
                if(count != 0){
                    artCommentDto.setCountReply(count);
                    artCommentDto.setReply(true);
                }
            }
            // 添加到集合中
            artCommentDtos.add(artCommentDto);
        }
        return new Result(StatusCode.SUCCESS, artCommentDtos);
    }

    /**
     * 获取回复评论
     * @param pid
     * @return
     */
    @RequestMapping(value = "/getReply", method = RequestMethod.GET)
    public Result getReply(long pid){
        List<ArtComment> artCommentList = artCommentService.listByPid(pid);
        List<ArtCommentDto> artCommentDtos = new ArrayList<ArtCommentDto>();
        // 获取回复评论信息
        for (ArtComment artComment : artCommentList){
            ArtCommentDto artCommentDto = new ArtCommentDto();
            artCommentDto.setArtComment(artComment);
            Map map = userAccountService.getById(artComment.getCid());
            artCommentDto.setNickName(map.get("nickname").toString());
            artCommentDto.setHportrait(map.get("hportrait").toString());
            if(artComment.getId() != 0){
                // 判断是否有回复评论
                Integer count = artCommentService.countByPid(artComment.getId());
                if(count != 0){
                    artCommentDto.setCountReply(count);
                    artCommentDto.setReply(true);
                }
            }
            // 添加到集合中
            artCommentDtos.add(artCommentDto);
        }
        return new Result(StatusCode.SUCCESS, artCommentDtos);
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(long id){
        try{
            artCommentService.delete(id);
            return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            return new Result(StatusCode.ERROR, ResultEnums.INNER_ERROR);
        }
    }

}
