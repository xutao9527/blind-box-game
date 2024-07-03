<template>
  <el-container class="bbg-table">
    <el-header ref="header" class="bbg-table-header" height="auto">
      <el-row :gutter="10">
        <el-col :span="18">
          <el-row>
            <div class="bbg-table-header-input">
              <el-input v-model="tableProps.reqParams.queryEntity.itemName" placeholder="皮肤名称"/>
            </div>
            <div class="bbg-table-header-input">
              <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.type" ref="dictGoodTypeRef" :tag="'csgo_good_type'" placeholder="武器类型"/>
            </div>
          </el-row>
        </el-col>
        <el-col :span="6" style="display: flex;flex-direction: column ;justify-content:space-between">
          <el-row>
          </el-row>
          <el-row>
            <el-button class="bbg-table-header-control" icon="Search" @click="tableProps.fetchData">查询</el-button>
            <el-button class="bbg-table-header-control" icon="Magnet" @click="syncData">同步</el-button>
          </el-row>
        </el-col>
      </el-row>
    </el-header>
    <el-main class="bbg-table-main">
      <el-table class="bbg-table-main"
                :data="tableProps.apiRet.data.records"
                :height="tableDynamicHeight"
                table-layout="auto"
                @sortChange="tableProps.sortChange"
                border show-overflow-tooltip>
        <el-table-column prop="id" label="主键id"/>
        <el-table-column prop="itemName" label="皮肤名称" width="340"/>
        <el-table-column prop="marketHashName" label="皮肤市场名称" width="340"/>
        <el-table-column prop="imageUrl" label="图片" width="80" :show-overflow-tooltip="false">
          <template #default="scope">
            <el-image lazy :src="scope.row.imageUrl" :preview-src-list="[scope.row.imageUrl]" preview-teleported />
          </template>
        </el-table-column>
        <el-table-column prop="price" label="价格"/>
        <el-table-column prop="cnyPrice" label="人名币价格" width="120" sortable="custom"/>
        <el-table-column prop="quantity" label="商品数量" width="110" sortable="custom"/>
        <el-table-column prop="typeName" label="类型名称" width="100"/>
        <el-table-column prop="exteriorName" label="外观名称" width="100"/>
        <el-table-column prop="qualityName" label="类别名称" width="100"/>
        <el-table-column prop="rarityName" label="品质名称"/>
        <el-table-column prop="editTime" label="编辑时间" width="200"/>
      </el-table>
    </el-main>
    <el-footer class="bbg-table-footer">
      <el-row style="padding: 5px">
        <el-col style="display: grid;justify-content: right;">
          <el-pagination
              layout="total, prev, pager, next, jumper, sizes"
              :total="tableProps.apiRet.totalRow"
              :page-sizes="[15,20,50,100]"
              :default-current-page="tableProps.reqParams.page.pageNumber"
              :default-page-size="tableProps.reqParams.page.pageSize"
              @change="tableProps.pageChange"
          />
        </el-col>
      </el-row>
    </el-footer>
  </el-container>
</template>

<script setup>
import {useEventListener, useResizeObserver, useWindowSize} from "@vueuse/core";
import {http} from "@/core/axios";
import emitter from "@/core/mitt/";

const dictGoodTypeRef = ref(null)
const header = ref(null);
const tableDynamicHeight = ref(0)

const scope = effectScope()
scope.run(() => {
  const dynamicHeightUpdate = () => {
    let top = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--header-height'))
    tableDynamicHeight.value = useWindowSize().height.value - (top + header.value.$el.offsetHeight + 51 + 32 + 33);
    emitter.emit('bbgWindowResize');
  }
  useResizeObserver(header, () => {
    dynamicHeightUpdate()
  })
  useEventListener('resize', () => {
    dynamicHeightUpdate()
  })
})

onMounted(() => {
  tableProps.fetchData()
})

const syncData = async () => {
  const apiRet = await http.get(`/csgoGoods/syncData`)
  if(apiRet && apiRet.ok){
    if(apiRet.data){
      ElMessage({type: 'success', message: '同步数据通知完成!'})
    }else{
      ElMessage({type: 'warning', message: '同步数据进行中,稍后再试!'})
    }
  }
}

const add = () => {
  emits('activeRightTabs', 'form', false);
}

const edit = (row) => {
  emits('activeRightTabs', 'form', false, row.id);
}

const remove = async (row) => {
  ElMessageBox.confirm("确认删除?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(async () => {
    const apiRet = await http.get(`/csgoGoods/remove/${row.id}`)
    if (apiRet.ok) {
      ElMessage({type: 'success', message: apiRet.msg})
      await tableProps.fetchData()
    }
  })
}

const tableProps = reactive({
  reqParams: {
    page: {
      pageNumber: 1,
      pageSize: 15,
    },
    queryEntity: {
      "expandProps": {}
    }
  },
  apiRet: {
    data: [],
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

const emits = defineEmits(['activeRightTabs']);
defineExpose({
  tableProps
})
</script>
