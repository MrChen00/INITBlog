
<!-- 登录页面: -->
  <!--1. 登录-->
  <!--2. 注册-->

<template>
  <!-- 登录 -->
  <div class="login-container">
    <el-row>
      <el-col :xs="0" :sm="13" >
        <div style="margin: 40px; margin-left: 200px">
          <img src="http://init-blog-oss.oss-cn-beijing.aliyuncs.com/init-blog/image/gonggao/1.JPG" class="gongGao" />
        </div>
      </el-col>
      <el-col :xs="20" :sm="5" :offset="2" >
          <div style="margin-top: 60px; ">
            <h2>INIT 登录</h2>
              <div >
                <el-form :model="loginForm" ref="loginForm" status-icon :rules="loginRules" class="login-form">
                  <el-form-item label="邮箱名" prop="username">
                    <el-input v-model="loginForm.username"  placeholder="请输入邮箱"></el-input>
                  </el-form-item>
                  <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
                  </el-form-item>
                  <el-form-item label="记住密码">
                    <el-switch
                      v-model="loginForm.rememberMe"
                      active-text="记住密码">
                    </el-switch>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="checkLogin()" style="width: 100%">登录</el-button>
                  </el-form-item>
                </el-form>
              </div>
          </div>
        </el-col>
    </el-row>
  </div>

</template>

<script>
// 登录 api
import { login } from "@/api/login";

export default{
  name: "login",
  data(){
    // 用户名验证
    var checkUserName = (rule, value, callback) => {
      if(value === ''){
        callback(new Error('请输入用户名'));
      } else {
        if(this.loginForm.username !== ''){
          this.$refs.loginForm.validateField('checkUserName');
        }
        callback();
      }
    };

    // 密码验证
    var checkPassword = (rule, value, callback) => {
      if(value === ''){
        callback(new Error('请输入密码'));
      } else if (value.length < 6) {
        callback(new Error('密码必须大于6位!'));
      } else{
         callback();
      }
    };

    return {
      // 记住密码
      loginForm: {
        username: '',
        password: '',
        rememberMe: false
      },
      // 表单格式 验证绑定
      loginRules: {
        username: [
          { validator: checkUserName, trigger: 'blur' , request: true }
        ],
        password: [
          { validator: checkPassword, trigger: 'blur' , request: true }
        ]
      }
    };
  },

  methods: {
    // 登录
    checkLogin() {
      // 表单验证
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          // 异步调用 vuex Login方法
          this.$store.dispatch('Login', this.loginForm).then(() => {
            // 这里需要优化的地方
            // 1. 记录登录主机的IP地址, 进行经常的登录IP的检测, 如果IP地址改变较大, 则邮箱/发送短信 提示用户即可, 提示修改密码
            // if(this.rememberMe){
            //     localStorage.setItem('user', JSON.stringify(this.$store.getters.user))
            // }
            this.$router.push('/')
          }).catch((resp) => {
                this.$message({
                  showClose: true,
                  message: resp,
                  type: 'error'
                })
          })
        } else {
          // 弹出框错误
          this.$message({
            showClose: true,
            message: '格式错误, 请重新填写!',
            type: 'error'
          })
          return false;
        }
      });
    },
    // // 重置
    // resetForm() {
    //   this.$refs.loginForm.resetFields()
    // }
  },
  computed: {
    fullname: {
      get(){},
      set(){}
    }
  }
}

</script>

<style scoped >
.gongGao{
  width: 610px; 
  height: 480px; 
  border-radius: 20px; 
  box-shadow: 4px 3px 6px #999
}
</style>
