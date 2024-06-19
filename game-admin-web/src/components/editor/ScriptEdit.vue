<template>
  <el-drawer
      append-to-body
      v-model="drawer"
      direction="rtl"
      size="90%"
      :before-close="beforeClose"
  >
    <template #header style="margin-bottom: 10px">
      <el-text type="primary" size="large">
        {{props.title}}
      </el-text>
    </template>
    <template #default>
<!--      s:{{script}}<br>-->
      <MonacoEdit v-model:value="script" ref="monacoEditRef"/>
    </template>
    <template #footer>
      <el-button @click="close">返 回</el-button>
      <el-button type="primary" @click="save">保 存</el-button>
    </template>
  </el-drawer>
</template>

<script setup>
import MonacoEdit from "@/components/editor/MonacoEdit.vue";

const monacoEditRef = ref(null)
const emit = defineEmits(['update:value'])
const props = defineProps({
  value: {
    type: String,
  },
  title:{
    type: String,
    default: '脚本编辑'
  }
})
const script = ref(props.value)
const drawer = ref(false)

const editScript = () => {
  console.log("editScript",props.value)
  script.value = props.value
  if(monacoEditRef.value){
    monacoEditRef.value.editScript(props.value)
  }
  drawer.value = true
}

const save = () => {
  emit('update:value', script.value)
  drawer.value = false
}

const close = () => {
  drawer.value = false
}

const beforeClose = (done) => {
  ElMessageBox.confirm("确认保存？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    save();
  }).catch(() => {
    close()
  });
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
  text-align: right;
}
</style>