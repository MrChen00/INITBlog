<template>
<div class="profile-container" style="padding: 6px; ">
    <el-row :gutter="10">
        <el-col  :xs="0" :sm="4" :md="4" :offset="3">
            <type></type>
        </el-col>
         <el-col :xs="24" :sm="0" :md="0" :offset="1">
                <el-tag><router-link to="/user/profile" tag="span">基本信息</router-link></el-tag>
                <el-tag><router-link to="/user/article" tag="span">文章管理</router-link></el-tag>
                <el-tag><router-link to="/user/collect" tag="span">我的收藏</router-link></el-tag>
                <el-tag><router-link to="/user/message" tag="span">消息和留言</router-link></el-tag>
        </el-col>
        <el-col :xs="24" :sm="18" :md="14">
        <div class="profile">
            <el-card shadow="always" class="profile-one" >
                    <div style="text-align: center">
                        <!-- 头像信息/上传 -->
                         <!-- action="http://39.107.82.119:8018/initblog/user/uploadHportrait"
                             action="http://127.0.0.1:8018/initblog/user/uploadHportrait" -->
                        <el-upload
                            class="avatar-uploader"
                            action="http://39.107.82.119:8018/initblog/user/uploadHportrait"
                            :show-file-list="false"
                            :data="uploadData"
                            :on-success="uploadSuccess"
                            :before-upload="beforeUpload">
                            <img  :src="users.hportrait" class="avatar">
                        </el-upload>
                        <!-- <img :src="users.hportrait"/> -->
                        <br/>
                            <div v-if="updateNickName != ''" >
                                <i class="el-icon-circle-close-outline" @click="hanlderName(false)"/>
                                <el-input 
                                style="width: 100px; margin-top: 11.5px" 
                                placeholder="修改昵称" 
                                v-model="updateNickName" />
                                <i @click="hanlderName(true)" 
                                    class="el-icon-circle-check-outline"></i> 
                            </div>
                            <el-tooltip class="item" effect="dark" content="点击修改昵称" placement="bottom">
                                <h2 @click="updateName" v-if="updateNickName == ''"  >{{ users.nickname }}</h2>
                            </el-tooltip>
                    </div>
                    <div class="profile-bottom">
                        <p>
                            <span @click="getFollows">💗 关注</span>&nbsp;{{ follow.follow }}  
                            <span @click="getLikes">😀 粉丝</span>&nbsp;{{ follow.like }}  
                            <span>😆 浏览量</span>&nbsp;{{ proFileForm.upageView }}
                        </p>
                    </div>
            </el-card>
             <transition name="el-zoom-in-top">
                <div class="follow" v-show="!show2">
                    <el-card shadow="always" style="border-radius: 20px">
                            <h4>我的关注</h4>
                            <h4 v-if="follows == ''" style="text-align: center">你当前没有任何关注</h4>
                            <div v-for="item in follows" 
                                    class="borderBottom"
                                    :key="item.id">
                                    <img :src="item.hportrait"  />
                                    <router-link :to="'/' + item.id" tag="span">
                                        {{ item.nickname }}
                                    </router-link>
                                    <el-button size="small" style="float:right"
                                        @click="cancelFollow(item.id)">
                                        取消关注
                                    </el-button>
                            </div>
                    </el-card>
                </div>
             </transition>
                <transition name="el-zoom-in-top">
                    <div class="follow" v-show="show2" >
                        <el-card shadow="always" style="border-radius: 20px">
                                <h4>我的粉丝</h4>
                                <h4 v-if="likes == ''" style="text-align: center">你当前没有任何粉丝</h4>
                                <div v-for="item in likes" 
                                        class="borderBottom"
                                        style="border-bottom: 1px solid rgba(0,0,0,0.1);"
                                        :key="item.id">
                                        <img :src="item.hportrait"  />
                                        <router-link :to="'/' + item.id" tag="span">
                                            {{ item.nickname }}
                                        </router-link>
                                </div>
                        </el-card>
                    </div>
                </transition>
                <div class="profile-basic">
                    <div >
                        <ul >
                            <li><span>姓名:</span> {{ proFileForm.realName }}</li>
                            <li><span>性别:</span> {{ proFileForm.sex }}</li>
                            <li><span>生日:</span> {{ proFileForm.birthday }}</li>
                            <!-- <li><span>地区:</span> {{ proFileForm.region }}</li> -->
                            <li><span>职位:</span> {{ proFileForm.post }}</li>
                            <li><span>个人简介:</span> {{ proFileForm.intro }}</li>
                            <li><el-button @click="proFileFormVisible=true" >修改</el-button></li>
                        </ul>  
                    </div>
                </div>
                    <el-dialog :visible.sync="proFileFormVisible" >
                        <el-form :model="proFileForm" :rules="proFileRules" ref="proFileRef" >
                            <el-form-item label="姓名" prop="realName">
                                <el-input v-model="proFileForm.realName"></el-input>
                            </el-form-item>
                            <el-form-item label="性别" prop="sex">
                                <el-radio-group v-model="proFileForm.sex">
                                    <el-radio label="男"></el-radio>
                                    <el-radio label="女"></el-radio>
                                </el-radio-group>
                            </el-form-item>
                            <el-form-item label="生日" prop="birthday">
                                <el-date-picker
                                    v-model="proFileForm.birthday"
                                    type="date"
                                    format="yyyy 年 MM 月 dd 日">
                                </el-date-picker>   
                            </el-form-item>
                            <!-- <el-form-item label="地区" prop="region">
                                <el-input placeholder="这里本来是三级联动的, 因为现在没有所以使用id" v-model="proFileForm.region" />
                            </el-form-item> -->
                            <el-form-item label="职位" prop="post">
                                <el-input v-model="proFileForm.post" />
                            </el-form-item>
                            <el-form-item label="个人简介" prop="intro">
                                <el-input v-model="proFileForm.intro" />
                            </el-form-item>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="saveProfile" type="primary" >确定</el-button>
                            <el-button @click="cancelProfile">取消</el-button>
                        </div>
                    </el-dialog>
                </div>
        </el-col>
    </el-row>
