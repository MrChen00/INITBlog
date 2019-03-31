// 外部文章详情
<template >
<div >
    <el-row :gutter="10">
        <el-col :sm="3" :xs="0"><br/></el-col>
        <el-col :xs="24" :sm="5" >
            <div class="user-container">
                <el-card shadow="always">
                    <div class="userNickName">
                        <img :src="user.hportrait" style="float:left" class="hportrait"/>
                        <h4 @click="userDetail" >{{ user.nickname }}</h4>
                        <el-button type="primary" round  icon="el-icon-plus" size="medium" 
                            @click="addFollow"
                            v-if="followButton"
                            style="float:right;margin-right: 15px; margin-top: 15px">
                                关注</el-button>
                    </div>
                    <div class="userFollow">
                        <div>关注: {{ follow.follow }}</div>
                        <div>粉丝: {{ follow.like }}</div> 
                        <div>浏览量: {{ users.upageView }}</div> 
                    </div>
                </el-card>
            </div>
        </el-col>
        <el-col :xs="24" :sm="13" >
            <el-card shadow="always" >
                <div class="detail-container">
                    <div class="articleDetail">
                        <div class="header" >
                            <h2 class="title">{{ articleDetail.title }}</h2>
                            <el-tag v-for="(item) in tags" :key="item" size="small" 
                                style="margin: 3px">
                                {{ item }}
                            </el-tag>
                            <span class="publishTime">{{ articleDetail.publishTime }}</span>
                        </div>
                        <div class="content">
                            <div id="content"></div>
                        </div>
                        浏览 {{ articleDetail.pageViews }} 
                        <span @click="addLikes">点赞</span> {{ articleDetail.likes }}
                        收藏 <i class="el-icon-star-off"  @click="addCollect"></i> 
                    </div>
                </div>
            </el-card>
        </el-col>
    </el-row>
    <el-row>
        <el-col :xs="24" :sm="24" >
            <!-- 评论组件 -->
            <comment :articleDetail="articleComment" :status="false"></comment>
        </el-col>
    </el-row>
</div>
</template>

<script>

// 评论组件
import Comment from '@/components/comment'

import { get, getTag, updateViews, updateLike } from '@/api/article'
import  { add, getComment, getReply } from '@/api/comment'
import Vue from "vue"
import { exists } from 'fs'
import { addCollect, count, deletes } from '@/api/collect'
import { getUser } from '@/api/home'
import { getProfile } from '@/api/user'
import { countFollow, countLike, addFollow, deleteFollow, checkFollow } from '@/api/follow'

export default {
    name: "UsersArticleDetail",
    data() {
        return{
            login: this.$store.getters.user,
            // 文章ID
            id: this.$route.params.id,
            // 用户ID
            uid: this.$route.params.uid,
            // 用户信息
            user: {},
            // 文章信息
            articleDetail: {},
            // 标签信息
            tags: [],
            // 评论组件的传值
            articleComment: {},
            // 文章收藏数量
            countCollect: 0,
            // 用户基本
            users: {},
            // 关注信息
            follow: {},
            // 关注按钮
            followButton: false
        }
    },
    methods: {
        // 获取文章
        setArticleDetail(){
            // console.log(this.$route.params.uid)
            get(this.id).then(response => {
                var data = response.data
                
                // 没有该文章则 404
                if(data.data == null){
                    this.$router.push('/error/article')
                    return;
                }
                if(data.code === 20000){
                    this.articleDetail = data.data
                    // 获取标签
                    getTag(this.articleDetail.id).then(resp => {
                        this.tags = resp.data.data    
                    })
                    // 文章内容设置
                    var MyContent = Vue.extend({
                        template: "<div>" + this.articleDetail.content + "</div>",
                    })
                    var component = new MyContent().$mount();
                    document.getElementById('content').appendChild(component.$el)
                    // 添加数据即可
                    var imgs = document.getElementById('content').querySelectorAll("img");
                    imgs.forEach(function(item, index){
                        item.style.width = "100%"
                        item.style.margin="2px"
                        item.style.maxWidth = "600px"
                        item.style.maxHeight = "700px"
                    })
                    // 修改浏览量 传输到后台过滤器判断IP
                    updateViews(this.id).then(resp => {
                        
                    })
                    // 这里获取文章评论
                    const commentss = {
                        id: this.articleDetail.id,
                        uid: this.articleDetail.uid,
                        title: this.articleDetail.title
                    }
                    // 不能直接属性赋值, 因为子组件会先加载, 需要对象赋值才能刷新子组件的watch事件
                    this.articleComment = commentss;

                }else{
                    // 错误跳转
                    this.$message.error('文章错误, 请稍后重试 !');
                }
            })
        },
        // 获取文章作者信息
        setUser(){
            getProfile(this.uid).then(resp => {
                this.users = resp.data.data
            })
            getUser(this.uid).then(resp => {
                this.user = resp.data.data
            })
            countFollow(this.uid).then(resp => {
                this.follow.follow = resp.data.data
            })
            countLike(this.uid).then(resp => {
                this.follow.like = resp.data.data
            })
        },
        // 收藏文章
        addCollect(){
            addCollect(this.$store.getters.user, this.articleDetail.id).then(resp => {
                this.$notify({
                    title: '收藏成功',
                    message: '文章收藏成功',
                    type: 'success'
                });
                count(this.id).then(resp => {
                    this.countCollect = resp.data.data
                })
            })
        },
        // 用户主页
        userDetail(){
            this.$router.push('/' + this.uid)
        },
        // 关注用户
        addFollow(){
            if(!this.login){
                this.$notify.error({
                    title: '错误',
                    message: '您当前未登录, 请登录后关注!'
                });
            }else{
                addFollow(this.login, this.uid).then(resp => {
                    this.$notify({
                        title: '成功',
                        message: '关注成功',
                        type: 'success'
                    });
                    this.followButton = false
                    this.follow.like ++
                })
            }
        },
        // 文章点赞
        addLikes(){
            updateLike(this.articleDetail.id).then(resp => {
                this.articleDetail.likes +=1
            })
        }
    },
    components:{
        Comment
    },
    mounted() {
        
        // 初始化文章/评论
        this.setArticleDetail()
        // 初始化文章收藏数量
        count(this.id).then(resp => {
            this.countCollect = resp.data.data
        })
        this.setUser()
        if(this.login){
            if(this.login == this.uid){
                this.followButton = false
            }else{
                // 是否已关注
                checkFollow(this.login, this.uid).then(resp => {
                    this.followButton = resp.data.data
                })
            }
        }else{
            this.followButton = true
        }
    }

}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    @import "~@/styles/usersArticle.scss";
</style>

