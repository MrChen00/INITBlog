import request from '@/utils/request'

// 登录/注册页面API

// 用户登录验证
export function login(username, password, rememberMe){
  return request({
    url: '/user/login',
    method: 'post',
    params: {
      username,
      password,
      rememberMe
    }
  })
}
// 退出登录
export function logout(){
    return request({
      url: '/user/logout',
      method: 'get'
    })
}

// 检查用户名是否重名
export function checkUserName(username){
  return request({
    url: '/user/checkUserName',
    method: 'get',
    params: {
      username
    }
  })
}
// 用户注册
export function registerUser(username, password){
  return request({
    url: '/user/register',
    method: 'post',
    params: {
      username,
      password
    }
  })
}
// 验证码
export function securityCode(email){
  return request({
    url: '/user/securityCode',
    method: 'get',
    params: {
      email
    }
  })
}

