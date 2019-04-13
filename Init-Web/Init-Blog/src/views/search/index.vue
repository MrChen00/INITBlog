// 搜索
<template>
    <div>
        <el-row>
            <el-col :xs="0" :sm="3">
                <br/>
            </el-col>
            
            <el-col :xs="24" :sm="18">
                <!-- 搜索框 -->
                <el-row>
                    <el-col :sm="3" :xs="0"><br /></el-col>
                    <el-col :sm="18" :xs="24">
                        <div style="margin-top: 20px">
                            <div style="float:left" class="initblog" >
                                <img  width="130" height="60" src="data:image/png;base64,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" />
                            </div>
                            <div style="float:left; margin-left: 15px; margin-top: 10px; width: 60%">
                                <el-input  placeholder="请输入内容" v-model="searchName" class="input-with-select">
                                    <el-button slot="append" icon="el-icon-search"></el-button>
                                </el-input>
                            </div>
                            <div  class="searchType">
                                <el-select v-model="select" slot="prepend"  placeholder="请选择" 
                                        >
                                        <el-option label="全部" value="All"></el-option>
                                        <el-option label="博客" value="ARTICLE"></el-option>
                                        <el-option label="用户" value="USER"></el-option>
                                        <el-option label="标签" value="TAG"></el-option>
                                </el-select>
                            </div>
                        </div>
                    </el-col>
                </el-row>
                <!-- 搜索内容 -->
                <el-row style="margin-top: 50px">
                    <el-col :sm="3" :xs="0">
                        <br/>
                    </el-col>
                    <el-col :sm="18" :xs="24" >
                        <el-tabs type="card" @tab-click="showArticle(this)" >
                            <el-tab-pane>
                                <span slot="label"> 全部</span>
                                <el-card class="box-card show-article" v-for="item in ArticleData" :key="item.id" 
                                    >
                                    
                                        <div slot="header" class="clearfix" style="height: 10px">
                                        <div style="float:left; width: 80%; margin-top: -18px" class="textTitle">
                                            <router-link :to="'/'+ item.uid +'/article/'+ item.id" tag="p" class="textOverflow"  >{{ item.title }}</router-link >
                                        </div>
                                        <div style="float:left; width: 20%">
                                            <el-button style="float: right; margin-top: -19px" type="text" ><el-tag size="small" type="danger">文章</el-tag></el-button>
                                        </div>
                                        </div>
                                        <router-link :to="'/00/article/'+ item.id" tag="div">
                                            <div class="text item">
                                                <div style="padding-bottom: 10px" >
                                                    <div style="width: 80%; float:left; " class="detail">
                                                        <p style="font-size: 13px; color: #999;" class="textOverflow" >{{ item.contentShort }}</p>
                                                        <div style=" position: relative; bottom: -15px" >
                                                            <!-- 分割标签 -->
                                                            <el-tag size="small"
                                                                    v-for="item in item.tag.split(',')" :key="item.id"
                                                                    style="margin-left: 3px" >
                                                                {{item}}
                                                            </el-tag>
                                                            <span style="float: right" >
                                                                💙 {{ item.likes }}
                                                                👍 {{ item.pageViews }}
                                                            </span>
                                                        </div>
                                                    </div>
                                                    <div style="width: 20%; float:left" class="hportrait">
                                                        <img :src="item.cover" 
                                                           class="cover"
                                                        />
                                                    </div>
                                                
                                            </div>
                                           
                                        </div>
                                    </router-link>
                                </el-card>
                                
                                <el-card class="box-card show-user" v-for="item in UserData" :key="item.id" >
                                    <router-link :to="'/' +item.id" tag="div">
                                        <div>
                                            <div style="width: 30%; float:left">
                                                <img :src="item.hportrait" width="70" height="70" 
                                                    style="border-radius: 50%"/>
                                            </div>
                                              <div style="width: 70%; float:left; margin-top: -10px">
                                                <p style="font-weight: 600">{{ item.nickname }}</p>
                                                <p>关注: {{ item.follow }}
                                                粉丝: {{ item.likes }}</p>
                                                <div style="position: relative;  top: -80px; float:right; "><el-tag size="small" type="danger">用户</el-tag></div>
                                            </div>
                                            
                                        </div>
                                    </router-link>
                                </el-card>
                                <el-card style="margin-top: 15px">
                                    <h4 style="text-align: center">没有您想要的信息?  <a href="https://www.baidu.com"> INITBlog合作公司 </a></h4>
                                </el-card>
                            </el-tab-pane>

                            <!-- 博客 -->
                            <el-tab-pane label="博客" >
                                <el-card class="box-card show-article" v-for="item in ArticleDataPage" :key="item.id" >
                                        <div slot="header" class="clearfix" style="height: 10px">
                                        <div style="float:left; width: 80%; margin-top: -18px" class="textTitle">
                                            <router-link :to="'/'+ item.uid +'/article/'+ item.id" tag="p" class="textOverflow"  >{{ item.title }}</router-link >
                                        </div>
                                        <div style="float:left; width: 20%">
                                            <el-button style="float: right; margin-top: -19px" type="text" ><el-tag size="small" type="danger">文章</el-tag></el-button>
                                        </div>
                                        </div>
                                        <div class="text item">
                                            <div style="padding-bottom: 10px" >
                                                <div style="width: 80%; float:left; " class="detail">
                                                    <p style="font-size: 13px; color: #999;" class="textOverflow" >{{ item.contentShort }}</p>
                                                    <div style=" position: relative; bottom: -15px" >
                                                        <!-- 分割标签 -->
                                                        <el-tag size="small"
                                                                v-for="item in item.tag.split(',')" :key="item.id"
                                                                style="margin-left: 3px" >
                                                            {{item}}
                                                        </el-tag>
                                                        <span style="float: right" >
                                                            💙 {{ item.likes }}
                                                            👍 {{ item.pageViews }}
                                                        </span>
                                                    </div>
                                                </div>
                                                <div style="width: 20%; float:left" class="hportrait">
                                                    <img :src="item.cover" 
                                                        class="cover"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </el-card>
                                    <br/><br/>
                                    <!-- 分页 -->
                                    
                                        <el-row style="margin-top: 15px;" class="pageView">
                                            
                                                <el-col :xs="24" :sm="24">
                                                    <el-pagination
                                                        @current-change="handleCurrentChange"
                                                        :page-size="5"
                                                        layout="total, prev, pager, next, jumper"
                                                        :total="initPageSize">
                                                    </el-pagination>     
                                                </el-col>
                                           
                                        </el-row>
                                    
                            </el-tab-pane>

                            <!-- 用户 -->
                            <el-tab-pane label="用户">
                                <el-card class="box-card show-user" v-for="item in UserData" :key="item.id">
                                    <router-link :to="'/' +item.id" tag="div">
                                        <div>
                                            <div style="width: 30%; float:left">
                                                <img :src="item.hportrait" width="70" height="70" 
                                                    style="border-radius: 50%"/>
                                            </div>
                                            <div style="width: 70%; float:left; margin-top: -10px">
                                                <p style="font-weight: 600">{{ item.nickname }}</p>
                                                <p>关注: {{ item.follow }}
                                                粉丝: {{ item.likes }}</p>
                                                <div style="position: relative;  top: -80px; float:right; "><el-tag size="small" type="danger">用户</el-tag></div>
                                            </div>
                                            
                                        </div>
                                    </router-link>
                                </el-card>
                            </el-tab-pane>
                        </el-tabs>
                    </el-col>
                </el-row>
               
            </el-col>
        </el-row>
    </div>
