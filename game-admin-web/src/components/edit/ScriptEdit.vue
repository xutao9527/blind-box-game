<template>
  <el-drawer
      append-to-body
      v-model="drawer"
      title="脚本编辑"
      :with-header="false"
      direction="rtl"
      size="60%">
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
</script>
<style scoped lang="less">

</style>