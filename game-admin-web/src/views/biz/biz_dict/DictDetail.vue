<template>
  <div class="bbg_sub_table">
    <el-form label-position="right" :inline="true" size="small">
      <el-form-item label="名称">
        <el-input v-model="tableProps.editData.label" placeholder="名称"/>
      </el-form-item>
      <el-form-item label="别名">
        <el-input v-model="tableProps.editData.labelAlias" placeholder="别名"/>
      </el-form-item>
      <el-form-item label="具体值">
        <el-input v-model="tableProps.editData.value" placeholder="具体值"/>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="tableProps.editData.sort" placeholder="排序"/>
      </el-form-item>
      <el-form-item label="状态">
        <el-switch v-model="tableProps.editData.enable" placeholder="状态"/>
      </el-form-item>
      <el-form-item>
        <el-button icon="Plus" @click="submit">{{ submitText }}</el-button>
        <el-button icon="Close" @click="toEdit(undefined)">取消</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableProps.apiRet.data" size="small">
      <el-table-column prop="label" label="名称"/>
      <el-table-column prop="labelAlias" label="别名"/>
      <el-table-column prop="value" label="具体值"/>
      <el-table-column prop="sort" label="排序"/>
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
  </div>
</template>
<script setup>

import {http} from "@/core/axios/index.js";

const submitText = computed(() => {
  return tableProps.editData.id ? '修改' : '添加'
})

const tableProps = reactive({
  queryEntity: {},
  editData: {
    enable: true
  },
  apiRet: {
    data: [],
  },
  fetchData: async () => {
    let apiRet = await http.post('/bizDictDetail/list', tableProps.queryEntity)
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
  if (!tableProps.editData.label || !tableProps.editData.labelAlias || !tableProps.editData.value) {
    ElMessage({type: 'error', message: '参数不能为空!'})
    return
  }
  if (tableProps.editData.id) {
    const apiRet = await http.post('/bizDictDetail/update', tableProps.editData)
    if (apiRet.ok) {
      ElMessage({type: 'success', message: apiRet.msg})
      await tableProps.fetchData()
    }
  } else {
    const apiRet = await http.post('/bizDictDetail/save', tableProps.editData)
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
    const apiRet = await http.get(`/bizDictDetail/remove/${row.id}`)
    if (apiRet.ok) {
      ElMessage({type: 'success', message: apiRet.msg})
      await tableProps.fetchData()
    }
  })
}

onMounted(() => {
  tableProps.editData.dictId = props.rowOjb.id.toString()
  tableProps.queryEntity.dictId = props.rowOjb.id.toString()
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