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
    </el-tab-pane>
  </el-tabs>
</template>
<script setup>
import {useTabsStore} from "@/store/tabsStore.js";


const store = useTabsStore()
const router = useRouter()
onMounted(() => {
  let route = router.getRoutes().find((r) => r.name === router.currentRoute.value.name);
  // console.log(route.meta.title)
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
        console.log(targetName)
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
</style>

