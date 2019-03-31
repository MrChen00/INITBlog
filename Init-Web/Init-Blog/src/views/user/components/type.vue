<template>
    <div class="type-container">
        
        <ul>
            <li><router-link to="/user/profile" tag="span"> 
            <i class="el-icon-location-outline"></i>基本信息</router-link></li>
            <li><router-link to="/user/article" tag="span"> <i class="el-icon-tickets" ></i>文章管理</router-link></li>
            <li><router-link to="/user/collect" tag="span"> <i class="el-icon-star-off"></i>我的收藏</router-link></li>
            <li> <router-link to="/user/message" tag="span"><i class="el-icon-message"></i>消息和留言</router-link></li>
        </ul>
    </div>
</template>

<script>
import { getToken, setToken, removeToken } from '@/utils/cookie'

export default {
    name: "type",
    created(){
        // 判断用户是否登录
        if(!getToken("token")){
            this.$message({
                showClose: true,
                message: "您当前未登录!",
                type: 'error'
            })
            
            this.$router.push("/")
        }
    }
}
</script>

<style lang="scss" scoped>
.type-container{
    ul{
        list-style: none;
        border-radius: 20px;
        box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
        padding-top: 6px;
        padding-bottom: 15px;
        li{
            margin-top: 15px;
            margin-bottom: 15px; 
            line-height: 30px;
            cursor: pointer;
            i{
                font-size: 20px; 
                margin-right: 5px;
                margin-top: 10px;
            }
        }
        li:hover{
            color:deepskyblue
        }
    }
}
</style>


