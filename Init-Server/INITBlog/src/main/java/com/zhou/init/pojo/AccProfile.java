package com.zhou.init.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户基本信息
 * @author ZHOU
 * @create 2019-02-15 16:03
 */
@Data
@NoArgsConstructor
public class AccProfile {

    Integer uid; // 用户ID
    String realName; // 真实姓名
    String sex; // 性别
    Date birthday; // 生日
    Integer region; // 地区
    String post; // 职位
    String intro; // 个人简介
    Integer upageView; // 个人主页浏览量

}