</template>

<script>
import { query } from '@/api/search'
import Cookies from 'vue-cookies'

export default {
    name: 'search',
    data(){
        return{
            // 搜索关键字
            searchName: sessionStorage.getItem("searchValue") != null ? sessionStorage.getItem("searchValue") : "INIT",
            // 搜索类型
            select: 'All',
            // 当前页
            page: 1,
            // 页大小
            size: 5,
            // 总数量
            initPageSize: 20,
            // 博客数据
            ArticleData: [],
            // 用户数据
            UserData: [],
            // 博客分页数据
            ArticleDataPage: [],
            // 用户分页数据
            UserDataPage: []
        }
    },
    methods: {
        // 修改页号
        handleCurrentChange(val) {
            this.page = val
            this.queryInit(this.searchName)
            
        },
        // 查询
        queryInit(val){
            query(val, this.select, this.page, this.size).then(resp => {
                if(this.select === "All"){
                    this.ArticleData = resp.data.data[0].lists
                    this.UserData = resp.data.data[1].lists
                    this.initPageSize = resp.data.data[0].totalElement
                   
                } else if(this.select === "USER"){
                    this.ArticleData = ''
                    this.UserData = resp.data.data.lists
                    this.initPageSize = resp.data.data.totalElement
                }else{
                    this.UserData = ''
                    this.ArticleData = resp.data.data.lists
                    this.initPageSize = resp.data.data.totalElement
                }
                this.showArticle()
            })
        },
        // 刷新分页数据
        showArticle(){
            this.ArticleDataPage = this.ArticleData
            this.UserDataPage = this.UserData
        }
    },
    mounted() {
        this.queryInit(sessionStorage.getItem("searchValue"))
    },
    watch: {
        searchName: function(val){
            this.queryInit(val)
            // 搜索值存储在sessionStorage中
            sessionStorage.setItem("searchValue", this.searchName, 30000)
        }
    }
}
</script>

<style lang="scss" scope >

 // 文本单行溢出
 .textOverflow{
     overflow:hidden;
     text-overflow:ellipsis;
     white-space:nowrap;
 }
 // 用户框
 .show-user{
    margin-top: 10px;
    width: 40%; 
    float:left; 
    margin-left: 10px;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,0) !important
 }
 // 封面
 .cover{
     width: 145px; 
     height: 100px; 
     float: right
 }
 // 博客框
 .show-article{
     margin-top: 10px; 
     padding-bottom: 20px; 
     box-shadow: 0 2px 12px 0 rgba(0,0,0,0) !important
 }

// 搜索类型
.searchType{
    float:left; 
    margin-left: 20px; 
    margin-top: 10px; 
    width: 18%
}

 @media screen and (max-width: 600px) {
     .textTitle{
         font-size:14px
     }
     .detail{
         width: 100% !important;
         margin-bottom: 7px
     }
     // 封面
     .hportrait{
         width: 0px !important;
         display: none;
     }
     // 分页
     .pageView{
         position: fixed;
         bottom:0px;
         left: 0px;
         width: 100%;
         background-color: #fff;
         padding-left:22px
     }
     // 用户框调整
     .show-user{
         margin-left: 0px !important;
         width: 99% !important
     }
     .initblog{
         display: none
     }
     .searchType{
         width: 25%;
     }
 }

</style>
