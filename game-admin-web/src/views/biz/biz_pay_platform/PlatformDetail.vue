<template>
  <div class="bbg_sub_table">
    <el-form  size="small">
      <el-form-item label="调用引擎">
        <el-input v-model="props.rowOjb.name" placeholder="名称"/>
      </el-form-item>
    </el-form>
  </div>
</template>
<script setup>
const props = defineProps({
  rowOjb: {
    type: Object,
    required: true
  }
})
</script>
<style scoped lang="less">
.bbg_sub_table {
  margin: 10px;
  border-right: 1px solid var(--el-border-color);
}
</style>