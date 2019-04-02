<template>
      <div>
        
        <el-row>
          <el-col :xs="0" :sm="3">
              <br/>
          </el-col>
        // 测试表情啊
          <el-col :xs="24" :sm="12">
              <el-carousel :interval="2200" arrow="never"  >
                <el-carousel-item v-for="item in homeImg" :key="item" >
                  <img :src="item" width="100%" height="100%" />
                </el-carousel-item>
              </el-carousel>
              <el-tabs v-model="activeName" style="margin-top: 30px" @tab-click="tabsHandler">
                <!-- 热门文章 -->
                  <el-tab-pane label="热门文章" name="article">
                        <el-card shadow="hover" v-for="item in articleGrab" :key="item.id"
                        class="hotArticle" >
                        <el-row style="width: 100%">
                          <el-col :xs="12" :sm="19">
                              <div>
                                <div @click="showDetail(item.id, item.uid)" style="cursor: pointer">
                                  <h4 class="textOverflow" 
                                    @click="showDetail(item.id, item.uid)">{{ item.title }}</h4>
                                  <p class="textOverflow"  style="font-size: 13px;" >
                                      {{ item.contentShort }}</p>
                                </div>
                                <div @click="showUser(item.uid)" 
                                    style="cursor: pointer; float:left; margin-bottom: 16px;">
                                    <img :src="item.hportrait" 
                                      style="width: 25px; height: 25px; border-radius: 20px; position: relative; top: 15px" />
                                    <span style="font-size: 13px;position: relative; top: 8px ">{{ item.nickname }}</span>
                                </div>
                              </div>
                            </el-col>
                            <el-col :xs="12" :sm="5">
                              <div class="hotArticleImg">
                                <img :src="item.cover" @click="showDetail(item.id, item.uid)" />
                              </div>
                            </el-col>
                        </el-row>
                    </el-card>
                  </el-tab-pane>
                  <!-- 最新资讯 -->
                  <el-tab-pane label="最新资讯" name="itArticle">

                    <div v-for="item in hotItArticle" :key="item.id" 
                      class="itArticle">
                      <!-- <a href="http://www.baidu.com" target="_Blank">百度</a>
                      _Blank是新窗口
                      _Self是自身
                      _Parent是父窗口
                      _Top是顶层窗口 -->
                      <div style="overflow:hidden">
                        <p class="textOverflow"  ><a :href="item.href" target="_Blank" >{{ item.title }}</a></p>
                      </div>
                    </div>

                  </el-tab-pane>
                  <!-- 热门标签 -->
                  <el-tab-pane label="热门标签" name="tag" >
                    <el-tag size="medium" style="margin: 5px" 
                        v-for="(item, index) in tags" :key="index">
                      {{ item }}
                    </el-tag>
                  </el-tab-pane>
                  <!-- 其他 -->
                  <!-- <el-tab-pane label="其他" name="fourth">其他</el-tab-pane> -->
              </el-tabs>
          </el-col>
          <el-col :xs="0" :sm="5" :push="1"  >
            <div>
              <div class="ittop">
                <h4>IT 资讯</h4>
              </div>
              <div v-loading="loading" :style="loadingHeight">
                <div v-for="item in itArticle" :key="item.id" 
                  class="itArticle">
                  <!-- <a href="http://www.baidu.com" target="_Blank">百度</a>
                  _Blank是新窗口
                  _Self是自身
                  _Parent是父窗口
                  _Top是顶层窗口 -->
                  <div style=" overflow:hidden">
                    <p class="textOverflow" >
                      <a :href="item.href" target="_Blank" >{{ item.title }}</a></p>
                  </div>
                </div>
              </div>
              <div class="itbottom">
                <el-button type="primary" round>查看更多</el-button>
              </div>
            </div>
          </el-col>
        </el-row>
        <!-- 热门文章展示测试 -->
        <!-- <div>
          <el-row >
            <el-col :xs="1" :sm="3">
             <br/>
          </el-col>
            <el-col :xs="22" :sm="12">
               
            </el-col>
          </el-row>
        </div> -->
      </div>
</template>

<script>

import { grabById, getItArticle } from '@/api/home'
import { get, listTag } from '@/api/article'

export default {
      name: "home",
        data() {
            return {
                // 主页文章数据
                homeImg: [
                  "http://init-blog-oss.oss-cn-beijing.aliyuncs.com/init-blog/image/home/1.JPG",
                  "http://init-blog-oss.oss-cn-beijing.aliyuncs.com/init-blog/image/home/zhou02.jpg",
                  "http://init-blog-oss.oss-cn-beijing.aliyuncs.com/init-blog/image/home/MEIZU16.jpg",],
                // 推荐文章
                articleGrab: [],
                // IT资讯
                itArticle: [],
                activeName: 'article',
                // IT资讯文章加载
                loading: true,
                loadingHeight: "height: 700px",
                // 热门标签
                tags: [],
                // 热门资讯 现在和IT资讯是一样的
                hotItArticle: []
            }
        },
        methods: {
           getArticle(){
             grabById().then(resp => {
                this.articleGrab = resp.data
             })
           },
           // 显示文章
           showDetail(id, uid){
             this.$router.push('/'+ uid +'/article/' + id)
           },
           showUser(id){
             this.$router.push('/' + id)
           },
           // 选项栏切换加载
           tabsHandler(tab, event){
              const name = tab.paneName
              if(name == 'tag'){
                listTag().then(resp => {
                  this.tags = resp.data.data
                })
              }
              if(name == 'itArticle'){
                  this.hotItArticle = this.itArticle
              }
           }
        },
        mounted() {
          getItArticle().then(resp => {
            this.itArticle = resp.data.data
            // 去除加载和高度
            this.loading = false
            this.loadingHeight = "height: auto"
          })
          this.getArticle()
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss">
  @import '~@/styles/home.scss'
  
</style>
