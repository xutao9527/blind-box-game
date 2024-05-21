<template>
  <el-container class="bbg-form">
    <el-header class="bbg-form-header">
      <el-text tag="b" size="large" type="primary">
        <h3>{{ submitText }}-Roll房间</h3>
      </el-text>
    </el-header>
    <el-main class="bbg-form-main">
      <el-scrollbar :max-height="formDynamicHeight">
        <el-row>
          <el-col :offset="7" :span="8">
            <el-form label-position="right" label-width="120">
<!--              <el-form-item label="创建用户编号">-->
<!--                <el-input v-model="data.createUserId"/>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="创建用户名称">-->
<!--                <el-input v-model="data.createUserName"/>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="创建用户头像">-->
<!--                <el-input v-model="data.createUserPhoto"/>-->
<!--              </el-form-item>-->
              <el-form-item label="房间标题">
                <el-input v-model="data.rollTitle" />
              </el-form-item>
              <el-form-item label="房间描述">
                <el-input v-model="data.rollRemark" autosize style="width: 600px;" type="textarea" show-word-limit maxlength="200"/>
              </el-form-item>
              <el-form-item label="撸房类型">
                <bbg-dict-select v-model:value="data.rollType" :tag="'csgo_roll_type'"/>
              </el-form-item>
              <el-form-item label="撸房模式">
                <bbg-dict-select v-model:value="data.rollModel" :tag="'csgo_roll_model'"/>
              </el-form-item>
              <el-form-item label="开始时间">
                <el-date-picker
                    v-model="data.startTime"
                    type="datetime"
                    value-format="YYYY-MM-DD HH:mm:ss"
                />
              </el-form-item>
              <el-form-item label="结束时间">
                <el-date-picker
                    v-model="data.endTime"
                    type="datetime"
                    value-format="YYYY-MM-DD HH:mm:ss"
                />
              </el-form-item>
              <el-form-item label="房间人数">
                <el-input-number v-model="data.peopleNumber"/>
              </el-form-item>
<!--              <el-form-item label="加入房间条件">-->
<!--                <el-input v-model="data.joinCondition"/>-->
<!--              </el-form-item>-->
              <el-form-item label="房间密码">
                <el-input v-model="data.joinCode"/>
              </el-form-item>
              <el-form-item label="房间排序">
                <el-input v-model="data.sort"/>
              </el-form-item>
<!--              <el-form-item label="状态">-->
<!--                <el-switch v-model="data.enable"/>-->
<!--              </el-form-item>-->
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
    const apiRet = await http.get(`/csgoRoll/getInfo/${id}`);
    if (apiRet.ok) {
      Object.assign(data, apiRet.data);
      data.createTime = null
      data.updateTime = null
      data.enable = false
    }
  } else {
    data.id = null
  }
}

const submit = async () => {
  if (data.id) {
    const apiRet = await http.post('/csgoRoll/update', data)
    if (apiRet.ok) {
      backList(true)
    }
  } else {
    const apiRet = await http.post('/csgoRoll/save', data)
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