</div>
</template>

<script>
import type from './type'
import { getProfile, updateProfile, updateBasic } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/cookie'
import { getUser } from '@/api/home'

import { countFollow, countLike, listLike, listFollow, deleteFollow } from '@/api/follow'


export default {
    name: "profile",
    data() {
        return {
            // 基本信息表单
            proFileFormVisible: false,
            // 表单数据
            proFileForm: { 
                uid: '',
                realName: '',
                sex: '',
                birthday: '',
                region: '',
                post: '',
                intro: '',
                upageView: ''
            },
            // 验证 之后有要求再写
            proFileRules: {},
            user: this.$store.getters.user,
            // 用户信息
            users: {},
            // 关注信息
            follow: {
                follow: '',
                like: ''
            },
            // 所有关注信息
            follows: [],
            // 所有粉丝信息
            likes: [],
            show2: false,
            // 修改昵称参数
            updateNickName: '',
            uploadData:{
                id: this.$store.getters.user
            }
        }
    },
    methods: {
        // 获取基本信息
        setProfile(){
            getProfile(this.user).then(response => {
                this.proFileForm = response.data.data
            })
        },
        // 关注/粉丝
        setFollow(){
            countFollow(this.user).then(resp => {
                this.follow.follow = resp.data.data
            })
            countLike(this.user).then(resp => {
                this.follow.like = resp.data.data
            })
            listFollow(this.user).then(resp => {
                this.follows = resp.data.data
            })
        },
        // 修改基本信息
        saveProfile(){
            this.proFileForm.uid = this.user
            updateProfile(this.proFileForm).then(response => {
                if(response.data.code == 20000){
                    this.proFileFormVisible = false      
                    this.$notify({
                        title: '成功',
                        message: '修改成功',
                        type: 'success'
                    });
                }else{
                    this.$notify({
                        title: '失败',
                        message: '修改失败',
                        type: 'error'
                    });
                }
            })
        },
        // 取消修改
        cancelProfile(){
            // 由于数据是双向绑定的, 取消时会将信息实时更新所以刷新
            this.setProfile()
            this.proFileFormVisible = false  
        },
        // 取消关注
        cancelFollow(id){
            deleteFollow(this.user, id).then(resp => {
                this.$notify({
                    title: '成功',
                    message: '取消关注成功',
                    type: 'success'
                });
                this.setFollow()
            })
        },
        // 所有粉丝信息
        getLikes(){
            this.show2 = true
            listLike(this.user).then(resp => {
                this.likes = resp.data.data
            })
        },
        // 所有关注信息
        getFollows(){
            this.show2 = false
            listFollow(this.user).then(resp => {
                this.follows = resp.data.data
            })
        },
        // 修改昵称
        updateName(){
           this.updateNickName = this.users.nickname
        },
        // 是否修改昵称
        hanlderName(bear){
            if(bear){
                const user = {
                    id: this.user,
                    nickName: this.updateNickName
                }
                updateBasic(user).then(resp =>{
                    if(resp.data.code != 20000){
                        this.$notify.error({
                            title: '修改失败',
                            message: '请稍后重试!'
                        });
                    }else{
                         this.users.nickname = this.updateNickName
                         this.$notify({
                            title: '成功',
                            message: '修改成功!',
                            type: 'success'
                        });
                    }
                    this.updateNickName = ''
                })
            }else{
                this.updateNickName = ''
            }
        },
        // 上传成功
        uploadSuccess(res, file){
            this.users.hportrait = res.data
        },
        // 上传之前
        beforeUpload(file){
            const isJPG = file.type === "image/jpeg" || file.type === 'image/png'
            const isLt2M = file.size / 1024 / 1024 < 6
            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG/PNG 格式!')
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 6MB!')
            }
            return isJPG && isLt2M;
        }
    },
    components: {
        type
    },
    mounted() {
        this.setProfile()
        // 初始化用户信息
        getUser(this.user).then(resp => {
            this.users = resp.data.data
            
        })
        this.setFollow()
    },
    created(){
        // 判断用户是否登录
        if(!getToken("token")){
            this.$message({
                showClose: true,
                message: "您当前未登录!",
                type: 'error'
            })
            
            this.$router.push("/")
        }
    }
}
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
    @import "~@/styles/userProfile.scss";
</style>
