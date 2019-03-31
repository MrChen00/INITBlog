// 文章详情
<template>
<div>
 <el-row>
    <el-col :xs="0" :sm="8">
        <br/>
    </el-col>
    <el-col :xs="24" :sm="13"  class="context" >
    <el-card shadow="always">
    <div class="detail-container">
        <!-- query 传参接收 
            <h2>{{ this.$route.query.id }}</h2> -->
        <!-- params 传参接收 
            <h2>{{ this.$route.params.id }}</h2> -->
       
                <div class="articleDetail">
                    <div class="header" >
                        <h2 class="title">{{ articleDetail.title }}</h2>
                        <!-- 简要: -->
                        <!-- 封面: -->
                        <el-tag v-for="(item) in tags" :key="item" size="small" style="margin-left: 3px">
                            {{ item }}
                        </el-tag>
                        <el-button type="text" @click="editArticle">编辑</el-button>
                        <el-button type="text" @click="deleteArticle">删除</el-button>
                    </div>
                    <div class="content">
                        <div id="content"></div>
                    </div>
                    <div class="articleBottom">
                        <div v-if="articleDetail.status == 1">
                            已发布 
                            <span>发布时间: {{ articleDetail.publishTime }}</span>
                        </div>
                        <div v-else-if="articleDetail.status == 0">
                            私密 
                            <span>创建时间: {{ articleDetail.createTime }}</span>
                        </div>
                        <div v-else>
                            草稿 
                            <span>创建时间: {{ articleDetail.createTime }}</span>
                        </div>
                    </div>
                    <!-- <el-popover
                        placement="top-start"
                        title="标题"
                        width="200"
                        trigger="hover"
                        content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">
                        <el-button slot="reference">hover 激活</el-button>
                    </el-popover> -->
                    <div style="margin-top: 15px">
                        浏览 {{ articleDetail.pageViews }} 
                        <span>点赞{{ articleDetail.likes }}</span> 
                        收藏 <i class="el-icon-star-off"></i> 
                    </div> 
                </div>
                </div>
            </el-card>
        </el-col>
    </el-row>  

    <!-- 评论组件 -->
     <el-row>
        <el-col :xs="24" :sm="24" >
            <comment :articleDetail="articleComment" :status="true"></comment>
        </el-col>
     </el-row>
</div>
   
</template>

<script>
import { get, getTag, remove } from '@/api/article'
import { getToken, setToken, removeToken } from '@/utils/cookie'
import  { add, getComment, getReply, deleteComment } from '@/api/comment'
import Vue from "vue"

// 评论组件
import Comment from '@/components/comment'

export default {
    name: "articleDetail",
    data() {
        return{
            articleDetail: {},
            tags: [],
            articleComment: {
            }
        }
    },
    methods: {
        setArticleDetail(){
            get(this.$route.params.id).then(response => {
                var data = response.data

                if(data.data == null){
                    this.$router.push('/error/article')
                    return;
                }

                if(data.code === 20000){
                    
                    this.articleDetail = data.data

                    // 这里获取文章评论
                    const commentss = {
                        id: data.data.id,
                        uid: data.data.uid,
                        title: data.data.title
                    }
                    // 不能直接属性赋值, 需要对象赋值才能刷新子组件的watch事件
                    this.articleComment = commentss;
                    
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
                }else{
                    // 错误跳转
                    this.$message.error('文章错误, 请稍后重试 !');
                }
            })
        },
        // 删除文章
        deleteArticle(){
            this.$confirm('是否删除文章?', '提示', {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning',
            }).then(() => {
                // 删除文章
                remove(this.$route.params.id).then(resp => {
                    if(resp.data.code == 20000){
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.$router.push('/user/article')
                    }else{
                        this.$message({
                            type: 'success',
                            message: '删除失败!'
                        });
                    }
                })
                
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
        },
        // 编辑文章
        editArticle(){
            this.$router.push('/article/edit/' + this.$route.params.id)
        }
    },
    components:{
        Comment
    },
    mounted() {
        // 初始化文章
        this.setArticleDetail()
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
    // query 传参接收
    // created() {
    //     console.log(this.$route.query.id)
    // }
}
</script>

<style rel="stylesheet/scss" lang="scss">
    @import "~@/styles/articleDetail.scss"
</style>

