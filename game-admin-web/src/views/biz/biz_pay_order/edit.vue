<template>
  <el-container class="bbg-form">
    <el-header class="bbg-form-header">
      <el-text tag="b" size="large" type="primary">
        <h3>{{ submitText }}-支付订单</h3>
      </el-text>
    </el-header>
    <el-main class="bbg-form-main">
      <el-scrollbar :max-height="formDynamicHeight">
        <el-row>
          <el-col :offset="7" :span="8">
            <el-form label-position="right" label-width="120">
              <el-form-item label="用户编号">
                <el-input v-model="data.userId"/>
              </el-form-item>
              <el-form-item label="用户手机">
                <el-input v-model="data.userMobile"/>
              </el-form-item>
              <el-form-item label="支付订单号">
                <el-input v-model="data.payNo"/>
              </el-form-item>
              <el-form-item label="支付平台编号">
                <el-input v-model="data.payPlatformId"/>
              </el-form-item>
              <el-form-item label="支付币种">
                <el-input v-model="data.payCurrency"/>
              </el-form-item>
              <el-form-item label="支付金额">
                <el-input v-model="data.payMoney"/>
              </el-form-item>
              <el-form-item label="回调时间">
                <el-input v-model="data.callbackTime"/>
              </el-form-item>
              <el-form-item label="回调内容">
                <el-input v-model="data.callbackContent"/>
              </el-form-item>
              <el-form-item label="支付订单描述">
                <el-input v-model="data.payOrderRemark"/>
              </el-form-item>
              <el-form-item label="支付状态">
                <el-input v-model="data.payStatus"/>
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

const data = reactive({});
const submitText = computed(() => {
  return data.id ? '修改' : '添加'
})

const toEdit = async (id) => {
  if (id) {
    const apiRet = await http.get(`/bizPayOrder/getInfo/${id}`);
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
    const apiRet = await http.post('/bizPayOrder/update', data)
    if (apiRet.ok) {
      backList(true)
    }
  } else {
    const apiRet = await http.post('/bizPayOrder/save', data)
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