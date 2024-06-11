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
              <MultiFileUpload v-model:value="data.value" ref="multiFileUploadRef"/>
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
import MultiFileUpload from "@/components/oss/MultiFileUpload.vue";

const visible = ref(true)
const data = reactive([]);
const dataTypeRef = ref(null)
const multiFileUploadRef = ref(null);

const importData = () => {
  visible.value = true;
}

const dataTypeDictObject = ref(null);
onMounted(async () => {
  dataTypeDictObject.value = await DictObject.create('biz_data_type');
  data.type = dataTypeDictObject.value.dictMap['profile_photo']
})

const submit = async () => {
  multiFileUploadRef.value.uploadMultiFile(callBack)
}

const callBack = (file) => {
  console.log("callBack file")
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