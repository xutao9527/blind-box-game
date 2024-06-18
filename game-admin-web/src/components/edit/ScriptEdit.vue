<template>
  <el-drawer
      append-to-body
      v-model="drawer"
      direction="rtl"
      size="80%">
    <template #header style="margin-bottom: 10px">
      <el-text type="primary" size="large">
        脚本编辑
      </el-text>
    </template>
    <template #default>
      <MonacoEdt :editorOption="editorOption" />
    </template>
    <template #footer>
      <el-button >返 回</el-button>
      &nbsp;&nbsp;
      <el-button type="primary" >保 存</el-button>
    </template>
  </el-drawer>
</template>

<script setup>

const drawer = ref(true)
const editScript = () => {
  drawer.value = true
}

defineExpose({
  editScript
})

const editorOption = reactive({
  language: 'javascript',
  code: `console.log('hello world')`
});

</script>
<style lang="less">
.monacoEditor {
  width: 100%;
  height: 100%;
}
.el-drawer__header{
  margin-bottom: 0;
}

.el-drawer__footer{
  text-align: center;
}
</style>