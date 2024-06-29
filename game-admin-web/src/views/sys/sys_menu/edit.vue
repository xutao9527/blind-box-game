<template>
  <el-container class="bbg-form">
    <el-header class="bbg-form-header">
      <el-text tag="b" size="large" type="primary">
        <h3>{{ submitText }}-系统菜单</h3>
      </el-text>
    </el-header>
    <el-main class="bbg-form-main">
      <pre>{{JSON.stringify(data,null,2)}}</pre>
      <el-scrollbar :max-height="formDynamicHeight">
        <el-row>
          <el-col :offset="7" :span="8">
            <el-form label-position="right" label-width="120">
              <template v-if="!data.id">
                <el-form-item label="父菜单">
                  <BbgMenuSelect v-model:value="data.parentId" :is-panel="true" :is-menu="data.type"/>
                </el-form-item>
              </template>
              <template v-else-if="data.id && data.parentId != null">
                <el-form-item label="父菜单">
                  <BbgMenuSelect v-model:value="data.parentId" :is-panel="true"/>
                </el-form-item>
              </template>
              <el-form-item label="菜单标题">
                <el-input v-model="data.title"/>
              </el-form-item>
              <el-form-item label="菜单名称">
                <el-input v-model="data.name"/>
              </el-form-item>
              <el-form-item label="请求路径">
                <el-input v-model="data.path"/>
              </el-form-item>
              <el-form-item label="图标">
                <bbg-icon-select v-model:value="data.icon"></bbg-icon-select>
              </el-form-item>
              <el-form-item label="组件路径">
                <el-input v-model="data.component"/>
              </el-form-item>
              <el-form-item label="排序">
                <el-input v-model="data.sort"/>
              </el-form-item>
              <el-form-item label="类型">
                <el-radio-group v-model="data.type" >
                  <el-radio-button value="1" label="菜单">菜单</el-radio-button>
                  <el-radio-button value="2" label="按钮">接口</el-radio-button>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="显示">
                <el-switch v-model="data.view"/>
              </el-form-item>
              <el-form-item label="启用">
                <el-switch v-model="data.enable"/>
              </el-form-item>
              <el-form-item label="租户权限">
                <el-radio-group v-model="data.tenantPermissions">
                  <el-radio :label="false" :value="false">所有租户</el-radio>
                  <el-radio :label="true" :value="true">顶级租户</el-radio>
                </el-radio-group>
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

const data = reactive({
  type: '1',
  view: true,
  enable: true,
  tenantPermissions: false
});
const submitText = computed(() => {
  return data.id ? '修改' : '添加'
})

const toEdit = async (id) => {
  if (id) {
    const apiRet = await http.get(`/sysMenu/getInfo/${id}`);
    if (apiRet.ok) {
      Object.assign(data, apiRet.data);
      data.parentId = data.parentId == null ? null : data.parentId.toString()
      data.createTime = null
      data.updateTime = null;
    }
  } else {
    data.id = null
  }
  if(!data.tenantPermissions){
    data.tenantPermissions = false
  }
}

const submit = async () => {
  if (data.id) {
    const apiRet = await http.post('/sysMenu/update', data)
    if (apiRet.ok) {
      backList(true)
    }
  } else {
    const apiRet = await http.post('/sysMenu/save', data)
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