<template>
  <el-container class="bbg-form">
    <el-header class="bbg-form-header">
      <el-text tag="b" size="large" type="primary">
        <h3>{{ submitText }}-Csgo商品表</h3>
      </el-text>
    </el-header>
    <el-main class="bbg-form-main">
      <el-scrollbar :max-height="formDynamicHeight">
        <el-row>
          <el-col :offset="7" :span="8">
            <el-form label-position="right" label-width="120">
              <el-form-item label="皮肤名称">
                <el-input v-model="data.itemName"/>
              </el-form-item>
              <el-form-item label="皮肤短名">
                <el-input v-model="data.shortName"/>
              </el-form-item>
              <el-form-item label="皮肤市场名称">
                <el-input v-model="data.marketHashName"/>
              </el-form-item>
              <el-form-item label="类型">
                <el-input v-model="data.type"/>
              </el-form-item>
              <el-form-item label="类型名称">
                <el-input v-model="data.typeName"/>
              </el-form-item>
              <el-form-item label="外观">
                <el-input v-model="data.exterior"/>
              </el-form-item>
              <el-form-item label="外观名称">
                <el-input v-model="data.exteriorName"/>
              </el-form-item>
              <el-form-item label="外观颜色">
                <el-input v-model="data.exteriorColor"/>
              </el-form-item>
              <el-form-item label="类别">
                <el-input v-model="data.quality"/>
              </el-form-item>
              <el-form-item label="类别名称">
                <el-input v-model="data.qualityName"/>
              </el-form-item>
              <el-form-item label="类别颜色">
                <el-input v-model="data.qualityColor"/>
              </el-form-item>
              <el-form-item label="品质">
                <el-input v-model="data.rarity"/>
              </el-form-item>
              <el-form-item label="品质名称">
                <el-input v-model="data.rarityName"/>
              </el-form-item>
              <el-form-item label="品质颜色">
                <el-input v-model="data.rarityColor"/>
              </el-form-item>
              <el-form-item label="价格">
                <el-input v-model="data.price"/>
              </el-form-item>
              <el-form-item label="人名币价格">
                <el-input v-model="data.cnyPrice"/>
              </el-form-item>
              <el-form-item label="图片地址">
                <el-input v-model="data.imageUrl"/>
              </el-form-item>
              <el-form-item label="商品数量">
                <el-input v-model="data.quantity"/>
              </el-form-item>
              <el-form-item label="状态">
                <el-switch v-model="data.status"/>
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
    const apiRet = await http.get(`/csgoGoods/getInfo/${id}`);
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
    const apiRet = await http.post('/csgoGoods/update', data)
    if (apiRet.ok) {
      backList(true)
    }
  } else {
    const apiRet = await http.post('/csgoGoods/save', data)
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