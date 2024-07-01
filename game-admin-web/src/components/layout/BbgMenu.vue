<template>
  <el-scrollbar :max-height="dynamicHeight">
    <el-menu
        :default-active="activeMenu"
        :collapse="props.isCollapse"
        :collapse-transition="false"
        style="border: 0"
        :router="true"
        @select="menuSelect"
        unique-opened
    >
      <template v-for="menuItem in menuTree">
        <template v-if="menuItem.view">
          <el-sub-menu :index="menuItem.name">
            <template #title>
              <el-icon>
                <component :is="menuItem.icon"/>
              </el-icon>
              <span>{{ menuItem.title }}</span>
            </template>
            <template v-if="menuItem.children && menuItem.children.length > 0">
              <template v-for="subMenuItem in menuItem.children">
                <el-menu-item v-if="subMenuItem.view" :index="subMenuItem.name" :route="subMenuItem.path">
                  <el-icon>
                    <component :is="subMenuItem.icon"></component>
                  </el-icon>
                  <template #title>
                    <span>{{ subMenuItem.title }}</span>
                  </template>
                </el-menu-item>
              </template>
            </template>
          </el-sub-menu>
        </template>
      </template>
    </el-menu>
  </el-scrollbar>
</template>
<script setup>
import {useUserStore} from "@/store/userStore.js";
import {useTabsStore} from "@/store/tabsStore.js";


const activeMenu = ref('')
const menuTree = ref(null);
const userStore = useUserStore()
const router = useRouter()
const tabsStore = useTabsStore()
const menuSelect = (index, indexPath, item, routeResult) => {
  let route = router.getRoutes().find((item) => {
    return item.name === index
  })
  tabsStore.activateTab(
      {
        title: route.meta.title,
        path: route.path,
        name: route.name
      }
  )
}

const dynamicHeight = ref(0)
onMounted(async () => {
  let top = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--header-height'))
  dynamicHeight.value = document.body.offsetHeight - top
  let menuTreeData = await userStore.getMenuTree;
  if (menuTreeData && menuTreeData.length > 0 && menuTreeData[0].children) {
    menuTree.value = menuTreeData[0].children
  }
  activeMenu.value = router.currentRoute.value.name
})

const props = defineProps({
  isCollapse: {
    type: Boolean,
  }
})
</script>
<style scoped>

</style>