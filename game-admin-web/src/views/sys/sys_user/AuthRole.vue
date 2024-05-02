<template>
  <el-dialog
      v-model="visible"
      draggable
      overflow
  >
    <el-container class="bbg-table">
      <el-header ref="header" class="bbg-table-header" height="auto">
        <el-row :gutter="10">
          <el-col :span="18">
            <el-row>
              <div class="bbg-table-header-input">
                <el-input v-model="tableProps.reqParams.queryEntity.name" placeholder="角色名"/>
              </div>
              <div class="bbg-table-header-input">
                当前角色: {{ roleName }}
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
                  height="440"
                  border show-overflow-tooltip>
          <el-table-column prop="id" label="主键"/>
          <el-table-column prop="name" label="角色名"/>
          <el-table-column prop="remark" label="角色描述"/>
          <el-table-column prop="enable" label="状态">
            <template #default="scope">
              {{ scope.row.enable ? '是' : '否' }}
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间"/>
          <el-table-column prop="updateTime" label="修改时间"/>
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="select(scope.row)">选择</el-button>
              <el-button link type="primary" size="small" @click="authMenu(scope.row)">菜单</el-button>
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
  <AuthMenu ref="authMenuRef"/>
</template>
<script setup>
import {http} from "@/core/axios/index.js";

const visible = ref(false)
const currentUserId = ref('')
const roleName = ref('');
const authMenuRef = ref(null)

const authRole = async (userId) => {
  currentUserId.value = userId;
  await tableProps.fetchData()
  let userRole = {
    userId: userId,
  }
  const apiRet = await http.post('/sysUser/getUserRole', userRole)
  roleName.value = apiRet.data ? apiRet.data.name : "无"
  visible.value = true;
}

const authMenu = async (row) =>{
  authMenuRef.value.authMenu(row)
}

const select = async (row) => {
  let userRole = {
    userId: currentUserId.value,
    roleId: row.id
  }
  const apiRet = await http.post('/sysUser/authUserRole', userRole)
  if (apiRet.ok) {
    roleName.value = row.name
    ElMessage({type: 'success', message: '授权角色成功!'})
  }
}

defineExpose({
  authRole
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
    const apiRet = await http.post('/sysRole/page', tableProps.reqParams)
    if (apiRet.ok) {
      tableProps.apiRet = apiRet
      tableProps.apiRet.totalRow = apiRet.data.totalRow
    }
  }
});
</script>
