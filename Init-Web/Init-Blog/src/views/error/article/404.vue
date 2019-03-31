<template>
    <div class="error-container">
        <h2>文章异常页面</h2>
        <!-- 异常跳转主页 -->
        <router-link to="/"><img src="@/assets/404.jpg"/></router-link>
    </div>
</template>

<script>
export default {
    name: 'error'
}
</script>

<style lang="scss" scoped>
.error-container{
    margin-left: 10%;
    img{
        width: 60%
    }
}
</style>

