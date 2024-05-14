<template>
  <div class="bbg_sub_table">
    <el-form label-position="right" :inline="true" size="small">
      <el-form-item label="名称">
        <el-input v-model="tableProps.editData.name" placeholder="商品名称"/>
      </el-form-item>
      <el-form-item label="别名">
        <el-input v-model="tableProps.editData.nameAlias" placeholder="商品别名"/>
      </el-form-item>
      <el-form-item label="价格">
        <el-input v-model="tableProps.editData.price" placeholder="商品价格"/>
      </el-form-item>
      <template v-if="props.rowOjb.type === '3'">
        <el-form-item label="费用">
          <el-input v-model="tableProps.editData.rate" placeholder="手续费比率"/>
        </el-form-item>
      </template>
      <template v-else>
        <el-form-item label="概率">
          <el-input v-model="tableProps.editData.rate" placeholder="获得概率"/>
        </el-form-item>
      </template>
      <el-form-item label="排序">
        <el-input v-model="tableProps.editData.sort" placeholder="排序"/>
      </el-form-item>
      <el-form-item>
        <el-switch v-model="tableProps.editData.enable" placeholder="状态"/>
      </el-form-item>
      <el-form-item>
        <el-button icon="Position" @click="goodsSelectRef.selectData()">选择</el-button>
        <el-button icon="Plus" @click="submit">{{ submitText }}</el-button>
        <el-button icon="Close" @click="toEdit(undefined)">取消</el-button>
        <el-button :type="enableButtonType" icon="Open" @click="enableBox">
          {{ props.rowOjb.enable ? '停用' : '启用' }}{{ sumRate }}
        </el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableProps.apiRet.data" size="small">
      <el-table-column prop="name" label="商品名称"/>
      <el-table-column prop="nameAlias" label="商品别名"/>
      <el-table-column prop="imageUrl" label="图片" width="60">
        <template #default="scope">
          <el-image :src="scope.row.imageUrl" :preview-src-list="[scope.row.imageUrl]" preview-teleported/>
        </template>
      </el-table-column>
      <el-table-column prop="price" label="商品价格"/>
      <template v-if="props.rowOjb.type === '3'">
        <el-table-column prop="rate" label="手续费"/>
      </template>
      <template v-else>
        <el-table-column prop="rate" label="获得概率"/>
      </template>
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
    <goods-select ref="goodsSelectRef" v-model:good="tableProps.editData"/>
  </div>
</template>
<script setup>

import {http} from "@/core/axios/index.js";
import GoodsSelect from "@/views/csgo/csgo_box/GoodsSelect.vue";

const enableButtonType = computed(() => {
  if (props.rowOjb.type === '1' || props.rowOjb.type === '2') {
    return sumRate.value === 100 ? 'success' : 'danger'
  } else {
    return 'success'
  }
})

const sumRate = computed(() => {
  if (props.rowOjb.type === '1' || props.rowOjb.type === '2') {
    return +tableProps.apiRet.data.reduce((accumulator, currentObject) => {
      if (currentObject.enable) {
        return accumulator + currentObject.rate;
      } else {
        return accumulator
      }
    }, 0);
  } else {
    return ''
  }
})

const enableBox = async () => {
  const data = {
    id: props.rowOjb.id.toString(),
  }
  if (props.rowOjb.enable) {
    data.enable = false;
    const apiRet = await http.post('/csgoBox/update', data)
    if (apiRet.ok) {
      props.rowOjb.enable = false;
      ElMessage({type: 'success', message: '停用成功!'})
    }
  } else {
    if (props.rowOjb.type === '1' || props.rowOjb.type === '2') {
      console.log(sumRate.value)
      if (sumRate.value === 100) {
        data.enable = true;
        props.rowOjb.enable = true;
        const apiRet = await http.post('/csgoBox/update', data)
        if (apiRet.ok) {
          ElMessage({type: 'success', message: '启动成功!'})
        }
      } else {
        ElMessage({type: 'error', message: '总概率之和不等于100,不能启用'})
      }
    } else {
      data.enable = true;
      props.rowOjb.enable = true;
      const apiRet = await http.post('/csgoBox/update', data)
      if (apiRet.ok) {
        ElMessage({type: 'success', message: '启动成功!'})
      }
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
  console.log(props.rowOjb)
  tableProps.editData.boxId = props.rowOjb.id.toString()
  tableProps.queryEntity.boxId = props.rowOjb.id.toString()
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