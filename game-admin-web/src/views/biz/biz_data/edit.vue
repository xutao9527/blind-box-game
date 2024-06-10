<template>
  <el-container class="bbg-form">
    <el-header class="bbg-form-header">
      <el-text tag="b" size="large" type="primary">
        <h3>{{ submitText }}-业务数据</h3>
      </el-text>
    </el-header>
    <el-main class="bbg-form-main">
      <el-scrollbar :max-height="formDynamicHeight">
        <el-row>
          <el-col :offset="7" :span="8">
            <el-form label-position="right" label-width="120">
              <el-form-item label="数据类型">
                  <bbg-dict-select v-model:value="data.type" ref="dataTypeRef" :tag="'biz_data_type'" placeholder="数据类型"/>
              </el-form-item>
              <template v-if="dataType === '人物头像' || dataType === '首页图片'">
                <el-form-item label="图片">
                  <FileUpload v-model:value="data.value"/>
                </el-form-item>
              </template>
              <template v-else>
                <el-form-item label="数据值">
                  <el-input v-model="data.value"/>
                </el-form-item>
              </template>
              <el-form-item label="排序">
                <el-input type="number" v-model="data.sort"/>
              </el-form-item>
              <el-form-item label="数据描述">
                <el-input v-model="data.remark"/>
              </el-form-item>
              <el-form-item label="状态">
                <el-switch v-model="data.enable"/>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </el-scrollbar>
    </el-main>
    <el-footer class="bbg-form-footer">
      <el-button @click="submit">
        {{ submitText }}
      </el-button>
      <el-button @click="backList()">
        返回
      </el-button>
    </el-footer>
  </el-container>
</template>
<script setup>
import {http} from "@/core/axios";
import emitter from "@/core/mitt/index.js";
import FileUpload from "@/components/oss/FileUpload.vue";

const dataTypeRef = ref(null)
const dataType = computed(() => {
  if(dataTypeRef.value) {
    return dataTypeRef.value.getLabel(data.type)
  }
  return null
})
const data = reactive({});
const submitText = computed(() => {
  return data.id ? '修改' : '添加'
})

const toEdit = async (id) => {
  if (id) {
    const apiRet = await http.get(`/bizData/getInfo/${id}`);
    if (apiRet.ok) {
      Object.assign(data, apiRet.data);
      data.createTime = null
      data.updateTime = null;
    }
  } else {
    data.id = null
  }
}

const submit = async () => {
  if (data.id) {
    const apiRet = await http.post('/bizData/update', data)
    if (apiRet.ok) {
      backList(true)
    }
  } else {
    const apiRet = await http.post('/bizData/save', data)
    if (apiRet.ok) {
      ElMessage({type: 'success', message: apiRet.msg})
      backList(true)
    }
  }
}

const backList = (refresh = false) => {
  emits('activeRightTabs', 'list', refresh);
};

const emits = defineEmits(['activeRightTabs']);
defineExpose({
  toEdit
})

const formDynamicHeight = ref(0)
onMounted(() => {
  let top = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--header-height'))
  formDynamicHeight.value = document.body.offsetHeight - top - 60 - 60 - 51 - 32
  emitter.on('bbgWindowResize', () => {
    let top = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--header-height'))
    formDynamicHeight.value = document.body.offsetHeight - top - 60 - 60 - 51 - 32
  })
})
</script>
<style scoped>

</style>
<style>

</style>