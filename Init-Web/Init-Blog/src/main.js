// webpack 程序入口文件, 加载各种公共组件
import Vue from 'vue'

import VueResource from 'vue-resource'
Vue.use(VueResource)

// ElementUI
// http://element-cn.eleme.io/#/zh-CN/component/installation
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css'
Vue.use(ElementUI)

// 文章编辑器
import mavonEditor from 'mavon-editor'
Vue.use(mavonEditor)
import 'mavon-editor/dist/css/index.css'

import App from './App'
import router from './router'
import store from './store'


// 去除生产环境的警告
Vue.config.productionTip = false;
// 设置请求头部 content-type:application/x-www-form-urlencoded
Vue.http.options.emulateJSON = true;

import Cookies from 'vue-cookies'
Vue.use(Cookies)

store.commit("SET_USER", Cookies.get("token"))


new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
