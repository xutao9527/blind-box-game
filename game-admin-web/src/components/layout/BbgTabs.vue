<template>
  <el-tabs
      v-model="store.currentName"
      type="card"
      editable
      class="bbg-tabs"
      @edit="handleTabsEdit"
    @tab-change="handleTabsChange">
    <el-tab-pane
        v-for="item in store.tabs"
        :key="item.name"
        :label="item.title"
        :name="item.name">
      <template #label>
        <el-dropdown trigger="contextmenu" >
          <span>{{ item.title }}</span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handDropdown('current',item)">关闭当前</el-dropdown-item>
              <el-dropdown-item @click="handDropdown('other',item)">关闭其他</el-dropdown-item>
              <el-dropdown-item @click="handDropdown('all',item)">关闭所有</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
    </el-tab-pane>
  </el-tabs>
</template>
<script setup>
import {useTabsStore} from "@/store/tabsStore.js";

const store = useTabsStore()
const router = useRouter()
onMounted(() => {
  let route = router.getRoutes().find((r) => r.name === router.currentRoute.value.name);
  if(route){
    store.activateTab(
        {
          title: route.meta.title,
          path: route.path,
          name: route.name
        }
    )
  }
})

const handDropdown = (action,data) => {
  switch (action) {
    case 'current':
      if('welcome' !== data.name){
        store.removeTab(data.name)
      }
      break
    case 'other':
      const tabs1 = store.tabs.slice()
      tabs1.forEach((tab) => {
        if (tab.name !== data.name && 'welcome' !== tab.name) {
          store.removeTab(tab.name)
        }
      })
      break
    case 'all':
      const tabs2 = store.tabs.slice()
      tabs2.forEach((tab) => {
        if ('welcome' !== tab.name) {
          store.removeTab(tab.name)
        }
      })
      break
  }
}

const handleTabsChange = (tabName) =>{
  const tabInfo = store.getTab(tabName)
  router.push(tabInfo.path)
}

const handleTabsEdit = (
    targetName,
    action
) => {

  if (action === 'remove') {
    if (store.tabs.length !== 1) {
      //store.removeTab(targetName)
      if(store.currentName === targetName){
        // console.log(targetName)
        store.tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            const nextTab = store.tabs[index + 1] || store.tabs[index - 1]
            store.activateTab(nextTab)
            router.push(nextTab.path)
          }
        })
      }
      store.removeTab(targetName)
    }
  }
}
</script>
<style lang="less">
.bbg-tabs > .el-tabs__header {
  margin-bottom: 10px;
}

.el-tabs__item.is-active >  .el-dropdown {
  color: var(--el-color-primary);
}
</style>

