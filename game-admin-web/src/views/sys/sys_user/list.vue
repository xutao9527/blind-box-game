<template>
  <el-container class="bbg-table">
    <el-header ref="header" class="bbg-table-header" height="auto">
      <el-row :gutter="10">
        <el-col :span="18">
          <el-row>
            <div class="bbg-table-header-input">
              <el-input v-model="tableProps.reqParams.queryEntity.account" placeholder="账号" clearable/>
            </div>
            <div class="bbg-table-header-input">
              <el-input v-model="tableProps.reqParams.queryEntity.mobile" placeholder="手机" clearable/>
            </div>
            <TenantIdSearchSelect v-model:value="tableProps.reqParams.queryEntity.tenantId" :include-top-tenant="true"/>
          </el-row>
        </el-col>
        <el-col :span="6" style="display: flex;flex-direction: column ;justify-content:space-between">
          <el-row>
            <el-button class="bbg-table-header-control" icon="Plus" @click="add" v-has="'sysUser_save'">新增</el-button>
          </el-row>
          <el-row>
            <el-button class="bbg-table-header-control" icon="Search" v-has="'sysUser_page'" @click="tableProps.fetchData">查询</el-button>
          </el-row>
        </el-col>
      </el-row>
    </el-header>
    <el-main class="bbg-table-main">
        <el-table class="bbg-table-main"
                  :data="tableProps.apiRet.data.records"
                  :height="tableDynamicHeight"
                  table-layout="auto"
                  border show-overflow-tooltip>
        <el-table-column prop="id" label="主键"/>
        <el-table-column prop="mobile" label="手机"/>
        <el-table-column prop="account" label="账号"/>
        <el-table-column prop="password" label="密码"/>
        <el-table-column prop="superAdmin" label="超管">
        <template #default="scope">
            {{scope.row.superAdmin?'是':'否'}}
        </template>
        </el-table-column>
        <el-table-column prop="enable" label="状态">
          <template #default="scope">
            {{ scope.row.enable ? '启动' : '停用' }}
          </template>
        </el-table-column>
        <TenantIdColumn/>
        <el-table-column prop="createTime" label="创建时间"/>
        <el-table-column prop="updateTime" label="修改时间"/>
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button link type="primary" size="small" v-has="'sysUser_authUserRole'" @click="auth(scope.row)">授权</el-button>
            <el-button link type="primary" size="small" v-has="'sysUser_update'" @click="edit(scope.row)">编辑</el-button>
            <el-button link type="primary" size="small" v-has="'sysUser_remove_id'" @click="remove(scope.row)">删除</el-button>
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
  <AuthRole ref="authRoleRef"/>
</template>

<script setup>
import {useEventListener, useResizeObserver, useWindowSize} from "@vueuse/core";
import AuthRole from "./AuthRole.vue";
import {http} from "@/core/axios";
import emitter from "@/core/mitt/";
import TenantIdColumn from "@/components/tenant/TenantIdColumn.vue";

const authRoleRef = ref(null)
const auth = (row) => {
  authRoleRef.value.authRole(row)
}

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


onMounted(async () => {
  await tableProps.fetchData()
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
    const apiRet = await http.get(`/sysUser/remove/${row.id}`)
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
    const apiRet = await http.post('/sysUser/page', tableProps.reqParams)
    if (apiRet.ok) {
      tableProps.apiRet = apiRet
      tableProps.apiRet.totalRow = apiRet.data.totalRow
    }
  }
});

const emits = defineEmits(['activeRightTabs']);
defineExpose({
  tableProps
})
</script>
