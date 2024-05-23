<template>
  <el-container class="bbg-form">
    <pre>
      {{JSON.stringify(data,null,2)}}
    </pre>
    <el-header class="bbg-form-header">
      <el-text tag="b" size="large" type="primary">
        <h3>{{ submitText }}-业务用户</h3>
      </el-text>
    </el-header>
    <el-main class="bbg-form-main">
      <el-scrollbar :max-height="formDynamicHeight">
        <el-row>
          <el-col :offset="7" :span="8">
            <el-form label-position="right" label-width="120">
              <el-form-item label="手机号">
                <el-input v-model="data.mobile"/>
              </el-form-item>
              <el-form-item label="登录账号">
                <el-input v-model="data.account"/>
              </el-form-item>
              <el-form-item label="密码">
                <el-input v-model="data.password"/>
              </el-form-item>
              <el-form-item label="类型">
                <bbg-dict-select v-model:value="data.type" :tag="'user_type'" placeholder="用户类型"/>
              </el-form-item>
              <el-form-item label="昵称">
                <el-input v-model="data.nickName"/>
              </el-form-item>
              <el-form-item label="头像">
                <el-input v-model="data.photo"/>
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="data.email"/>
              </el-form-item>
              <el-form-item label="真实姓名">
                <el-input v-model="data.name"/>
              </el-form-item>
              <el-form-item label="身份证">
                <el-input v-model="data.idCard"/>
              </el-form-item>
              <el-form-item label="生日">
                <el-input v-model="data.birthday"/>
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
<!--      <el-button @click="submit">-->
<!--        {{ submitText }}-->
<!--      </el-button>-->
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
    const apiRet = await http.get(`/bizUser/getInfo/${id}`);
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
    const apiRet = await http.post('/bizUser/update', data)
    if (apiRet.ok) {
      backList(true)
    }
  } else {
    const apiRet = await http.post('/bizUser/save', data)
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