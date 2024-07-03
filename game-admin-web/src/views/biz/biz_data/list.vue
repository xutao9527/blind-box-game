<template>
  <el-container class="bbg-table">
    <el-header ref="header" class="bbg-table-header" height="auto">
      <el-row :gutter="10">
        <el-col :span="18">
          <el-row>
            <div class="bbg-table-header-input">
              <el-input v-model="tableProps.reqParams.queryEntity.id" placeholder="编号"/>
            </div>
            <div class="bbg-table-header-input">
              <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.type" ref="dataTypeRef"
                               :tag="'biz_data_type'" placeholder="数据类型"/>
            </div>
            <TenantIdSearchSelect v-model:value="tableProps.reqParams.queryEntity.tenantId"/>
          </el-row>
        </el-col>
        <el-col :span="6" style="display: flex;flex-direction: column ;justify-content:space-between">
          <el-row>
            <el-button class="bbg-table-header-control" icon="Plus" @click="add">新增</el-button>
            <el-button class="bbg-table-header-control" icon="DocumentAdd" @click="importNickNameRef.importData()">导入昵称</el-button>
            <el-button class="bbg-table-header-control" icon="DocumentAdd" @click="importProfilePhotoRef.importData()">导入头像</el-button>
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
        <el-table-column prop="id" label="主键" width="190"/>
        <el-table-column prop="type" label="数据类型" width="170">
          <template #default="scope">
            {{ dataTypeRef.getLabel(scope.row.type) }}
          </template>
        </el-table-column>
        <el-table-column prop="value" label="数据值" width="240" >
          <template #default="scope">
            <template v-if="dataTypeRef.getLabel(scope.row.type) === '人物头像' || dataTypeRef.getLabel(scope.row.type) === '首页图片'">
              <el-tooltip :content="scope.row.value" placement="top">
               <el-image lazy :src="scope.row.value" :preview-src-list="[scope.row.value]" preview-teleported :fit="'contain'" style="width: 50px;height: 50px"/>
              </el-tooltip>
            </template>
            <template v-else>
              {{ scope.row.value }}
            </template>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序"  width="60"/>
        <el-table-column prop="remark" label="数据描述"/>
        <el-table-column prop="enable" label="状态" width="60">
          <template #default="scope">
            {{ scope.row.enable ? '启用' : '停用' }}
          </template>
        </el-table-column>
        <TenantIdColumn/>
        <el-table-column prop="createTime" label="创建时间" width="170"/>
        <el-table-column prop="updateTime" label="修改时间" width="170"/>
        <el-table-column fixed="right" label="操作" width="120">
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
  <ImportNickName ref="importNickNameRef"/>
  <ImportProfilePhoto ref="importProfilePhotoRef"/>
</template>

<script setup>
import {useEventListener, useResizeObserver, useWindowSize} from "@vueuse/core";
import {http} from "@/core/axios";
import emitter from "@/core/mitt/";
import ImportNickName from "@/views/biz/biz_data/ImportNickName.vue";
import ImportProfilePhoto from "@/views/biz/biz_data/ImportProfilePhoto.vue";

const importNickNameRef = ref(null)
const importProfilePhotoRef = ref(null)

const dataTypeRef = ref(null)
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
  emitter.on('bizDataFetchData', () => {
    tableProps.fetchData()
  })
})

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
    const apiRet = await http.get(`/bizData/remove/${row.id}`)
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
    const apiRet = await http.post('/bizData/page', tableProps.reqParams)
    if (apiRet.ok) {
      tableProps.apiRet = apiRet
      tableProps.apiRet.totalRow = apiRet.data.totalRow
    }
  },
  sortChange: async (column) => {
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
