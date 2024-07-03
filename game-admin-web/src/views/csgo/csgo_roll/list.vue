<template>
  <el-container class="bbg-table">
    <el-header ref="header" class="bbg-table-header" height="auto">
      <el-row :gutter="10">
        <el-col :span="18">
          <el-row>
            <div class="bbg-table-header-input">
              <el-input v-model="tableProps.reqParams.queryEntity.rollTitle" placeholder="房间标题"/>
            </div>
            <div class="bbg-table-header-input">
              <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.rollType" ref="rollTypeRef" :tag="'csgo_roll_type'" placeholder="撸房类型"/>
            </div>
            <div class="bbg-table-header-input">
              <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.rollModel" ref="rollModelRef" :tag="'csgo_roll_model'" placeholder="撸房模式"/>
            </div>
            <div class="bbg-table-header-input">
              <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.status" ref="rollStatusRef" :tag="'csgo_roll_status'" placeholder="撸房状态"/>
            </div>
            <TenantIdSearchSelect v-model:value="tableProps.reqParams.queryEntity.tenantId"/>
<!--            <div class="bbg-table-header-input" style="width: 420px">-->
<!--              <el-date-picker-->
<!--                  v-model="tableProps.reqParams.queryEntity.expandProps.createTime"-->
<!--                  type="datetimerange"-->
<!--                  start-placeholder="Start date"-->
<!--                  end-placeholder="End date"-->
<!--                  value-format="YYYY-MM-DD HH:mm:ss"-->
<!--              />-->
<!--            </div>-->
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
            <RollGood v-model:row-ojb="props.row"/>
          </template>
        </el-table-column>
        <el-table-column prop="id" label="编号" width="185"/>
<!--        <el-table-column prop="createUserId" label="创建用户编号"/>-->
<!--        <el-table-column prop="createUserName" label="创建用户名称"/>-->
<!--        <el-table-column prop="createUserPhoto" label="创建用户头像"/>-->
        <el-table-column prop="rollTitle" label="房间标题" width="150"/>
        <el-table-column prop="rollRemark" label="描述" width="200"/>
        <el-table-column prop="rollType" label="撸房类型" width="90">
          <template #default="scope">
            {{rollTypeRef.getLabel(scope.row.rollType)}}
          </template>
        </el-table-column>
        <el-table-column prop="rollModel" label="撸房模式" width="90">
          <template #default="scope">
            {{rollModelRef.getLabel(scope.row.rollModel)}}
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="房间开始" width="165"/>
        <el-table-column prop="endTime" label="房间结束" width="165"/>
        <el-table-column prop="peopleNumber" label="房间人数" width="90"/>
<!--        <el-table-column prop="joinCondition" label="加入房间条件"/>-->
<!--        <el-table-column prop="joinCode" label="加入房间密码"/>-->
        <el-table-column prop="status" label="房间状态" width="85">
          <template #default="scope">
            {{rollStatusRef.getLabel(scope.row.status)}}
          </template>
        </el-table-column>
        <el-table-column prop="enable" label="状态" width="60">
          <template #default="scope">
              {{scope.row.enable?'启用':'停用'}}
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="60"/>
        <TenantIdColumn/>
        <el-table-column prop="createTime" label="创建时间" width="165"/>
        <el-table-column prop="updateTime" label="修改时间" width="165"/>
        <el-table-column fixed="right" label="操作" width="100">
          <template #default="scope" >
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
import RollGood from "./RollGood.vue";

const rollTypeRef = ref(null)
const rollModelRef = ref(null)
const rollStatusRef = ref(null)

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
    const apiRet = await http.get(`/csgoRoll/remove/${row.id}`)
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
      "expandProps":{}
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
    const apiRet = await http.post('/csgoRoll/page', tableProps.reqParams)
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
