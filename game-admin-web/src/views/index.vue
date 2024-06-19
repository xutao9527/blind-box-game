<template>
<!--  <el-config-provider :locale="locale">-->
  <el-container style="height: 100vh">
    <el-header class="bbg-header header-dynamic-height">
      <bbg-header/>
    </el-header>
    <el-container >
      <el-aside class="bbg-menu" :style="{'width':menuDynamicWidth+'px'}">
        <bbg-menu :is-collapse="isMenuCollapse"/>
      </el-aside>
      <el-main class="bbg-main" style="padding: 10px">
        <bbg-tabs/>
        <router-view v-slot="{Component, route}">
          <keep-alive>
            <component :is="Component" :key="route.path + (route.meta.keepAlive ? '' : '')"/>
          </keep-alive>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
<!--  </el-config-provider>-->
</template>
<script setup>
import emitter from "@/core/mitt/index.js";

const isMenuCollapse = ref(false)
const menuDynamicWidth = ref(0)
const menuWidth = ref(0)

onMounted(() => {
  emitter.on('menuCollapse', () => {
    isMenuCollapse.value = !isMenuCollapse.value;
    isMenuCollapse.value ? menuDynamicWidth.value = 64 : menuDynamicWidth.value = menuWidth.value
  })
  menuWidth.value = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--left-width'));
  menuDynamicWidth.value = menuWidth.value
})


</script>
<style scoped>
.header-dynamic-height {
  height: var(--header-height);
  padding: 0;
}
</style>