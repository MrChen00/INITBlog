<template>
    <el-main style="padding: 0px">
      <div class="main-container">
          <!-- 路由动画 -->
          <transition>
              <router-view></router-view>
          </transition>
      </div>
    </el-main>
</template>

<script>
export default {
    name: "Main"
}
</script>

<style lang="scss" scoped>
.main-container{
    overflow-x: hidden
}

// 路由动画
.v-enter {
  opacity: 0;
  transform: translateX(100%);
}

.v-leave-to {
  opacity: 0;
  transform: translateX(-100%);
  position: absolute;
}

.v-enter-active,
.v-leave-active {
  transition: all 0.5s ease;
}
</style>

