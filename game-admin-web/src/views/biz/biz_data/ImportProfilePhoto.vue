<template>
  <el-dialog
      v-model="visible"
      draggable
      overflow
      append-to-body
      :close-on-press-escape="false"
  >
    <el-container class="bbg-form">
      <el-header class="bbg-form-header">
        <el-text tag="b" size="large" type="primary">
          <h3>导入头像</h3>
        </el-text>
      </el-header>
      <el-main class="bbg-form-main">
        <el-scrollbar>
          <el-form label-position="right" label-width="auto">
            <el-row>
              <el-col :span="8">
                <el-form-item label="数据类型">
                  <bbg-dict-select v-model:value="data.type" ref="dataTypeRef" disabled="disabled"
                                   :tag="'biz_data_type'" placeholder="数据类型"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="数据值">
              <MultiFileUpload v-model:value="data.value" />
            </el-form-item>
          </el-form>
        </el-scrollbar>
      </el-main>
      <el-footer class="bbg-form-footer">
        <el-button @click="submit">
          导入
        </el-button>
      </el-footer>
    </el-container>
  </el-dialog>
</template>
<script setup>
import {DictObject} from "@/core/dict/index.js";
import {http} from "@/core/axios/index.js";
import emitter from "@/core/mitt/index.js";
import MultiFileUpload from "@/components/oss/MultiFileUpload.vue";
import FileUpload from "@/components/oss/FileUpload.vue";

const visible = ref(true)
const data = reactive([]);
const finalData = computed(() => {
  if (data.value) {
    return data.value.trim().split('\n').map(item => {
      return {
        type: data.type,
        value: item.trim()
      }
    }).filter(d => d.value && d.value !== '');
  }
  return []
})
const dataTypeRef = ref(null)

const importData = () => {
  visible.value = true;
}

const dataTypeDictObject = ref(null);
onMounted(async () => {
  dataTypeDictObject.value = await DictObject.create('biz_data_type');
  data.type = dataTypeDictObject.value.dictMap['profile_photo']
})

const submit = async () => {
  if (data.value && finalData.value && finalData.value.length > 0) {
    const apiRet = await http.post('/bizData/saveBatch', finalData.value)
    if (apiRet.ok) {
      if (apiRet.data) {
        ElMessage({type: 'success', message: '操作成功'})
      } else {
        ElMessage({type: 'success', message: '操作过于频繁'})
      }
      backList()
      emitter.emit('bizDataFetchData');
    }
  }
}

const backList = () => {
  visible.value = false;
}

defineExpose({
  importData
})

</script>
<style scoped lang="less">

</style>