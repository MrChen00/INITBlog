<template>
        <el-header class="headerMe">
         <el-row style="height: 30px">
          <el-card shadow="always" >
            <el-col :xs="0" :sm="2" ><br/></el-col>
            <el-col :xs="4" :sm="4">
              <div style="width: 100%;" align="center">
                  <img src="@/assets/INIT.png" class="initBlog"/>
              </div>
            </el-col>
            <el-col :xs="0" :sm="15" >
              <div class="router-container" >
                  <span v-if="!checkSearch">
                        <router-link to="/" tag="div" class="router-div">首页</router-link>
                        <router-link to="/" tag="div" class="router-div">IT 资讯</router-link>
                        <router-link to="/" tag="div" class="router-div">标签</router-link>
                        <router-link to="/" tag="div" class="router-div">关于本站</router-link>
                  </span>
                   <!-- 搜索 -->
                    <div v-if="checkSearch" class="checkSearch" >
                        <el-autocomplete
                          popper-class="my-autocomplete"
                          v-model="state3"
                          :fetch-suggestions="querySearch"
                          placeholder="你好"
                          @select="handleSelect"
                          size="small"
                          style="width: 160px; float:right"
                          >
                          <i
                          class="el-icon-search"
                          slot="suffix"
                          @click="handleIconClick" ></i>
                          <!-- 搜索模板 -->
                          <template slot-scope="{ item }">
                              <div v-if="item.type == 'User'">
                                <div class="name" @click="detailUser(item.id)">  {{ item.nickname }}</div>
                              </div>
                              <div v-if="item.type == 'Article'">
                                <div class="name" @click="detailArticle(item.uid, item.id)"> 
                                  <i class="el-icon-tickets"></i> {{ item.title }}</div>
                              </div>
                              <div v-if="item.type == 'Tag'">
                                <div class="name" @click="detailTag(item.bid, item.name)">
                                  <i class="el-icon-circle-check"></i> {{ item.name }}</div>
                              </div>
                          </template>
                        </el-autocomplete>
                    </div>
                  <!-- 用户登录权限 -->
                  <div v-if="checkLog" class="login-container">
                      <div @click="checkSearch=!checkSearch">
                          <i class="el-icon-search"></i>
                          搜索
                      </div>
                      <router-link to="/article/create" tag="div">
                            <i class="el-icon-edit"></i> 写文章
                      </router-link>
                      <router-link to="/user/message" tag="div">
                            <i class="el-icon-bell"></i> 我的消息 
                            <el-badge :value="countMessage" :max="99" style="float:right"></el-badge>
                      </router-link>
                    <el-dropdown >
                     
                    <el-button type="text" size="mini" >
                        <router-link to="/user/profile" tag="span">
                          <div class="hportrait" >
                            <img :src="user.hportrait" />
                          </div>
                          <div class="nickName">
                              {{ user.nickname }}
                          </div>
                        </router-link>
                      </el-button >
                      <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item><router-link to="/article/create" tag="span">写文章</router-link></el-dropdown-item>
                        <el-dropdown-item><router-link to="/user/profile" tag="span">个人中心</router-link></el-dropdown-item>
                        <el-dropdown-item><router-link to="/user/message" tag="span">我的消息</router-link>
                        
                        <el-badge :value="countMessage" :max="99"></el-badge>
                        </el-dropdown-item>
                        <el-dropdown-item ><span @click="logouts">退出登录</span></el-dropdown-item>
                      </el-dropdown-menu>
                    </el-dropdown>
                  </div>
                  <div v-else class="loginRegister">
                      <div @click="checkSearch=!checkSearch"> 
                          <img src="@/assets/home/search.png"/>
                          <div>
                            搜索
                          </div>
                      </div> 
                      <router-link to="/login" tag="div"> 
                          <img src="@/assets/home/login.png"/>
                          <div>
                            登录
                          </div>
                      </router-link> 
                      <router-link to="/register" tag="div">
                          <img src="@/assets/home/register.png"/>
                          <div>
                             注册
                          </div>
                      </router-link>
                  </div>
                  
              </div>
          </el-col>
                <el-col :xs="20" :sm="0" >
                    <!-- 搜索 -->
                    <div v-if="checkSearch" class="checkSearch" >
                        <el-autocomplete
                          popper-class="my-autocomplete"
                          v-model="state3"
                          :fetch-suggestions="querySearch"
                          placeholder="你好"
                          @select="handleSelect"
                          size="small"
                          style="width: 160px; float:right"
                          >
                          <i
                          class="el-icon-search"
                          slot="suffix"
                          @click="handleIconClick" ></i>
                          <!-- 搜索模板 -->
                          <template slot-scope="{ item }">
                              <div v-if="item.type == 'User'">
                                <div class="name" @click="detailUser(item.id)">  {{ item.nickname }}</div>
                              </div>
                              <div v-if="item.type == 'Article'">
                                <div class="name" @click="detailArticle(item.uid, item.id)"> <i class="el-icon-tickets"></i> {{ item.title }}</div>
                              </div>
                              <div v-if="item.type == 'Tag'">
                                <div class="name" @click="detailTag(item.bid, item.name)"><i class="el-icon-circle-check"></i> {{ item.name }}</div>
                              </div>
                          </template>
                        </el-autocomplete>
                    </div>

                    <!-- 小型设备展示 -->
                  <div style="float:right; margin-right: 20px; margin-top: -10px">
                    <el-dropdown>
                      <el-button type="text">
                       <i class="el-icon-menu" style="font-size: 25px"></i>
                      </el-button>
                      <el-dropdown-menu slot="dropdown" style="width: 37%;">
                         <!-- <el-dropdown-item><router-link to="/" tag="div">IT 资讯</router-link></el-dropdown-item>
                        <el-dropdown-item><router-link to="/" tag="div">标签</router-link></el-dropdown-item> -->
                        <el-dropdown-item><router-link to="/" tag="div"> &nbsp;&nbsp;&nbsp;INIT Blog</router-link></el-dropdown-item>

                        <div v-if="checkLog">
                            <el-dropdown-item>
                              <router-link to="/article/create" tag="span"> 
                                  <i class="el-icon-edit"></i>  &nbsp;&nbsp;写文章
                              </router-link>
                            </el-dropdown-item>
                            <el-dropdown-item>
                              <router-link to="/user/profile" tag="span"> 
                                <img src="@/assets/home/login.png" width="15px" height="15px"/>  &nbsp;个人中心
                              </router-link>
                            </el-dropdown-item>
                            <el-dropdown-item>
                              <router-link to="/user/message" tag="span"> 
                                <i class="el-icon-bell"></i> &nbsp;我的消息
                              </router-link>
                            </el-dropdown-item>
                            <el-dropdown-item > 
                              <div @click="checkSearch=!checkSearch"> 
                                <i class="el-icon-search" ></i> 
                                &nbsp;搜索 
                              </div>
                            </el-dropdown-item>
                            <el-dropdown-item> 
                              <i class="el-icon-upload2"></i> 
                              &nbsp;<span @click="logouts">退出登录</span>
                            </el-dropdown-item>
                        </div>
                        <div v-else>
                        <el-dropdown-item > 
                          <i class="el-icon-search" @click="checkSearch=!checkSearch"></i> 
                          &nbsp;搜索
                        </el-dropdown-item>
                        <el-dropdown-item> 
                          <router-link to="/login" tag="div">
                            <img src="@/assets/home/login.png" width="16px" height="16px"/> &nbsp;登录
                          </router-link>
                        </el-dropdown-item>
                        <el-dropdown-item> 
                          <router-link to="/register" tag="div">
                            <img src="@/assets/home/register.png" width="16px" height="16px"/> &nbsp;注册
                          </router-link>
                        </el-dropdown-item>
                        </div>
                      </el-dropdown-menu>
                    </el-dropdown>
                  </div>
                </el-col>
            </el-card>
         </el-row>
        </el-header>
