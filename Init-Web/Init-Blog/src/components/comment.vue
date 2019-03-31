// 文章评论组件
<template>
    <div>
        <!-- 评论 -->
         <el-row :gutter="15">
            <el-col :xs="0" :sm="8">
                <br/>
            </el-col>
            <el-col :xs="24" :sm="13"  >
                <!-- 评论 -->
                <el-card class="box-card">
                    <el-button type="text" @click="commentFlag=!commentFlag">评论</el-button>
                </el-card>
            </el-col>
        </el-row>

        
                <!-- 发表评论 -->
                <el-dialog
                    title="发表评论"
                    :visible.sync="commentFlag"
                    center
                    style="width:100%">
                    <el-input  v-if="commentFlag"  v-model="comment" placeholder="发表您的意见或建议!" >
                        <template slot="append">
                            <el-button @click="publishComments" >确定</el-button>
                        </template>
                    </el-input>
                </el-dialog>
       
    
        <el-row :gutter="15">
                <el-col :xs="0" :sm="8">
                    <br/>
                </el-col>
                <el-col :xs="24" :sm="13"  >
                <!-- 评论 -->
                <el-card class="box-card">
                    <div v-for="comment in comments" :key="comment.id" class="pingLun">
                        <img :src="comment.hportrait"/>
                        
                        <span style="font-size: 14px;">{{ comment.nickName }}: {{ comment.artComment.content }}</span>
                        <span style="color: #999; font-size: 11px; float:right">{{ comment.artComment.time }}</span> 
                        <el-button type="text" @click="publishComment(comment.artComment.id, comment.nickName)">回复</el-button>
                        
                        <el-button v-if="status" type="text" @click="deleteComment(comment.artComment.id)" >删除</el-button>
                        
                        <!-- 判断是否有回复 -->
                        <div v-if="comment.reply">
                            <el-button type="text"  @click="showReply(comment.artComment.id)">查看回复({{ comment.countReply }})</el-button>
                            <div v-for="(replys) in comment.replys" :key="replys" >
                                <span>{{ replys.nickName }} 回复 {{ comment.nickName }}： </span>
                                {{ replys.artComment.content }}

                                <el-button v-if="status"  @click="deleteComment(replys.artComment.id)" type="text">删除</el-button>
                            </div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <!-- 登录 -->
        <el-dialog
            title="登录"
            :visible.sync="centerDialogVisible"
            center>
            <span>邮箱:</span><el-input v-model="userName"></el-input>
            <span>密码:</span><el-input v-model="password"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="checkLogin">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 评论删除 -->
        <el-dialog
            title="提示"
            :visible.sync="deleteDialog"
            width="30%">
            <span>确认删除吗?</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="deleteComments(false)">取 消</el-button>
                <el-button type="primary" @click="deleteComments(true)">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

import { get, getTag, updateViews } from '@/api/article'
import  { add, getComment, getReply, deleteComment } from '@/api/comment'
import Vue from "vue"
import { exists } from 'fs';
import { debug } from 'util';

export default {
    name: 'comment',
    data(){
        return {
            // 评论模块显示
            commentFlag: false,
            // 评论内容
            comment: '',
            // 所有评论信息
            comments: [],
            // 文章回复保存内容
            replyId: '',
            replyName: '',
            // 用户登录
            centerDialogVisible: false,
            userName: '',
            password: '',
            // 文章删除提示框
            deleteDialog: false,
            // 删除评论ID
            deleteId: '',
        }
    },
    props: ['articleDetail','status'],
    methods: {
        // 加载评论 传入文章ID
        setComments(){
            getComment(this.articleDetail.id).then(resp =>{
                // console.log(resp)
                this.comments = resp.data.data
            })
        },
        // 判断是评论还是回复
        publishComment(id, name){
            if(id && name){
                this.replyId = id
                this.replyName = name
                this.commentFlag = true
            }else{
                this.replyId = ''
                this.replyName = ''
            }
        },
        // 发布成功提示
        success(){
            this.$notify({
                title: '成功',
                message: '发布成功',
                type: 'success'
            });
            // 刷新评论
            this.setComments()
        },
        // 评论/回复
        publishComments(){
            // 添加回复
            if(this.replyId && this.replyName){
                this.addReply(this.replyId, this.replyName)
            }else{
                // 添加评论
                const article  = this.articleDetail
                const artComment = {
                    bid: article.id,
                    uid: article.uid,
                    pid: 0,
                    content: this.comment,
                    cid: this.$store.getters.user,
                    title: article.title
                }
                add(artComment).then(resp => {
                    this.success()
                })
                this.commentFlag = false
                this.comment = ''
            }
        },
        // 显示回复
        showReply(pid){
            getReply(pid).then(resp => {
                this.comments.forEach(item => {
                    // 匹配的评论ID
                    if(item.artComment.id === pid){
                         this.$set(item, 'replys', resp.data.data)
                    }
                }) 
            })
        },
        // 添加回复评论
        addReply(id, name){
            // 显示评论模块
            // this.commentFlag = true;
                const article  = this.articleDetail
                const reply = {
                    bid: article.id, // 文章ID
                    uid: article.uid, // 给谁评论ID
                    pid: id, // 回复的ID
                    content: this.comment, // 评论内容
                    cid: this.$store.getters.user, // 评论的用户ID
                    title: article.title // 文章名称
                }
                add(reply).then(resp => {
                    this.success()
                    this.commentFlag = false
                    this.comment = ''
                })                
        },
        // 登录
        checkLogin(){
            const loginForm = {
                username: this.userName,
                password: this.password,
                rememberMe: false
            }
            // 异步调用 Vuex Login方法
            this.$store.dispatch('Login', loginForm).then(() => {
                // 这里需要优化的地方
                // 1. 记录登录主机的IP地址, 进行经常的登录IP的检测, 如果IP地址改变较大, 则邮箱/发送短信 提示用户即可, 提示修改密码
                // if(this.rememberMe){
                //     localStorage.setItem('user', JSON.stringify(this.$store.getters.user))
                // }
                // this.$router.push('/')
                this.centerDialogVisible = false
            }).catch((resp) => {
                this.$message({
                    showClose: true,
                    message: resp,
                    type: 'error'
                })
            })
        },
        // 删除评论 status: true为首评论 false 为回复
        deleteComment(id){
            this.deleteDialog = true;
            this.deleteId = id;
        },
        deleteComments(val){
            this.deleteDialog = false
            if(val){
                if(this.deleteId != ''){
                    deleteComment(this.deleteId).then(resp => {
                    if(resp.data.code === 20000){
                            console.log("删除成功")
                            // 初始化评论
                            this.setComments()
                        }
                    })
                }
            } 
            this.deleteId = ''
        }
    },
    watch: {
        commentFlag: function(val,vale){
            if(val){
                // this.commentFlag  = false
                // 检测用户是否登录
                if(this.$store.getters.user === null){
                    this.centerDialogVisible = true
                }
            }
        },
        // 由于会先初始化子组件,这样就获取不到父组件的评论ID, 所以在此监听文章评论更改
        articleDetail: function(val, value){
            this.setComments()
        }
    }
}
</script>

<style lang="scss">

.pingLun{
    margin-top: 7px;
    border-bottom: 1px solid rgba(0,0,0,.1);
    img{
        width: 25px; 
        height: 25px; 
        border-radius: 20px
    }
}

@media screen and (max-width: 600px) {
    .el-dialog{
        width: 100%
    }
}

</style>

