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
                <el-input v-model="tableProps.reqParams.name" placeholder="皮肤名称"/>
              </div>
              <div class="bbg-table-header-input">
                <bbg-dict-select v-model:value="tableProps.reqParams.type" :tag="'csgo_good_type'"
                                 placeholder="武器类型"/>
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
          <el-table-column prop="name" label="商品名称" width="300"/>
          <el-table-column prop="imageUrl" label="图片" width="80">
            <template #default="scope">
              <el-image :src="scope.row.imageUrl" lazy :preview-src-list="[scope.row.imageUrl]" preview-teleported/>
            </template>
          </el-table-column>
          <el-table-column prop="typeName" label="类型名称"/>
          <el-table-column prop="price" label="商品价格" width="120" sortable="custom"/>
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
                :default-current-page="tableProps.reqParams.pageNumber"
                :default-page-size="tableProps.reqParams.pageSize"
                @change="tableProps.pageChange"
            />
          </el-col>
        </el-row>
      </el-footer>
    </el-container>
  </el-dialog>
</template>
<script setup>
import {dreamMock} from "@/views/mock/js/dreamDto.js";

const visible = ref(false)
const selectData = async () => {
  if (tableProps.apiRet.totalRow === 0) {
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
  props.good.id = row.id
  props.good.imageUrl = row.imageUrl
  props.good.price = row.price
  props.good.rate = row.rate
  ElMessage({type: 'success', message: '选择完成'})
  visible.value = false;
}

defineExpose({
  selectData
})

onMounted(async () => {
  await tableProps.fetchData();
})

const tableProps = reactive({
  reqParams: dreamMock.dreamListReq,
  apiRet: {
    data: [],
    totalRow: 0,
  },
  pageChange: (currentPage, pageSize) => {
    tableProps.reqParams.pageNumber = currentPage;
    tableProps.reqParams.pageSize = pageSize;
    tableProps.fetchData()
  },
  fetchData: async () => {
    await dreamMock.dreamList()
    tableProps.apiRet.data = dreamMock.dreamListRes.dreamGoodsPage
    tableProps.apiRet.totalRow = dreamMock.dreamListRes.dreamGoodsPage.totalRow

  },
  sortChange: async (column) => {
    if (column.order === "descending") {
      tableProps.reqParams.expandProps.orderField = {[column.prop]: 'descending'}
    } else if (column.order === "ascending") {
      tableProps.reqParams.expandProps.orderField = {[column.prop]: 'ascending'}
    } else {
      delete tableProps.reqParams.expandProps.orderField;
    }
    await tableProps.fetchData()
  }
});
</script>
