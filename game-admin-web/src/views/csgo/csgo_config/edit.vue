<template>
  <el-container class="bbg-form">
    <el-header class="bbg-form-header">
      <el-text tag="b" size="large" type="primary">
        <h3>{{ submitText }}-游戏配置</h3>
      </el-text>
    </el-header>
    <el-main class="bbg-form-main">
      <el-scrollbar :max-height="formDynamicHeight">
        <el-row>
          <el-col :offset="5" :span="12">
            <el-form label-position="right" label-width="120">
              <TenantIdSelect v-model:value="data.tenantId" />
              <el-form-item label="配置名称">
                <el-input v-model="data.name"/>
              </el-form-item>
              <el-form-item label="配置别名">
                <el-input v-model="data.nameAlias"/>
              </el-form-item>
              <el-form-item label="配置值">
                <el-input v-model="data.value" size="small"
                          type="textarea"
                          show-word-limit maxlength="255"
                          :autosize="{ minRows: 7, maxRows: 10 }"
                />
                <el-row justify="end" style="width: 100%">
                  <!-- 判断data.value,是不是一个json字符串,如果是则显示格式化按钮 -->
                  <el-button v-if="isJsonStr" size="small" type="success" link @click="() => data.value = JSON.stringify(JSON.parse(data.value),null,2)">格式化</el-button>
                </el-row>
              </el-form-item>
              <el-form-item label="配置描述">
                <el-input v-model="data.remark"
                          type="textarea"
                          show-word-limit maxlength="255"
                          :autosize="{ minRows: 3, maxRows: 7 }"/>
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

const isJsonStr = computed(() => {
  try {
    JSON.parse(data.value);
    return true;
  } catch {
    return false;
  }
});

const data = reactive({});
const submitText = computed(() => {
  return data.id ? '修改' : '添加'
})

const toEdit = async (id) => {
  if (id) {
    const apiRet = await http.get(`/csgoConfig/getInfo/${id}`);
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
    const apiRet = await http.post('/csgoConfig/update', data)
    if (apiRet.ok) {
      backList(true)
    }
  } else {
    const apiRet = await http.post('/csgoConfig/save', data)
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