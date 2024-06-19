
<template>
<el-dialog v-model="isShow"
           draggable
           overflow
           append-to-body>

</el-dialog>
</template>
<script setup>
const isShow = ref(false)

const scriptObject = ref(null)
const scriptType = ref(null)
const debugScript = (rowOjb,rowType) => {
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
<style scoped lang="less">

</style>