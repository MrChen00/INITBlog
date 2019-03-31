<template>

  <!-- 注册 -->
  <div class="register-container">
      <el-row>
        <el-col :xs="0" :sm="13" >
          <div style="margin: 40px; margin-left: 200px">
            <img src="http://init-blog-oss.oss-cn-beijing.aliyuncs.com/init-blog/image/gonggao/1.JPG"
               class="gongGao" />
          </div>
        </el-col>
        <el-col :xs="20" :sm="5" :offset="2">
          <div style="margin-top: 40px">
            <h2>INIT 注册</h2>
            <el-form :model="registerForm" ref="registerForm" status-icon :rules="registerRules"  class="register-form">
              <el-form-item label="邮箱名" prop="username" >
                <el-input v-model="registerForm.username"  placeholder="请输入邮箱" size="medium" ></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password" style="margin-top: -10px">
                <el-input type="password" v-model="registerForm.password" placeholder="请输入密码" size="medium"></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="againPassword" style="margin-top: -10px">
                <el-input type="password" placeholder="再次输入密码" v-model="registerForm.againPassword" size="medium"></el-input>
              </el-form-item>
              <el-form-item label="" prop="email">
                <el-button type="success" @click="sendMsg" 
                  size="medium">{{ securityButton }}</el-button>
                <el-input placeholder="邮箱验证码" style="width: 167px" 
                   v-model="securityCode" size="medium"></el-input>
                
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="checkRegister()" style="width: 100%">注册</el-button>
                <!-- <el-button @click="resetForm()">重新填写</el-button> -->
              </el-form-item>
            </el-form>
          </div>
        </el-col>
    </el-row>
  </div>

</template>

<script>

  import { checkUserName, registerUser, securityCode } from "@/api/login"
  import { addProfile }  from '@/api/user'

  export default{
    data(){
      // 邮箱验证
      var checkUsername = (rule, value, callback) => {
        if(value === ''){
          callback(new Error('邮箱名不能为空 ! '))
        }
        else {
          // 邮箱格式验证 正则表达式有点问题...
          // var reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
          // if(!value.test(reg)){
          //   callback(new Error('请输入有效的邮箱 ! '))
          // }else {
            // 邮箱是否注册
              checkUserName(value).then((result) => {
                if (result.data.code === 20001) {
                  callback(new Error('该邮箱名已注册 ! '))
                } else {
                  callback()
                }
              })
          // }
        }
      };

      // 密码验证
      var checkPassword = (rule, value, callback) => {
        if(value === ''){
          callback(new Error('密码不能为空 ! '));
        } else if (value.length < 6) {
          callback(new Error('密码必须大于6位!'));
        } else{
          callback();
        }
      };

      // 确认密码验证
      var checkAgainPassword = (rule, value, callback) => {
        if(value === ''){
          callback(new Error('请确认密码 ! '));
        } else if (value != this.registerForm.password) {
          callback(new Error('密码填写不一致 ! '));
        } else{
          callback();
        }
      };

      return {
        securityButton: '发送验证码',
        securityCode: '',
        sendSecurity: '',
        time: 60,
        interval: '',
        registerForm: {
          username: '',
          password: '',
          againPassword: ''
        },
        // 表单验证绑定
        registerRules: {
          username: [
            { validator: checkUsername, trigger: 'blur' }
          ],
          password: [
            { validator: checkPassword, trigger: 'blur' }
          ],
          againPassword: [
              { validator: checkAgainPassword, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      // 发送验证码
      sendMsg() {
        if(this.registerForm.username != ''){
          // 清空验证码参数
          this.securityCode = ""
          this.sendSecurity = ""
          // 后台发送验证码
          securityCode(this.registerForm.username).then((result) => {
            if(result.data.code === 20001){
              // 暂停计时 设置按钮重新发送
              this.securityButton = "重新发送"
              me.time = 60,
              window.clearInterval(this.interval);
              this.$message({
                showClose: true,
                message: '验证码发送错误, 请稍后重试 ! ',
                type: 'error'
              });
            }else {
              // 成功赋值验证码
              this.sendSecurity = result.data.data
            }
          })

          // 验证码等待时间
          let me = this;
          this.interval = window.setInterval(function () {
            me.securityButton = '剩余 ' + me.time + '秒'
            -- me.time;
            if(me.time < 0){
              me.securityButton = "重新发送";
              me.time = 60,
              window.clearInterval(this.interval);
            }
          }, 1000);
        }else{
          this.$message({
            showClose: true,
            message: '请填写邮箱名!',
            type: 'error'
          });
        }
      },

      // 注册
      checkRegister() {
        // 验证码验证
        if(this.sendSecurity == ''){
          this.$message({
            showClose: true,
            message: '需要发送验证码哦 ! ',
            type: 'error'
          });
          return;
        }else {
          if (this.sendSecurity == this.securityCode) {
            // 注册用户
            registerUser(this.registerForm.username, this.registerForm.password).then((result) => {
              if(result.data.code == 20000){
                // 跳转到登录
                this.$router.push("/login");
                // 这里可以发送邮件 说: 欢迎您(邮件用户)注册INIT Blog博客网站, 期待您能我一起并肩同行, 创造更好的未来.
              }else{
                this.$message({
                  showClose: true,
                  message: '注册失败 ! ',
                  type: 'error'
                });
              }
            })
          } else {
            this.$message({
              showClose: true,
              message: '验证码错误 ! ',
              type: 'error'
            });
          }
        }

      },
      // 重置表单
      // resetForm() {
      //   this.$refs.registerForm.resetFields();
      // }
    }
  }
</script>

<style scoped>
.gongGao{
  width: 610px; 
  height: 480px; 
  border-radius: 20px; 
  box-shadow: 4px 3px 6px #999
}
</style>