</template>

<script>

import { updateCountMessage, getCountMessage, } from '@/api/message'
import { logout } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/cookie'
import { createECDH } from 'crypto'
import { getUser, listLikeName } from '@/api/home'

    export default {
        name: "Header",
        data() {
          return {
            itemArticle: '',
            login: getToken('token'),
            // 存储推荐信息
            restaurants: [],
            // 文本框实例
            state3: '',
            hportrait: 'src/assets/hportrait.jpg',
            checkLog: false,
            // 登录用户信息
            user: {},
            // 实时消息数量
            countMessage: 0,
            // 是否搜索
            checkSearch: false
          };
        },
        methods: {
          // 退出登录
          logouts(){
            logout().then(resp => {
              removeToken("token")
              this.checkLog = false
              this.$router.push("/")
              // 刷新Vuex
            })
          },
          // 可以理解为文本发生变化时触发
          querySearch(queryString, cb) {
            if(queryString != ''){
              // 查询 在这里查询
              listLikeName(queryString).then(resp => {
                cb(resp.data.data);
              })
            }else{
              const vals = [
                  { "nickname": "请填写用户名/文章名称/标签名!",
                    "type" : 'User'
                  }
                ]
              cb(vals)
            }

           
           
          },
          createFilter(queryString) {
            return (restaurant) => {
              // 过滤内容直到没有该数据
              return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            }
          },
          // 推荐的查询数据
          loadAll() {
            return [
              { "value": "请填写用户名/文章名称/标签名!"}
            ];
          },
          // 推荐列表选中事件
          handleSelect(item) {
             this.state3 = item.title
             this.state3 = item.name
          },
          // 点击查询
          handleIconClick(ev) {
            // console.log(this.state3)
          },
          // 搜索文章详情
          detailArticle(uid, id){
              this.$router.push('/'+ uid + '/article/' + id)
          },
          // 搜索标签详情
          detailTag(id, name){
            this.$alert('该标签搜索模块暂未开启, 请您理解 !', '抱歉', {
                confirmButtonText: '确定'
            });
          },
          // 搜索用户详情
          detailUser(uid){
              this.$router.push('/'+ uid)
          }
        },
        // 模板渲染完后调用该方法
        mounted() {
          // 赋值到定义的数组中
          this.restaurants = this.loadAll()
          // getUser(this.$store.getters.user).then(resp => {
          //     this.user = resp.data.data
          //     this.checkLog = true
          // })
         
        },
        watch: {
          // 监听登录变化实时调整
          '$store.getters.user': function(val, old){
              if(val){
                // 获取用户信息 
                getUser(val).then(resp => {
                    this.user = resp.data.data
                })
                // 获取消息数量
                getCountMessage(val).then(resp => {
                    this.countMessage = resp.data.data.count
                })
                this.checkLog = true
              }
              
          }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "~@/styles/header.scss";
</style>
