<template>
  <el-drawer
      append-to-body
      v-model="drawer"
      direction="rtl"
      size="20%"
      :before-close="handleClose"
  >
    <template #header style="margin-bottom: 10px">
      <el-text type="primary" size="large">
        脚本编辑
      </el-text>
    </template>
    <template #default>
      {{editorOption}}
      <MonacoEdit :editor-option="editorOption" />
    </template>
    <template #footer>
      <el-button @click="close">返 回</el-button>
      &nbsp;&nbsp;
      <el-button type="primary" @click="save">保 存</el-button>
    </template>
  </el-drawer>
</template>

<script setup>
import MonacoEdit from "@/components/edit/MonacoEdit.vue";

const props = defineProps({
  value: {
    type: String,
  }
})

const editorOption = reactive({
  language: 'javascript',
  code: props.value,
});

watchEffect(() => {
  console.log("script edit",editorOption.code)
});

watch(() => props.value, (newValue) => {
  console.log("script edit",newValue)
  editorOption.code = newValue;
});

const drawer = ref(false)
const editScript = () => {
  drawer.value = true
}

const save = () => {
  drawer.value = false
}

const close = () => {
  drawer.value = false
}

const handleClose = () => {
  close()
  // ElMessageBox.confirm('保存脚本编辑?', {
  //   confirmButtonText: '确认',
  //   cancelButtonText: '取消',
  // })
  //     .then(() => {
  //       save()
  //     })
  //     .catch(() => {
  //       close()
  //     })
}

defineExpose({
  editScript
})

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