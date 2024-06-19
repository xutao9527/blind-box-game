<template>
  <el-container class="bbg-form">
    <el-header class="bbg-form-header">
      <el-text tag="b" size="large" type="primary">
        <h3>{{ submitText }}-支付平台配置</h3>
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
                <el-col :span="10">
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
                    <bbg-dict-select v-model:value="data.callEngine" :tag="'third_pay_engine'"
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
                      <el-button v-if="isJsonforCallArg" size="small" type="success" link
                                 @click="() => data.callArg = JSON.stringify(JSON.parse(data.callArg),null,2)">格式化
                      </el-button>
                    </el-row>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <template v-if="payEngineDictObject">
                    <template v-if="data.callEngine === payEngineDictObject.dictMap['javascript_engine']">
                      <el-form-item label="调用引擎脚本">
<!--                        <el-input v-model="data.callContent"/>-->
                        <el-tooltip placement="top">
                          <template #content>
                            <el-scrollbar class="script-content-scrollbar" :max-height="200">
                              {{ data.callContent }}
                            </el-scrollbar>
                          </template>
                          <el-text line-clamp="2" class="script-content">
                            {{ data.callContent }}
                          </el-text>
                        </el-tooltip>
                        <el-row justify="end" style="width: 100%">
                          <el-button size="small" type="success" link @click="callContentRef.editScript()">编辑代码</el-button>
                        </el-row>
                        <ScriptEdit ref="callContentRef" v-model:value="data.callContent" :title="`调用引擎脚本-编辑`"/>
                      </el-form-item>
                    </template>
                    <template v-else>
                      <el-form-item label="调用实例[Bean]">
                        <el-input v-model="data.callBeanName"/>
                      </el-form-item>
                    </template>
                  </template>
                </el-col>
              </el-row>
              <el-divider content-position="left">回调引擎</el-divider>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="回调引擎">
                    <bbg-dict-select v-model:value="data.callbackEngine" :tag="'third_pay_engine'" placeholder="回调引擎"/>
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
                      <el-button v-if="isJsonforCallbackArg" size="small" type="success" link
                                 @click="() => data.callbackArg = JSON.stringify(JSON.parse(data.callArg),null,2)">格式化
                      </el-button>
                    </el-row>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                    <template v-if="payEngineDictObject">
                      <template v-if="data.callbackEngine === payEngineDictObject.dictMap['javascript_engine']">
                        <el-form-item label="回调引擎脚本">
<!--                          <el-input v-model="data.callbackContent"/>-->
                          <el-tooltip placement="top">
                            <template #content>
                              <el-scrollbar class="script-content-scrollbar" :max-height="200">
                                {{ data.callbackContent }}
                              </el-scrollbar>
                            </template>
                            <el-text line-clamp="2" class="script-content">
                              {{ data.callbackContent }}
                            </el-text>
                          </el-tooltip>
                          <el-row justify="end" style="width: 100%">
                            <el-button size="small" type="success" link @click="callbackContentRef.editScript()">编辑代码</el-button>
                          </el-row>
                          <ScriptEdit ref="callbackContentRef" v-model:value="data.callbackContent" :title="`回调引擎脚本-编辑`"/>
                        </el-form-item>
                      </template>
                      <template v-else>
                        <el-form-item label="回调实例[Bean]">
                          <el-input v-model="data.callbackBeanName"/>
                        </el-form-item>
                      </template>
                    </template>
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
                    <bbg-dict-select v-model:value="data.queryEngine" :tag="'third_pay_engine'" placeholder="查询引擎"/>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="查询引擎重载">
                    <el-switch v-model="data.queryReload"/>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="查询参数">
                    <el-input v-model="data.queryArg" size="small"
                              type="textarea"
                              show-word-limit maxlength="1024"
                              :autosize="{ minRows: 3, maxRows: 10 }"/>
                    <el-row justify="end" style="width: 100%">
                      <el-button v-if="isJsonforQueryArg" size="small" type="success" link
                                 @click="() => data.queryArg = JSON.stringify(JSON.parse(data.queryArg),null,2)">格式化
                      </el-button>
                    </el-row>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <template v-if="payEngineDictObject">
                    <template v-if="data.queryEngine === payEngineDictObject.dictMap['javascript_engine']">
                      <el-form-item label="查询引擎脚本">
<!--                        <el-input v-model="data.queryContent"/>-->
                        <el-tooltip placement="top">
                          <template #content>
                            <el-scrollbar class="script-content-scrollbar" :max-height="200">
                              {{ data.queryContent }}
                            </el-scrollbar>
                          </template>
                          <el-text line-clamp="2" class="script-content">
                            {{ data.queryContent }}
                          </el-text>
                        </el-tooltip>
                        <el-row justify="end" style="width: 100%">
                          <el-button size="small" type="success" link @click="queryContentRef.editScript()">编辑代码</el-button>
                        </el-row>
                        <ScriptEdit ref="queryContentRef" v-model:value="data.queryContent" :title="`查询引擎脚本-编辑`"/>
                      </el-form-item>
                    </template>
                    <template v-else>
                      <el-form-item label="查询实例[Bean]">
                        <el-input v-model="data.queryBeanName"/>
                      </el-form-item>
                    </template>
                  </template>
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
import {DictObject} from "@/core/dict/index.js";

const payEngineDictObject = ref(null);
onMounted(async () => {
  payEngineDictObject.value = await DictObject.create('third_pay_engine');
})

const callContentRef = ref(null)
const callbackContentRef = ref(null)
const queryContentRef = ref(null)
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
  callEngine: '1',
  callbackEngine: '1',
  queryEngine: '1',
  payType: '1',
  payCurrency: '1',
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
.script-content-scrollbar{
  white-space: pre-wrap;
  padding-right: 8px;
  min-width: 400px;
  max-width: 500px;
}

.script-content {
  white-space: pre-wrap; /* 保留换行符和空白字符 */
  font-family: monospace; /* 使用等宽字体显示代码 */
  background-color: var(--el-fill-color-light); /* 可选：设置背景颜色 */
  padding: 1px; /* 可选：设置内边距 */
  border-radius: 1px; /* 可选：设置圆角边框 */
  border: 1px solid #ccc; /* 可选：设置边框 */
  width: 100%;
}
</style>