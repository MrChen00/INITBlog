// 我的消息
<template>
    <div>
        <el-row :gutter="10">
            <el-col  :xs="0" :sm="4" :md="4" :offset="3">
                <type></type>
            </el-col>
            <el-col :xs="24" :sm="0" :md="0"  :offset="1">
                    <el-tag><router-link to="/user/profile" tag="span">基本信息</router-link></el-tag>
                    <el-tag><router-link to="/user/article" tag="span">文章管理</router-link></el-tag>
                    <el-tag><router-link to="/user/collect" tag="span">我的收藏</router-link></el-tag>
                    <el-tag><router-link to="/user/message" tag="span">消息和留言</router-link></el-tag>
            </el-col>
            <el-col :xs="24" :sm="18" :md="14">
                <el-card shadow="hover" style="border-radius: 30px; margin-top: 15px">
                    <!-- 未读数量 -->
                    <el-badge :value="unreadMessage" :max="99" class="item"></el-badge>
                     <h3 style="margin-top:0px">我的消息</h3> 
                     <div class="messages" >
                        <h4 style="text-align:center" v-if="messages == ''">您暂时没有任何消息!</h4>
                        <div v-for="(item, index) in messages" :key="index"
                            class="message">
                            <div>
                                <p>
                                    <router-link tag="span" :to="'/' + item.uid">
                                        <img :src="item.hportrait"/>
                                        <span style="position: relative; top: -7px; left: 4px">{{ item.uname }}</span>
                                    </router-link>
                                    <span style="float:right;">
                                        <el-button type="text" size="small"
                                            @click="deleteMessages(index)">删除</el-button>
                                        {{ item.type }} 
                                    </span>
                                </p>
                            </div>
                            <div>
                                <el-badge :value="item.status" ></el-badge>
                                <span @click="selectMessage(item.uid, item.bid, index)">
                                    <span>{{ item.title }}</span>
                                    <h5>{{ item.message }}</h5>
                                </span>
                                <p>{{ item.time }}</p>
                            </div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>

import { getMessage, deleteMessage, updateCountMessage, getCountMessage, updateStatus } from '@/api/message'
import type from './type'
import { get } from 'http';

export default {
    data(){
        return{
            // 存储消息
            messages: [],
            // 实时消息
            countMessage: 0,
            // 未读消息
            unreadMessage: 0,
            login: this.$store.getters.user
        }
    },
    methods: {
        // 删除消息
        deleteMessages(index){
            deleteMessage(this.login, index).then(resp => {
                this.get()
                this.$notify({
                    title: '成功',
                    message: '删除成功',
                    type: 'success'
                });
            })
        },
        get(){
            // 获取消息
            getMessage(this.login).then(resp => {
                this.messages = resp.data.data
            })
            // 消息数量
            getCountMessage(this.login).then(resp => {
                this.countMessage = resp.data.data.count
                this.unreadMessage = resp.data.data.unreadCount
            })
            // 修改实时消息为 0
            updateCountMessage(this.login);
        },
        selectMessage(uid, bid, index){
            this.$router.push('/'+ uid + '/article/' + bid);
            // 修改未读
            updateStatus(this.login, index);
            getCountMessage(this.login).then(resp => {
                this.countMessage = resp.data.data.count
                this.unreadMessage = resp.data.data.unreadCount
            })
        }
    },
    mounted(){
        this.get()
    },
    components:{
        type
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    @import "~@/styles/userMessage.scss";
</style>

