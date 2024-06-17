<template>
  <el-container class="bbg-form">
    <el-header class="bbg-form-header">
      <el-text tag="b" size="large" type="primary">
        <h3>{{ submitText }}-支付平台管理</h3>
      </el-text>
    </el-header>
    <el-main class="bbg-form-main">
      <el-scrollbar :max-height="formDynamicHeight">
        <el-row>
          <el-col :offset="2" :span="20">
            <el-form size="small" label-position="right" label-width="120">
              <el-row>
                <el-col :span="10">
                  <el-form-item label="支付名称">
                    <el-input v-model="data.payName"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="支付别名">
                    <el-input v-model="data.payNameAlias"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="支付图标">
                    <FileUpload v-model:value="data.payImageUrl"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10"/>
                <el-col :span="10">
                  <el-form-item label="支付编码">
                    <el-input v-model="data.payCode"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="排序">
                    <el-input v-model="data.sort"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10" >
                  <el-form-item label="支付类型">
                    <el-input v-model="data.payType"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="支付币种">
                    <el-input v-model="data.payCurrency"/>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="支付限额">
                    <el-input v-model="data.payAmountLimit"/>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="支付描述">
                    <el-input v-model="data.payRemark"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-divider content-position="left">调用引擎</el-divider>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="调用引擎">
                      <bbg-dict-select v-model:value="data.type" ref="dataTypeRef" :tag="'third_pay_engine'"
                                       placeholder="调用引擎"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="调用引擎重载">
                      <el-switch v-model="data.callReload"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="20">
                    <el-form-item label="调用参数">
                      <el-input v-model="data.callArg"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="20">
                    <el-form-item label="调用引擎内容">
                      <el-input v-model="data.callContent"/>
                    </el-form-item>
                  </el-col>
                </el-row>
              <el-divider content-position="left">回调引擎</el-divider>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="回调引擎">
                    <bbg-dict-select v-model:value="data.type" ref="dataTypeRef" :tag="'third_pay_engine'"
                                     placeholder="调用引擎"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="回调引擎重载">
                    <el-switch v-model="data.callbackReload"/>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="回调参数">
                    <el-input v-model="data.callbackArg"/>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="回调引擎内容">
                    <el-input v-model="data.callbackContent"/>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="回调白名单">
                    <el-input v-model="data.callbackIp"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-divider content-position="left">查询引擎</el-divider>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="查询引擎">
                    <bbg-dict-select v-model:value="data.type" ref="dataTypeRef" :tag="'third_pay_engine'"
                                     placeholder="调用引擎"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="查询引擎重载">
                    <el-switch v-model="data.queryReload"/>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="查询参数">
                    <el-input v-model="data.queryArg"/>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="查询引擎内容">
                    <el-input v-model="data.queryContent"/>
                  </el-form-item>
                </el-col>
              </el-row>










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

const data = reactive({});
const submitText = computed(() => {
  return data.id ? '修改' : '添加'
})

const toEdit = async (id) => {
  if (id) {
    const apiRet = await http.get(`/bizPayPlatform/getInfo/${id}`);
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
    const apiRet = await http.post('/bizPayPlatform/update', data)
    if (apiRet.ok) {
      backList(true)
    }
  } else {
    const apiRet = await http.post('/bizPayPlatform/save', data)
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