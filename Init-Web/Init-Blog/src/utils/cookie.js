import Vue from 'Vue'
import Cookies from 'vue-cookies'
Vue.use(Cookies)

// 获取
export function getToken(TokenKey){
    return Cookies.get(TokenKey)
}
// 设置
export function setToken(TokenKey, token, expireTime){
    return Cookies.set(TokenKey, token, expireTime)
}
// 删除
export function removeToken(TokenKey) {
    return Cookies.remove(TokenKey)
}
