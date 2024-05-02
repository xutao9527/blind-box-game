<template>
  <el-tabs tab-position="right" class="content-tabs" v-model="activeName">
    <el-tab-pane label="数 据" name="list">
      <list @activeRightTabs="activeTabs" ref="listRef"/>
    </el-tab-pane>
    <el-tab-pane label="表 单" name="form">
      <edit @activeRightTabs="activeTabs" ref="editRef"/>
    </el-tab-pane>
  </el-tabs>
</template>
<script setup>
import List from "./list.vue";
import Edit from "./edit.vue";

const activeName = ref('list')
const listRef = ref(null);
const editRef = ref(null)
const activeTabs = (tabs, refresh = false, id) => {
  activeName.value = tabs
  if (tabs === 'form' ) {
    editRef.value.toEdit(id)
  }
  else if (tabs === 'list' && refresh) {
    listRef.value.tableProps.fetchData()
  }
};
</script>
<style lang="less">
.content-tabs {
  & .el-tabs__item {
    writing-mode: vertical-lr;
    justify-content: center;
  }

  & .el-tabs__item.is-right {
    justify-content: center;
  }

  & .el-tabs__nav {
    width: 40px;
  }
}
</style>
