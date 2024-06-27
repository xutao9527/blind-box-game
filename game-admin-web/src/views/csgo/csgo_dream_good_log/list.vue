<template>
  <el-container class="bbg-table">
    <el-header ref="header" class="bbg-table-header" height="auto">
      <el-row :gutter="10">
        <el-col :span="18">
          <el-row>
            <div class="bbg-table-header-input">
              <el-input v-model="tableProps.reqParams.queryEntity.id" placeholder="编号"/>
            </div>
            <div class="bbg-table-header-input" style="width: 420px">
              <el-date-picker
                  v-model="tableProps.reqParams.queryEntity.expandProps.createTime"
                  type="datetimerange"
                  start-placeholder="Start date"
                  end-placeholder="End date"
                  value-format="YYYY-MM-DD HH:mm:ss"
              />
            </div>
          </el-row>
        </el-col>
        <el-col :span="6" style="display: flex;flex-direction: column ;justify-content:space-between">
          <el-row>
<!--            <el-button class="bbg-table-header-control" icon="Plus" @click="add">新增</el-button>-->
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
                :default-sort="{ prop: 'id', order: 'descending' }"
                border show-overflow-tooltip>
        <el-table-column prop="id" label="主键" sortable="custom" width="190"/>
        <el-table-column prop="userId" label="用户编号"  width="190"/>
        <el-table-column prop="userNickName" label="用户昵称"/>
<!--        <el-table-column prop="userPhoto" label="用户头像"/>-->
        <el-table-column prop="dreamPrice" label="追梦金额"/>
        <el-table-column prop="dreamGoodProbability" label="追梦概率"/>
        <el-table-column prop="dreamIsWin" label="追梦结果">
          <template #default="scope">
            {{ scope.row.dreamIsWin ? '成功' : '失败' }}
          </template>
        </el-table-column>
<!--        <el-table-column prop="boxId" label="箱子编号"/>-->
<!--        <el-table-column prop="goodId" label="商品编号"/>-->
        <el-table-column prop="goodName" label="商品名称"/>
<!--        <el-table-column prop="goodNameAlias" label="商品别名"/>-->
<!--        <el-table-column prop="goodImageUrl" label="商品图片"/>-->
        <el-table-column prop="goodPrice" label="商品价格"/>
        <el-table-column prop="dreamGoodTime" label="追梦时间"  width="170"/>
<!--        <el-table-column fixed="right" label="操作">-->
<!--          <template #default="scope">-->
<!--            <el-button link type="primary" size="small" @click="edit(scope.row)">编辑</el-button>-->
<!--            <el-button link type="primary" size="small" @click="remove(scope.row)">删除</el-button>-->
<!--          </template>-->
<!--        </el-table-column>-->
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
  ElMessageBox.confirm("确认删除?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(async () => {
    const apiRet = await http.get(`/csgoDreamGoodLog/remove/${row.id}`)
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
    data: {},
    totalRow: 0,
  },
  pageChange: (currentPage, pageSize) => {
    tableProps.reqParams.page.pageNumber = currentPage;
    tableProps.reqParams.page.pageSize = pageSize;
    tableProps.fetchData()
  },
  fetchData: async () => {
    const apiRet = await http.post('/csgoDreamGoodLog/page', tableProps.reqParams)
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
