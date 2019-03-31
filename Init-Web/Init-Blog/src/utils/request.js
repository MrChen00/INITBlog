// 所有请求的基
import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import store from '../store'
import { getToken } from './cookie'

// 创建 axios 实例
const service = axios.create({
  // 路径的前缀
  // baseURL: 'http://127.0.0.1:8018/initblog',
  // 上线地址设置
  baseURL: 'http://39.107.82.119:8018/initblog',
  // 超时时间
  // timeout: 50000
});

// 这里需要进行权限管理, 之后就会补上

// request 请求拦截器
service.interceptors.request.use(
  result => {
    // console.log("请求之前, 检查是否有登录" + store.getters.user)
    if(store.getters.user){
      result.headers['X-User'] = getToken("token")
    }
    result.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
    return result
  },
  error => {
    console.log(error) 
    Promise.reject(error)
  }
)

// response 响应拦截器
service.interceptors.response.use(
  result => {
    const resp = result.data
    if(resp.code != 20000){
        // Message({
        //     message: res.data,
        //     type: 'error',
        //     duration: 5 * 1000
        // })

       // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
            if (resp.code === 50008 || resp.code === 50012 || resp.code === 50014) {
                MessageBox.confirm(
                    '你已被登出，可以取消继续留在该页面，或者重新登录',
                    '确定登出',
                    {
                        confirmButtonText: '重新登录',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                ).then(() => {
                    console.log("清除用户记录, 并回到主页")
                    // store.dispatch('FedLogOut').then(() => {
                    //     location.reload() // 为了重新实例化vue-router对象 避免bug
                    // })
                })
                return Promise.reject('error')
            }
    }

    return result
  },
  error => {
    console.log('err' + error)
    // Message({
    //   message: error.data,
    //   type: 'error',
    //   duration: 5 * 1000
    // })
    return Promise.reject(error)
  }
)

export default service

