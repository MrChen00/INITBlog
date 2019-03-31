// 个人首页
<template>
    <div>
        <el-row :gutter="20"  style="padding: 8px; margin-top: -20px">
            <el-col :sm="5" :xs="22" :offset="1" style="padding: 0px" >
                <div style="margin-top: 15px">
                    <el-card shadow="always" style="border-radius: 30px;">
                        <div>
                            <div style="width: 32%; float: left">
                                <img :src="user.hportrait" class="hportrait" />
                            </div>
                            <div style="width: 68%; float: left">
                                  <el-button  size="small" 
                                    @click="addFollow"
                                    v-if="followButton"
                                    type="primary" icon="el-icon-plus" round
                                    style="float:right;margin-right: 30px; margin-top: 4px">
                                        关注</el-button>
                                <h3 style="line-height: 3px">{{ user.nickname }}</h3>
                              
                                <p style="padding-top: 5px">关注: {{ follow.follow }} 粉丝: {{ follow.like }} 浏览: {{ profile.upageView }} </p>
                            </div>
                            <div>
                                <el-tag class="typeTag" 
                                    type="danger" size="small" v-for="(type) in articleType" :key="type.id"
                                    >
                                    <span @click="articleTypes(type.id)">{{ type.name }}</span>
                                </el-tag>
                            </div>
                        </div>
                    </el-card>
                        <div style="margin-top: 15px">
                            <el-card shadow="always" style="border-radius: 30px;">
                                <h4>其他信息</h4>
                                <p>姓名: {{ profile.realName }}</p>
                                <p>性别: {{ profile.sex }}</p>
                                <p>地区: 暂时没有</p>
                                <p>职位：{{ profile.post }}</p>
                            </el-card>
                        </div>
                </div>
            </el-col>
            <el-col :sm="12">
                <div style="margin-top: 15px">
                    <el-card shadow="always" style="border-radius: 30px;">

                            <h3 v-if="articles == ''" 
                                style="text-align:center">
                                该分类暂时没有文章哦!
                            </h3>

                            <div v-for="(article) in articles" :key="article.id" shadow="always" 
                                 class="articleDetail">
                                    <h4 @click="articleDetail(article.uid, article.id)">{{ article.title }}</h4>
                                    <p style="font-size: 13px">{{ article.contentShort }}</p>
                                    <img :src="article.cover"/>
                                    <div style="height: 20px">
                                        <div style="width: 50%; float:left">
                                            <span style="font-size: 12px">{{ article.publishTime }}</span>
                                        </div>
                                        <div style="width: 48%; float:left; text-align: right;">
                                            <span style="font-size: 14px"><i class="el-icon-caret-top"></i> &nbsp;{{ article.likes }}</span>
                                            <span style="font-size: 14px"><i class="el-icon-view"></i>&nbsp; {{ article.pageViews }}</span>
                                        </div>
                                    </div>
                            </div>
                    </el-card>
                </div>
            </el-col>
            <el-col :sm="5" >
                <div style="margin-top: 15px">
                    <el-card shadow="always" style="border-radius: 30px;">
                        <h4>推荐内容</h4>
                        <p style="font-size: 150px; color: red; text-align:center; margin:0px">❤</p>
                    </el-card>
                </div>
            </el-col>
        </el-row>
    </div>    
</template>

<script>

import { getUser } from '@/api/home'
import { getProfile, updateProfile } from '@/api/user'
import { listType, listByTid } from '@/api/article'
import { countFollow, countLike, addFollow, deleteFollow, checkFollow } from '@/api/follow'


export default {
    name: 'Users',
    data(){
        return{
            // 当前登录用户
            id: this.$store.getters.user,
            
            uid: this.$route.params.id,
            // 用户信息
            user: '',
            // 基本信息
            profile: {},
            // 文章分类
            articleType: [],
            // 存储文章
            articles: [],
            // 关注信息
            follow: {},
            // 关注按钮
            followButton: false
        }
    },
    methods: {
        // 获取用户信息
        setUser(){
            getUser(this.uid).then((resp) => {
                // 这里捕捉错误进行跳转
                this.user = resp.data.data
            })
            countFollow(this.uid).then(resp => {
                this.follow.follow = resp.data.data
            })
            countLike(this.uid).then(resp => {
                this.follow.like = resp.data.data
            })
        },
        // 获取基本信息
        setProfile(){
            getProfile(this.uid).then(resp => {
                this.profile = resp.data.data
            })
             const profiles = {
                uid: this.uid,
                upageView: 1
            }
            // 修改主页浏览量
            updateProfile(profiles)
        },
        // 获取文章分类和初始化文章
        setArticles(){
             listType(this.uid).then(resp => {
                this.articleType = resp.data.data
                listByTid(this.articleType[0].id).then(resps => {
                    this.articles = resps.data.data
                })
            })
        },
        // 文章详情
        articleDetail(uid, id){
            this.$router.push('/'+ uid +'/article/' + id);
        },
        // 查看分类文章
        articleTypes(id){
            listByTid(id).then(resp => {
                this.articles = resp.data.data
            })
        },
        // 关注用户
        addFollow(){
            if(!this.id){
                this.$notify.error({
                    title: '错误',
                    message: '您当前未登录, 请登录后关注!'
                });
            }else{
                addFollow(this.id, this.uid).then(resp => {
                    this.$notify({
                        title: '成功',
                        message: '关注成功',
                        type: 'success'
                    });
                    this.followButton = false
                    this.follow.like ++
                })
            }
        }
    },
    mounted(){
       
        this.setUser()
        this.setProfile()
        this.setArticles()
        // 是否关注
        if(this.id){
            if(this.id == this.uid){
                this.followButton = false
            }else{
                // 是否已关注
                checkFollow(this.id, this.uid).then(resp => {
                    this.followButton = resp.data.data
                })
            }
        }else{
            this.followButton = true
        }
    }
}
</script>

<style lang="scss" scoped>
.typeTag{
    margin:5px; 
    margin-left: 7px;
    &:hover{
        cursor: pointer;
        background-color:azure
    }
}
// 文章样式
.articleDetail{
    border-bottom: 1px solid rgba(0, 0, 0, 0.1); 
    width: 100%; 
    padding-bottom: 18px; 
    padding-left: 5px;
    h4:hover{
        cursor: pointer;
        color:cornflowerblue;
    }
    // 封面
    img{
        border-radius: 20px; 
        max-width: 200px; 
        max-height: 160px;
    }
}
.hportrait{
    border-radius: 50%; 
    width: 70px; 
    height: 70px;
}

</style>

