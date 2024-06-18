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
              <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.payType" ref="payTypeRef" :tag="'third_pay_type'"
                               placeholder="支付类型"/>
            </div>
            <div class="bbg-table-header-input">
              <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.payCurrency" ref="payCurrencyRef" :tag="'third_pay_currency'"
                               placeholder="支付币种"/>
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
                size="small"
                :data="tableProps.apiRet.data.records"
                :height="tableDynamicHeight"
                table-layout="auto"
                @sortChange="tableProps.sortChange"
                border show-overflow-tooltip>
        <el-table-column prop="id" label="主键" width="155"/>
        <el-table-column prop="payName" label="支付名称" width="120"/>
<!--        <el-table-column prop="payNameAlias" label="支付别名" width="185"/>-->
        <el-table-column prop="payImageUrl" label="支付图标" width="80">
          <template #default="scope">
            <el-image
                :fit="'contain'" style="width: 20px;height: 20px"
                lazy :src="scope.row.payImageUrl" :preview-src-list="[scope.row.payImageUrl]" preview-teleported/>
          </template>
        </el-table-column>
        <el-table-column prop="payCode" label="支付编码"  width="80"/>
        <el-table-column prop="payType" label="支付类型"  width="80">
          <template #default="scope">
            {{ payTypeRef.getLabel(scope.row.payType) }}
          </template>
        </el-table-column>
        <el-table-column prop="payCurrency" label="支付币种" width="0">
          <template #default="scope">
            {{ payCurrencyRef.getLabel(scope.row.payCurrency) }}
          </template>
        </el-table-column>
        <el-table-column prop="payAmountLimit" label="支付限额" width="100"/>
        <el-table-column prop="sort" label="排序" width="40"/>
        <el-table-column prop="enable" label="状态" width="40">
          <template #default="scope">
            {{ scope.row.enable ? '启用' : '停用' }}
          </template>
        </el-table-column>

        <el-table-column prop="payRemark" label="支付描述"  width="130"/>
        <el-table-column prop="callEngine" label="调用引擎" width="100">
          <template #default="scope">
            {{ payEngineDictObject.getLabel(scope.row.callEngine) }}
          </template>
        </el-table-column>
        <el-table-column prop="callArg" label="调用参数" width="80"/>
        <el-table-column prop="callContent" label="调用引擎内容" width="100"/>
        <el-table-column prop="callReload" label="调用引擎重载" width="90">
          <template #default="scope">
            {{ scope.row.callReload ? '启用' : '停用' }}
          </template>
        </el-table-column>
        <el-table-column prop="callbackEngine" label="回调引擎"  width="100">
          <template #default="scope">
            {{ payEngineDictObject.getLabel(scope.row.callbackEngine) }}
          </template>
        </el-table-column>
        <el-table-column prop="callbackArg" label="回调参数" width="80"/>
        <el-table-column prop="callbackContent" label="回调引擎内容" width="100"/>
        <el-table-column prop="callbackReload" label="回调引擎重载" width="90">
          <template #default="scope">
            {{ scope.row.callbackReload ? '启用' : '停用' }}
          </template>
        </el-table-column>
        <el-table-column prop="callbackIp" label="回调白名单"  width="100"/>
        <el-table-column prop="queryEngine" label="查询引擎"  width="100">
          <template #default="scope">
            {{ payEngineDictObject.getLabel(scope.row.queryEngine) }}
          </template>
        </el-table-column>
        <el-table-column prop="queryArg" label="查询参数"  width="80"/>
        <el-table-column prop="queryContent" label="查询引擎内容"  width="100"/>
        <el-table-column prop="queryReload" label="查询引擎重载" width="90">
          <template #default="scope">
            {{ scope.row.queryReload ? '待重载' : '已编译' }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="140"/>
        <el-table-column prop="updateTime" label="修改时间" width="140"/>
        <el-table-column fixed="right" label="操作" width="100">
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
import {DictObject} from "@/core/dict/index.js";

const payTypeRef = ref(null)
const payCurrencyRef = ref(null)
const header = ref(null)
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

const payEngineDictObject = ref(null);
onMounted(async () => {
  payEngineDictObject.value = await DictObject.create('third_pay_engine');

  await tableProps.fetchData()
})

const add = () => {
  emits('activeRightTabs', 'form', false);
}

const edit = (row) => {
  emits('activeRightTabs', 'form', false, row.id);
}

const remove = async (row) => {
  const apiRet = await http.get(`/bizPayPlatform/remove/${row.id}`)
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
    const apiRet = await http.post('/bizPayPlatform/page', tableProps.reqParams)
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
