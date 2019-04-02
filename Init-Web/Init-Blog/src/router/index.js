import Vue from 'vue'
// 导入 Vue 路由
import Router from 'vue-router'
// @ 符号在 webpack.base.conf.js 中定义别名路径, 指向 src 路径.

// // 主页
// import home from '@/views/home'
// // 登录
// import login from '@/views/login'
// // 注册
// import register from '@/views/register'

// // 文章创建
// import articleCreate from '@/views/article/create'
// // 文章详情
// import articleDetail from '@/views/article/detail'
// // 文章编辑
// import articleEdit from '@/views/article/edit'


// // 用户信息
// import user from '@/views/user'
// // 基本信息
// import profile from '@/views/user/components/profile'
// // 文章管理
// import article from '@/views/user/components/article'
// // 收藏文章
// import collect from '@/views/user/components/collect'
// // 我的消息
// import message from '@/views/user/components/message'

// // 异常处理
// import error from '@/views/error/article/404'

// 搜索
// @/views/search

// // 查看非当前用户的信息

// // 个人首页
// import Users from '@/views/users'
// // 文章
// import UsersArticleDetail from '@/views/users/article'


// 布局
import Layout from '../site/layout/Layout'
// import { create } from 'domain';

// 绑定路由
Vue.use(Router)

// vue 路由管理
export default new Router({
  // 上线设置
  base: '/dist/',
  // 重定向的使用
  // { path: '/', redirect: '/home' }
  routes: [
    {
      // 主页登录注册
      path: '/',
      name: 'home',
      component: Layout,
      children: [{
        path: '/',
        component: () => import('@/views/home')
      },{
        path: '/login',
        name: 'login',
        component: () => import('@/views/login')
      },{
        path: '/register',
        name: 'register',
        component: () => import('@/views/register')
      }]
    },
    {
      // 文章增改查
      path: '/article',
      name: 'article',
      component: Layout,
      children: [{
        path: '/article/create',
        component: () => import('@/views/article/create')
      },{
        // params传参
        path: '/article/detail/:id',
        component: () => import('@/views/article/detail')
      },{
        path: '/article/edit/:id',
        component: () => import('@/views/article/edit')
      }]
    },
    // 用户信息
    {
      path: '/user',
      name: 'user',
      component: Layout,
      children: [
      {
        path: '/user/profile',
        component: () => import('@/views/user/components/profile')
      },{
        path: '/user/article',
        component: () => import('@/views/user/components/article')
      },{
        path: '/user/collect',
        component: () => import('@/views/user/components/collect')
      },{
        path: '/user/message',
        component: () => import('@/views/user/components/message')
      }]
    },{
      // 错误信息
      path: '/error',
      name: 'error',
      component: Layout,
      children: [
        {
          path: '/error/article',
          component: () => import('@/views/error/article/404')
        }
      ]
    },{
      // 外部可见信息
      path: '/users',
      name: 'users',
      component: Layout,
      children: [
        {
          path: '/:uid/article/:id',
          component: () => import('@/views/users/article')
        },{
          path: '/:id',
          component: () => import('@/views/users')
        }
      ]
    },{
      path: '/search',
      name: 'search',
      component: Layout,
      children: [
        {
          path: '/search/init',
          component: () => import('@/views/search')
        }
      ]
    }
  ]
})
