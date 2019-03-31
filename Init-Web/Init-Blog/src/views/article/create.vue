// 创建文章
<template>
    <div>
        <article-detail :is-edit="false"></article-detail>
    </div>
</template>

<script>
import ArticleDetail from '@/views/article/components/ArticleDetail'

import { getToken, setToken, removeToken } from '@/utils/cookie'

export default {
    name: 'CreateArticle',
    components: {
        ArticleDetail
    },
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

<style scoped lang="scss">
</style>

