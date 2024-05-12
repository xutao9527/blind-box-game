<template>
  <el-dialog
      v-model="visible"
      draggable
      overflow
      append-to-body
  >
    <el-container class="bbg-table">
      <el-header ref="header" class="bbg-table-header" height="auto">
        <el-row :gutter="10">
          <el-col :span="18">
            <el-row>
              <div class="bbg-table-header-input">
                <el-input v-model="tableProps.reqParams.queryEntity.itemName" placeholder="皮肤名称"/>
              </div>
              <div class="bbg-table-header-input">
                <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.type" :dict-id="'1784138822442606592'" placeholder="武器类型"/>
              </div>
            </el-row>
          </el-col>
          <el-col :span="6" style="display: flex;flex-direction: column ;justify-content:space-between">
            <el-row>
              <el-button class="bbg-table-header-control" icon="Search" @click="tableProps.fetchData">查询</el-button>
            </el-row>
          </el-col>
        </el-row>
      </el-header>
      <el-main class="bbg-table-main">
        <el-table class="bbg-table-main"
                  :data="tableProps.apiRet.data.records"
                  table-layout="auto"
                  @sortChange="tableProps.sortChange"
                  height="440"
                  border show-overflow-tooltip>
          <el-table-column prop="id" label="主键id"/>
          <el-table-column prop="itemName" label="皮肤名称" width="300"/>

          <el-table-column prop="imageUrl" label="图片" width="80">
            <template #default="scope">
              <el-image :src="scope.row.imageUrl" :preview-src-list="[scope.row.imageUrl]" preview-teleported/>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="价格"/>
          <el-table-column prop="cnyPrice" label="人名币价格" width="120" sortable="custom"/>
          <el-table-column prop="quantity" label="商品数量" width="110" sortable="custom"/>
          <el-table-column prop="typeName" label="类型名称" width="100"/>
          <el-table-column prop="marketHashName" label="皮肤市场名称" width="340"/>
          <el-table-column prop="exteriorName" label="外观名称" width="100"/>
          <el-table-column prop="qualityName" label="类别名称" width="100"/>
          <el-table-column prop="rarityName" label="品质名称"/>
          <el-table-column prop="editTime" label="编辑时间" width="200"/>
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="select(scope.row)">选择</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
      <el-footer class="bbg-table-footer">
        <el-row style="padding: 5px">
          <el-col style="display: grid;justify-content: right;">
            <el-pagination
                layout="total, prev, pager, next, jumper, sizes"
                :total="tableProps.apiRet.totalRow"
                :page-sizes="[10]"
                :default-current-page="tableProps.reqParams.page.pageNumber"
                :default-page-size="tableProps.reqParams.page.pageSize"
                @change="tableProps.pageChange"
            />
          </el-col>
        </el-row>
      </el-footer>
    </el-container>
  </el-dialog>
</template>
<script setup>
import {http} from "@/core/axios/index.js";

const visible = ref(false)
const selectData = async () => {
  if(tableProps.apiRet.totalRow === 0 ){
    await tableProps.fetchData()
  }
  visible.value = true;
}

const emit = defineEmits(['update:good'])
const props = defineProps({
  good: {
    required: true
  }
})


const select = async (row) => {
  props.good.goodId = row.id
  props.good.name = row.itemName
  props.good.nameAlias = row.marketHashName
  props.good.imageUrl = row.imageUrl
  props.good.price = row.cnyPrice
  props.good.type = row.type
  props.good.typeName = row.typeName
  ElMessage({type: 'success', message: '选择完成'})
  visible.value = false;
}

defineExpose({
  selectData
})

const tableProps = reactive({
  reqParams: {
    page: {
      pageNumber: 1,
      pageSize: 10,
    },
    queryEntity: {
      "expandProps": {}
    }
  },
  apiRet: {
    data: {},
    totalRow: 0,
  },
  pageChange: (currentPage, pageSize) => {
    tableProps.reqParams.page.pageNumber = currentPage;
    tableProps.reqParams.page.pageSize = pageSize;
    tableProps.fetchData()
  },
  fetchData: async () => {
    const apiRet = await http.post('/csgoGoods/page', tableProps.reqParams)
    if (apiRet.ok) {
      tableProps.apiRet = apiRet
      tableProps.apiRet.totalRow = apiRet.data.totalRow
    }
  },
  sortChange: async (column)=>{
    if(column.order === "descending"){
      tableProps.reqParams.queryEntity.expandProps.orderField = {[column.prop] : 'descending'}
    }else if(column.order === "ascending"){
      tableProps.reqParams.queryEntity.expandProps.orderField = {[column.prop] : 'ascending'}
    }else{
      delete tableProps.reqParams.queryEntity.expandProps.orderField;
    }
    await tableProps.fetchData()
  }
});
</script>
