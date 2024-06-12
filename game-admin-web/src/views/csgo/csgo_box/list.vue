<template>
  <el-container class="bbg-table">
    <el-header ref="header" class="bbg-table-header" height="auto">
      <el-row :gutter="10">
        <el-col :span="18">
          <el-row>
            <div class="bbg-table-header-input">
              <el-input v-model="tableProps.reqParams.queryEntity.name" placeholder="箱子名称"/>
            </div>
            <div class="bbg-table-header-input">
              <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.type" ref="dictTypeRef"
                               :tag="'csgo_box_type'" placeholder="箱子类型"/>
            </div>
            <div class="bbg-table-header-input">
              <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.group" ref="dictGroupRef"
                               :tag="'csgo_box_group'" placeholder="箱子分组"/>
            </div>
          </el-row>
        </el-col>
        <el-col :span="6" style="display: flex;flex-direction: column ;justify-content:space-between">
          <el-row>
            <el-button class="bbg-table-header-control" icon="Plus" @click="add">新增</el-button>
          </el-row>
          <el-row>
            <el-button class="bbg-table-header-control" icon="Search" @click="tableProps.fetchData">查询</el-button>
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
        <el-table-column type="expand" label="#">
          <template #default="props">
            <BoxGood v-model:row-ojb="props.row"/>
          </template>
        </el-table-column>
        <el-table-column prop="id" label="主键"/>
        <el-table-column prop="name" label="箱子名称"/>
        <el-table-column prop="nameAlias" label="箱子别名"/>
        <el-table-column prop="imageUrl" label="图片地址">
          <template #default="scope">
            <el-tooltip :content="scope.row.imageUrl" placement="top">
              <el-image lazy :src="scope.row.imageUrl" :preview-src-list="[scope.row.imageUrl]" preview-teleported
                        style="width: 50px;height: 50px"/>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="箱子类型" sortable="custom">
          <template #default="scope">
            {{ dictTypeRef.getLabel(scope.row.type) }}
          </template>
        </el-table-column>
        <el-table-column prop="group" label="箱子分组">
          <template #default="scope">
            {{ dictGroupRef.getLabel(scope.row.group) }}
          </template>
        </el-table-column>
        <el-table-column prop="price" label="箱子价格"/>
        <el-table-column prop="enable" label="状态">
          <template #default="scope">
            {{ scope.row.enable ? '启动' : '停用' }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间"/>
        <el-table-column prop="updateTime" label="修改时间"/>
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="edit(scope.row)">编辑</el-button>
            <el-button link type="primary" size="small" @click="remove(scope.row)">删除</el-button>
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
import BoxGood from "@/views/csgo/csgo_box/BoxGood.vue";


const dictTypeRef = ref(null)
const dictGroupRef = ref(null)
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

const add = () => {
  emits('activeRightTabs', 'form', false);
}

const edit = (row) => {
  emits('activeRightTabs', 'form', false, row.id);
}

const remove = async (row) => {
  const apiRet = await http.get(`/csgoBox/remove/${row.id}`)
  if (apiRet.ok) {
    ElMessage({type: 'success', message: apiRet.msg})
    await tableProps.fetchData()
  }
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
    data: {},
    totalRow: 0,
  },
  pageChange: (currentPage, pageSize) => {
    tableProps.reqParams.page.pageNumber = currentPage;
    tableProps.reqParams.page.pageSize = pageSize;
    tableProps.fetchData()
  },

  fetchData: async () => {
    const apiRet = await http.post('/csgoBox/page', tableProps.reqParams)
    if (apiRet.ok) {
      tableProps.apiRet = apiRet
      tableProps.apiRet.totalRow = apiRet.data.totalRow
    }
  },
  sortChange: async (column) => {
    console.log(column)
    if (column.order === "descending") {
      tableProps.reqParams.queryEntity.expandProps.orderField = {[column.prop]: 'descending'}
    } else if (column.order === "ascending") {
      tableProps.reqParams.queryEntity.expandProps.orderField = {[column.prop]: 'ascending'}
    } else {
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
