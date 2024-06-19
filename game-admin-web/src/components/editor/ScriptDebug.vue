<style scoped lang="less">
.request-header-content {
  display: flex;
  flex-direction: column;
}

.request-header-content > * {
  margin-bottom: 5px;
}

.debug-log {
  height: 400px;
  border: 1px solid var(--el-border-color);
  .debug-log-content {
    height: 100%;

  }
}

</style>
<template>
  <el-dialog v-model="isShow"
             draggable
             overflow
             :title="debugTitle"
             width="1024px"
             append-to-body>
    <el-row :gutter="20">
      <el-col :span="12" class="request-header-content">
        <el-divider content-position="left" style="margin-bottom: 20px">
          <el-text size="small">请求头</el-text>
        </el-divider>
        <el-row justify="space-evenly">
          <el-col :span="8">
            <el-text type="primary">参数名</el-text>
          </el-col>
          <el-col :span="12">
            <el-text type="primary">参数值</el-text>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" link :icon="Plus"></el-button>
          </el-col>
        </el-row>
        <el-row justify="space-evenly">
          <el-col :span="8">
            <el-input size="small" placeholder="header-name" clearable></el-input>
          </el-col>
          <el-col :span="12">
            <el-input size="small" placeholder="header-value" clearable></el-input>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" link :icon="Minus"/>
          </el-col>
        </el-row>
        <el-row justify="space-evenly">
          <el-col :span="8">
            <el-input size="small" placeholder="header-name" clearable></el-input>
          </el-col>
          <el-col :span="12">
            <el-input size="small" placeholder="header-value" clearable></el-input>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" link :icon="Minus"/>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="12" class="request-header-content">
        <el-divider content-position="left" style="margin-bottom: 20px">
          <el-text size="small">请求参数</el-text>
        </el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-text type="primary">参数名</el-text>
          </el-col>
          <el-col :span="16">
            <el-text type="primary">参数值</el-text>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-input size="small" placeholder="param-name" clearable></el-input>
          </el-col>
          <el-col :span="16">
            <el-input size="small" placeholder="param-value" clearable></el-input>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider content-position="left" style="margin-bottom: 20px">
      <el-text size="small">调试输出日志</el-text>
    </el-divider>
    <el-row class="debug-log">
      <el-col :span="24" style="position: relative">
        <el-row style="position: absolute;z-index: 10;right: 0">
          <el-button type="primary" link size="small">清除</el-button>
        </el-row>
        <el-scrollbar class="debug-log-content">

        </el-scrollbar>
      </el-col>
    </el-row>
    <el-row justify="center" style="margin-top: 20px">
      <el-button type="primary" size="large"> 执 行 脚 本 </el-button>
    </el-row>
  </el-dialog>
</template>
<script setup>
import {Minus, Plus} from "@element-plus/icons-vue";
import {boxMock} from "@/views/mock/js/boxDto.js";

const isShow = ref(false)

const debugTitle = computed(() => {
  let title = scriptType.value === 'query' ? '查询脚本调试' : scriptType.value === 'call' ? '调用脚本调试' : '回调脚本调试'
  title = title + '[' + scriptObject.value?.payName + ']'
  return title
})

const scriptObject = ref(null)
const scriptType = ref(null)
const debugScript = (rowOjb, rowType) => {
  scriptObject.value = rowOjb
  scriptType.value = rowType
  switch (rowType) {
    case 'query':
      console.log(rowType)
      break
    case 'call':
      console.log(rowType)
      break
    case 'callback':
      console.log(rowType)
      break
  }
  isShow.value = true
}

defineExpose({
  debugScript
})

</script>
