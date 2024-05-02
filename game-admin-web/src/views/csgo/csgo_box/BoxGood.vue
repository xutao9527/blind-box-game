<template>
  <div class="bbg_sub_table">
    <el-form label-position="right" label-width="10" :inline="true" size="small">
      <el-form-item>
        <el-input v-model="tableProps.editData.price" placeholder="商品价格"/>
      </el-form-item>
      <el-form-item>
        <el-input v-model="tableProps.editData.rate" placeholder="获得概率"/>
      </el-form-item>
      <el-form-item>
        <el-input v-model="tableProps.editData.rate" placeholder="获得概率"/>
      </el-form-item>
      <el-form-item>
        <el-switch v-model="tableProps.editData.enable" placeholder="状态"/>
      </el-form-item>
      <el-form-item>
        <el-button icon="Plus" @click="goodsSelectRef.selectData()">选择</el-button>
        <el-button icon="Plus" @click="submit">{{ submitText }}</el-button>
        <el-button icon="Plus" @click="toEdit(undefined)">取消</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableProps.apiRet.data" size="small">
      <el-table-column prop="name" label="商品名称"/>
      <el-table-column prop="nameAlias" label="商品别名"/>
      <el-table-column prop="imageUrl" label="图片地址"/>
      <el-table-column prop="price" label="商品价格"/>
      <el-table-column prop="rate" label="获得概率"/>
      <el-table-column prop="enable" label="状态">
        <template #default="scope">
          {{ scope.row.enable ? '启动' : '停用' }}
        </template>
      </el-table-column>
      <el-table-column prop="updateTime" label="修改时间"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="toEdit(scope.row)">编辑</el-button>
          <el-button link type="primary" size="small" @click="remove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <goods-select ref="goodsSelectRef"/>
  </div>
</template>
<script setup>

import {http} from "@/core/axios/index.js";
import GoodsSelect from "@/views/csgo/csgo_box/GoodsSelect.vue";

const submitText = computed(() => {
  return tableProps.editData.id ? '修改' : '添加'
})

const goodsSelectRef = ref(null)
const tableProps = reactive({
  queryEntity: {},
  editData: {
    enable: true,
    goodId: '22313'
  },
  apiRet: {
    data: [],
  },
  fetchData: async () => {
    let apiRet = await http.post('/csgoBoxGoods/list', tableProps.queryEntity)
    if (apiRet.ok) {
      tableProps.apiRet = apiRet
    }
  }
})

const toEdit = async (row) => {
  if (row) {
    Object.assign(tableProps.editData, row);
    tableProps.editData.createTime = null
    tableProps.editData.updateTime = null;
  } else {
    tableProps.editData.id = null
  }
}

const submit = async () => {
  if (!tableProps.editData.price || !tableProps.editData.rate) {
    ElMessage({type: 'error', message: '参数不能为空!'})
    return
  }
  if (tableProps.editData.id) {
    const apiRet = await http.post('/csgoBoxGoods/update', tableProps.editData)
    if (apiRet.ok) {
      ElMessage({type: 'success', message: apiRet.msg})
      await tableProps.fetchData()
    }
  } else {
    const apiRet = await http.post('/csgoBoxGoods/save', tableProps.editData)
    if (apiRet.ok) {
      ElMessage({type: 'success', message: apiRet.msg})
      await tableProps.fetchData()
    }
  }
}

const remove = async (row) => {
  const apiRet = await http.get(`/csgoBoxGoods/remove/${row.id}`)
  if (apiRet.ok) {
    ElMessage({type: 'success', message: apiRet.msg})
    await tableProps.fetchData()
  }
}

onMounted(() => {
  tableProps.editData.boxId = props.rowOjb.id.toString()
  tableProps.queryEntity.boxId = props.rowOjb.id.toString()
  tableProps.fetchData()
})

const props = defineProps({
  rowOjb: {
    type: Object,
    required: true
  }
})
</script>
<style lang="less" scoped>
.bbg_sub_table {
  margin: 10px;
  border-right: 1px solid var(--el-border-color);
}
</style>