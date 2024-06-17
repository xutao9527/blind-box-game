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
                    <el-input v-model="data.sort" type="number"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10" >
                  <el-form-item label="支付类型">
                    <bbg-dict-select v-model:value="data.payType" ref="dataTypeRef" :tag="'third_pay_type'"
                                     placeholder="支付类型"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="支付币种">
                    <bbg-dict-select v-model:value="data.payCurrency" ref="dataTypeRef" :tag="'third_pay_currency'"
                                     placeholder="支付币种"/>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="支付限额">
                    <el-input v-model="data.payAmountLimit" size="small"
                              type="textarea"
                              show-word-limit maxlength="255"
                              :autosize="{ minRows: 2, maxRows: 5 }"/>
                    <el-row justify="end" style="width: 100%">
                      <el-button v-if="isJsonArray" size="small" type="success" link
                                 @click="() => data.payAmountLimit = JSON.stringify(JSON.parse(data.payAmountLimit),null,2)">
                        格式化
                      </el-button>
                    </el-row>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="支付描述">
                    <el-input v-model="data.payRemark" size="small"
                              type="textarea"
                              show-word-limit maxlength="255"
                              :autosize="{ minRows: 2, maxRows: 5 }"/>
                  </el-form-item>
                </el-col>
                <el-col>
                  <el-form-item label="状态">
                    <el-switch v-model="data.enable"/>
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
                      <el-input v-model="data.callArg" size="small"
                                type="textarea"
                                show-word-limit maxlength="1024"
                                :autosize="{ minRows: 3, maxRows: 10 }"/>
                      <el-row justify="end" style="width: 100%">
                        <el-button v-if="isJsonforCallArg" size="small" type="success" link @click="() => data.callArg = JSON.stringify(JSON.parse(data.callArg),null,2)">格式化</el-button>
                      </el-row>
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
                    <bbg-dict-select v-model:value="data.callbackEngine" ref="dataTypeRef" :tag="'third_pay_engine'"
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
                    <el-input v-model="data.callbackArg" size="small"
                              type="textarea"
                              show-word-limit maxlength="1024"
                              :autosize="{ minRows: 3, maxRows: 10 }"/>
                    <el-row justify="end" style="width: 100%">
                      <el-button v-if="isJsonforCallbackArg"  size="small" type="success" link @click="() => data.callbackArg = JSON.stringify(JSON.parse(data.callArg),null,2)">格式化</el-button>
                    </el-row>
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
                    <bbg-dict-select v-model:value="data.queryEngine" ref="dataTypeRef" :tag="'third_pay_engine'"
                                     placeholder="查询引擎"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="查询引擎重载">
                    <el-switch v-model="data.queryReload" />
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="查询参数">
                    <el-input v-model="data.queryArg" size="small"
                              type="textarea"
                              show-word-limit maxlength="1024"
                              :autosize="{ minRows: 3, maxRows: 10 }"/>
                    <el-row justify="end" style="width: 100%">
                      <el-button v-if="isJsonforQueryArg"  size="small" type="success" link @click="() => data.queryArg = JSON.stringify(JSON.parse(data.queryArg),null,2)">格式化</el-button>
                    </el-row>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="查询引擎内容">
                    <el-input v-model="data.queryContent" />
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

const isJsonArray = computed(() => {
  try {
    const parsed = JSON.parse(data.payAmountLimit);
    return Array.isArray(parsed) && parsed.every(item => typeof item === 'number');
  } catch {
    return false;
  }
})

const isJsonforCallArg = computed(() => {
  try {
    JSON.parse(data.callArg);
    return true;
  } catch {
    return false;
  }
});

const isJsonforCallbackArg = computed(() => {
  try {
    JSON.parse(data.callbackArg);
    return true;
  } catch {
    return false;
  }
});

const isJsonforQueryArg = computed(() => {
  try {
    JSON.parse(data.queryArg);
    return true;
  } catch {
    return false;
  }
});

const data = reactive({
  callReload: true,
  callbackReload: true,
  queryReload: true,
  payType:'1',
  payCurrency:'1',
});
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