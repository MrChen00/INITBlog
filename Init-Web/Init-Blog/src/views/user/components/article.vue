<template>
    <div>
        <el-row >
            <el-col  :xs="0" :sm="4" :md="4" :offset="3">
                <type></type>
            </el-col>
            <el-col :xs="24" :sm="0" :md="0" :offset="1" >
                <el-tag><router-link to="/user/profile" tag="span">基本信息</router-link></el-tag>
                <el-tag><router-link to="/user/article" tag="span">文章管理</router-link></el-tag>
                <el-tag><router-link to="/user/collect" tag="span">我的收藏</router-link></el-tag>
                <el-tag><router-link to="/user/message" tag="span">消息和留言</router-link></el-tag>
            </el-col>

        <!-- 文章分类 -->

        <el-col :xs="20" :sm="18" :md="14">
            <div class="articleType">
                <h3>文章分类</h3>
               
                <el-collapse accordion style="width: 265px">
                    <div v-for="(type) in articleType"  :key="type.id"
                    @click="getArticleTypeShort(type.id)">
                        <!-- <el-badge :value="type.id"> -->
                            <el-collapse-item 
                            :title="type.name"
                            :key="type.id"
                            >
                            <div>创建时间  {{ type.createTime }}</div>
                            </el-collapse-item>
                        <!-- </el-badge> -->
                    </div>
                </el-collapse>
            </div>
         </el-col>
        </el-row>
        <el-row >
                <el-col :xs="0" :sm="3">
                    <br/>
                </el-col> 
                <el-col :xs="24" :sm="21">
                    <h3>文章</h3>
                </el-col> 
        </el-row> 
            <el-row  >
                <el-col :xs="0" :sm="3">
                    <br/>
                </el-col> 
                    <el-col  :xs="24" :sm="5"  class="articleShort" 
                        v-for="(article) in articleTypeShort" 
                        :key="article.id" >
                        <transition name="el-fade-in-linear" >
                            <el-card  :body-style="{ padding: '0px' }" v-show="article.id">
                                 <div class="articleStatus" >
                                    <span v-if="article.status === 2">
                                        <img src="@/assets/articleStatus/privacy.png"/>
                                    </span>
                                    <span v-else-if="article.status === 0">
                                        <img src="@/assets/articleStatus/draft.png" />
                                    </span>
                                    <span v-else>
                                        <img src="@/assets/articleStatus/publish.png" />
                                    </span>
                                </div>
                                <img :src="article.cover" class="image" />
                               
                                <div style="padding: 14px" class="articleDetail"  @click="showArticleDetail(article.id)">
                                    <span class="articleTitle">{{ article.title }}</span><br/>
                                    <span class="articleShort">{{ article.contentShort }}</span>
                                    <div class="bottom">
                                        <div style="float:right;margin-bottom: 13px;margin-top: 5px">
                                            <!-- <el-button icon="el-icon-search" circle size="mini"></el-button>
                                            <el-button type="primary" circle size="mini" icon="el-icon-edit"></el-button>
                                            <el-button type="danger" circle size="mini" icon="el-icon-delete" ></el-button> -->
                                            <time class="time">{{ article.publishTime }}</time>
                                        </div>
                                    </div>
                                </div>
                            </el-card>
                            </transition>
                        </el-col>
            </el-row>  
    </div>
</template>

<script>
import type from './type'
import { listType, countByTid, listByTid } from '@/api/article'
import { getToken, setToken, removeToken } from '@/utils/cookie'

export default {
    name: "articles",
    data(){
        return {
            // 文章分类
            articleType: [{}],
            // 分类文章
            articleTypeShort: [{}]
        }
    },
    methods: {
        // 获取文章分类
        setArticleType(){
            listType(this.$store.getters.user).then(response => {
                // console.log(response)
                response.data.data.forEach(function(item, index) {
                    countByTid(item.id).then(result => {
                       item.count = result.data.data
                    })
                })
                this.articleType = response.data.data
            })
        },
        // 获取分类文章信息
        getArticleTypeShort(tid){
            listByTid(tid).then(response => {
                this.articleTypeShort = response.data.data 
            })
        },

        // 文章详情
        showArticleDetail(id){
            // 路由 query 传参方式, 可以多参数传递
            // this.$router.push('/article/detail?id='+ id)
            this.$router.push('/article/detail/' + id)
        }        
    },
    components: {
        type
    },
    created() {
        this.setArticleType()
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    @import "~@/styles/userArticle.scss";
</style>


