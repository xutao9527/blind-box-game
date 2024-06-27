<template>
  <div class="bbg_sub_table">
    <el-form label-position="right" :inline="true" size="small">
      <el-form-item label="名称">
        <el-input v-model="tableProps.editData.name" placeholder="商品名称"/>
      </el-form-item>
      <el-form-item label="别名">
        <el-input v-model="tableProps.editData.nameAlias" placeholder="商品别名"/>
      </el-form-item>
      <el-form-item label="商品价格">
        <el-input v-model="tableProps.editData.goodPrice" placeholder="商品价格"/>
      </el-form-item>
      <el-form-item>
        <el-switch v-model="tableProps.editData.enable" placeholder="状态"/>
      </el-form-item>
      <el-form-item>
        <el-button icon="Position" @click="goodsSelectRef.selectData()">选择</el-button>
        <el-button icon="Plus" @click="submit">{{ submitText }}</el-button>
        <el-button icon="Close" @click="toEdit(undefined)">取消</el-button>
        <el-button :type="enableButtonType" icon="Open" @click="enableRoll">
          {{ props.rowOjb.enable ? '停用' : '启用' }}
        </el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableProps.apiRet.data" size="small">
      <el-table-column prop="goodId" label="商品编号"/>
      <el-table-column prop="name" label="商品名称"/>
      <el-table-column prop="nameAlias" label="商品别名"/>
      <el-table-column prop="imageUrl" label="图片" width="60">
        <template #default="scope">
          <el-image :src="scope.row.goodImage" :preview-src-list="[scope.row.goodImage]" preview-teleported/>
        </template>
      </el-table-column>
      <el-table-column prop="goodPrice" label="商品价格"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="toEdit(scope.row)">编辑</el-button>
          <el-button link type="primary" size="small" @click="remove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <goods-select ref="goodsSelectRef" v-model:good="tableProps.editData"/>
  </div>
</template>
<script setup>

import {http} from "@/core/axios/index.js";

const enableButtonType = computed(() => {
  if (props.rowOjb.type === '1' || props.rowOjb.type === '2') {
    return sumRate.value === 100 ? 'success' : 'danger'
  } else {
    return 'success'
  }
})

const enableRoll = async () => {
  const data = {
    id: props.rowOjb.id.toString(),
  }
  if (props.rowOjb.enable) {
    data.enable = false;
    const apiRet = await http.post('/csgoRoll/update', data)
    if (apiRet.ok) {
      props.rowOjb.enable = false;
      ElMessage({type: 'success', message: '停用成功!'})
    }
  }else {
    data.enable = true;
    const apiRet = await http.post('/csgoRoll/update', data)
    if (apiRet.ok) {
      props.rowOjb.enable = true;
      ElMessage({type: 'success', message: '启动成功!'})
    }
  }

}

const submitText = computed(() => {
  return tableProps.editData.id ? '修改' : '添加'
})

const goodsSelectRef = ref(null)
const tableProps = reactive({
  queryEntity: {},
  editData: {
    enable: true,
  },
  apiRet: {
    data: [],
  },
  fetchData: async () => {
    let apiRet = await http.post('/csgoRollGood/list', tableProps.queryEntity)
    if (apiRet.ok) {
      tableProps.apiRet = apiRet
    }
  }
})

const submitData = computed(()=>{
  return {
    id:tableProps.editData.id,
    rollId: tableProps.editData.rollId,
    goodId:tableProps.editData.goodId,
    name:tableProps.editData.name,
    nameAlias:tableProps.editData.nameAlias,
    goodPrice:tableProps.editData.goodPrice,
    goodImage:tableProps.editData.goodImage,
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
  if (!submitData.value.name || !submitData.value.nameAlias || !submitData.value.goodPrice) {
    ElMessage({type: 'error', message: '参数不能为空!'})
    return
  }
  if (tableProps.editData.id) {
    const apiRet = await http.post('/csgoRollGood/update', submitData.value)
    if (apiRet.ok) {
      ElMessage({type: 'success', message: apiRet.msg})
      await tableProps.fetchData()
    }
  } else {
    const apiRet = await http.post('/csgoRollGood/save', submitData.value)
    if (apiRet.ok) {
      ElMessage({type: 'success', message: apiRet.msg})
      await tableProps.fetchData()
    }
  }
}

const remove = async (row) => {
  ElMessageBox.confirm("确认删除?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(async () => {
    const apiRet = await http.get(`/csgoRollGood/remove/${row.id}`)
    if (apiRet.ok) {
      ElMessage({type: 'success', message: apiRet.msg})
      await tableProps.fetchData()
    }
  })
}

onMounted(() => {
  tableProps.editData.rollId = props.rowOjb.id.toString()
  tableProps.queryEntity.rollId = props.rowOjb.id.toString()
  tableProps.fetchData()
})

const emit = defineEmits(['update:rowOjb'])
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