<style scoped lang="less" xmlns="http://www.w3.org/1999/html">
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
            <el-button type="primary" link :icon="Plus" @click="debugReq.addHeader()"></el-button>
          </el-col>
        </el-row>
        <el-row justify="space-evenly" v-for="(header, index) in debugReq.headers">
          <el-col :span="8">
            <el-input size="small" v-model="header.name" placeholder="header-name" clearable></el-input>
          </el-col>
          <el-col :span="12">
            <el-input size="small" v-model="header.value" placeholder="header-value" clearable></el-input>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" link :icon="Minus" @click="debugReq.removeHeader(index)"/>
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
        <el-row :gutter="20" v-for="param in debugReq.params">
          <el-col :span="8">
            <el-input size="small" v-model="param.name" placeholder="param-name" clearable></el-input>
          </el-col>
          <el-col :span="16">
            <el-input size="small" v-model="param.value" placeholder="param-value" clearable></el-input>
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
          <el-button type="primary" link size="small" @click="messages = []">清除</el-button>
        </el-row>
        <el-scrollbar class="debug-log-content">
          <el-text v-for="msg in messages" style="white-space: pre-wrap;" size="small">
            <p v-html="msg" style="margin: 0"/>
          </el-text>
        </el-scrollbar>
      </el-col>
    </el-row>
    <el-row justify="center" style="margin-top: 20px">
      <el-button type="primary" size="large" @click="debugReq.execute"> 执 行 脚 本</el-button>
    </el-row>
  </el-dialog>
</template>
<script setup>
import {Minus, Plus} from "@element-plus/icons-vue";
import {useDebugDataStore} from "@/store/debugStore.js";
import {http} from "@/core/axios/index.js";
import {webSocket} from "@/core/socket/index.js";
import {AnsiUp} from "ansi_up";

const messages = ref([])
const ansiUp = new AnsiUp();
const handleMessageEvent  = (event) => {
  messages.value.push(ansiUp.ansi_to_html(event.data));
};

onMounted(() => {
  webSocket.socket.addEventListener('message', handleMessageEvent);
});

onUnmounted(() => {
  webSocket.socket.removeEventListener('message', handleMessageEvent);
});

const isShow = ref(false)
const debugTitle = computed(() => {
  let title = scriptType.value === 'query' ? '查询脚本调试' : scriptType.value === 'call' ? '调用脚本调试' : '回调脚本调试'
  title = title + '[' + scriptObject.value?.payName + ']'
  return title
})

const debugReq = reactive({
  target: '',
  headers: [],
  params: [],
  addHeader() {
    this.headers.push({name: '', value: ''})
  },
  removeHeader(index) {
    this.headers.splice(index, 1)
  },
  pushHeader(header) {
    const index = this.headers.findIndex(item => item.name === header.name)
    if (index === -1) {
      this.headers.push(header)
    }
  },
  pushParam(param) {
    const index = this.params.findIndex(item => item.name === param.name)
    if (index === -1) {
      this.params.push(param)
    }
  },
  async execute() {
    debugStore.setData(debugReq.target, {headers: debugReq.headers, params: debugReq.params})
    const convertArrayToMap = (array) => {
      let map = {}
      array.forEach(item => {
        if (item.name && item.value !== undefined) {
          map[item.name] = item.value;
        }
      })
      return map
    }
    let debugData = {
      debugTarget: debugReq.target,
      headers: convertArrayToMap(debugReq.headers),
      params: convertArrayToMap(debugReq.params),
      payPlatform: scriptObject.value
    }
    const apiRet = await http.post('/bizPayPlatform/debug', debugData)
    if (apiRet.ok) {
      ElMessage({type: 'success', message: apiRet.msg})
    }else{
      ElMessage({type: 'error', message: apiRet.msg})
    }
  }
})

const debugStore = useDebugDataStore()
const scriptObject = ref(null)
const scriptType = ref(null)
const debugScript = (rowOjb, rowType) => {
  scriptObject.value = rowOjb
  scriptType.value = rowType
  debugReq.target = rowType
  debugReq.headers = []
  debugReq.params = []
  let storeData = debugStore.getData(rowType)
  if (storeData) {
    debugReq.headers = storeData.headers
    debugReq.params = storeData.params
  } else {
    if (rowType === 'call') {
      debugReq.pushHeader({name: 'token', value: ''})
      if (rowOjb.payAmountLimit) {
        debugReq.pushParam({name: 'money', value: JSON.parse(rowOjb.payAmountLimit)[0]})
      } else {
        debugReq.pushParam({name: 'money', value: ''})
      }
    } else if (rowType === 'callback') {
      debugReq.pushParam({name: 'payCode', value: rowOjb.payCode})
    } else if (rowType === 'query') {
      debugReq.pushParam({name: 'payCode', value: ''})
    }
  }
  isShow.value = true
}

defineExpose({
  debugScript
})

</script>
