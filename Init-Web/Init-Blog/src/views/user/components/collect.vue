// 文章收藏
<template>
    <div>
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
                <el-card shadow="hover" style="border-radius: 30px; margin-top: 15px">
                    <h3>我的收藏</h3>   
                    <div v-for="(article) in  collects" :key="article.id">
                        <el-card shadow="hover" class="article">
                            <h4 class="title" @click="articleDetail(article.id)">{{ article.title }}</h4>
                            <p class="contentShort">{{ article.contentShort }}</p>
                            <div class="deleteCollect" @click="deleteCollect(article.id)"><i class="el-icon-delete" ></i></div>
                        </el-card>
                    </div>
                </el-card>
            </el-col>
        </el-row>
                <!-- 删除提示 -->
                <el-dialog
                    title="提示"
                    :visible.sync="dialogVisible"
                    >
                    <span>确认取消收藏吗?</span>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
                        <el-button type="primary" @click="deleteCollects" size="small">确 定</el-button>
                    </span>
                </el-dialog>
    </div>
</template>

<script>
import type from './type'
import { getArticle, deletes  } from '@/api/collect'

export default {
    name: 'Collect',
    data(){
        return {
            // 登录用户ID
            id: this.$store.getters.user,
            // 文章集合
            collects: [],
            dialogVisible: false,
            // 删除暂存
            deleteArticle: ''
        }
    },
    methods: {
        // 获取收藏文章
        setCollects(){
            getArticle(this.id).then(resp => {
                this.collects = resp.data.data
            })
        },
        // 文章详情
        articleDetail(id){
            this.$router.push('/' + this.id + '/article/' + id)
        },
        deleteCollect(id){
            this.deleteArticle = id
            this.dialogVisible = true
        },
        // 删除收藏
        deleteCollects(){
            if(this.deleteArticle != null){
                deletes(this.id, this.deleteArticle).then(resp => {
                    this.setCollects()
                })
            } 
            this.deleteArticle = ''   
            this.dialogVisible = false       
        }
    },
    components:{
        type
    },
    mounted(){
        this.setCollects()
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    @import "~@/styles/userCollect.scss";
</style>

