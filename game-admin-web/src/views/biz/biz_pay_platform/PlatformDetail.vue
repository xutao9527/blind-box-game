<template>
  <div class="bbg_sub_table">
    <el-form label-position="right" label-width="120" size="small">
      <el-row>
        <el-col :span="5">
          <el-form-item label="调用引擎:">
            {{ props.rowOjb.callEngine }}
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="调用引擎重载:">
            {{ props.rowOjb.callReload }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="调用参数:">
            <el-text line-clamp="2" class="script-content" >
              {{ props.rowOjb.callArg }}
            </el-text>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="调用引擎内容">
            <el-text line-clamp="2" class="script-content" >
              {{ props.rowOjb.callContent }}
            </el-text>
          </el-form-item>
        </el-col>
      </el-row>
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

.script-content {
  white-space: pre-wrap; /* 保留换行符和空白字符 */
  font-family: monospace; /* 使用等宽字体显示代码 */
  background-color: #f5f5f5; /* 可选：设置背景颜色 */
  padding: 10px; /* 可选：设置内边距 */
  border-radius: 5px; /* 可选：设置圆角边框 */
  border: 1px solid #ccc; /* 可选：设置边框 */
  width: 100%;
}
</style>