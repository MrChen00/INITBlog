// 用户 Vuex 操作
import { login } from '@/api/login'
import { setToken } from '@/utils/cookie'

const user = {
  state: {
    user: ''
  },
  mutations: {
    SET_USER: (state, user) => {
      state.user = user
    }
  },
  actions: {
    // 登录
    Login({ commit }, loginForm) {
      const username = loginForm.username.trim()
      return new Promise((resolve, reject) => {
        login(username, loginForm.password, loginForm.rememberMe).then(response => {
          if (response.data.code === 20000) {
            // 设置用户名到Vuex中
            commit('SET_USER', response.data.data);
            // 存储到 Cookie 过时时间按秒计算
            setToken("token", response.data.data, 30000)
            // 成功回调
            resolve()
          }else{
            // 抛出错误信息
            reject(response.data.data)
          }
        })
      })
    }
  }
}

export default user

