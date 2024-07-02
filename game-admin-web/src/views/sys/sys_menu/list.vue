<template>
  <el-container class="bbg-table">
    <el-header ref="header" class="bbg-table-header" height="auto">
      <el-row :gutter="10">
        <el-col :span="18">
          <el-row>
            <div class="bbg-table-header-input">
              <el-input v-model="tableProps.reqParams.queryEntity.title" placeholder="菜单标题" clearable/>
            </div>
            <div class="bbg-table-header-input">
              <el-input v-model="tableProps.reqParams.queryEntity.path" placeholder="请求路径" clearable/>
            </div>
            <div class="bbg-table-header-input">
              <BbgMenuSelect v-model:value="tableProps.reqParams.queryEntity.parentId" :is-panel="false" :check-strictly="true"/>
            </div>
            <div class="bbg-table-header-input">
              <el-select v-model="tableProps.reqParams.queryEntity.type"
                         placeholder="菜单类型"
                         clearable
                         filterable>
                <el-option label="菜单" value="1"/>
                <el-option label="接口" value="2"/>
              </el-select>
            </div>
          </el-row>
        </el-col>
        <el-col :span="6" style="display: flex;flex-direction: column ;justify-content:space-between">
          <el-row>
            <el-button class="bbg-table-header-control" icon="Plus" @click="add" v-has="'sysMenu_save'">新增</el-button>
            <el-button class="bbg-table-header-control" icon="Magnet" v-has="'sysMenu_syncPermission'" @click="syncData">同步权限</el-button>
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
                size="small"
                border show-overflow-tooltip>
        <el-table-column prop="id" label="主键"  width="160"/>
        <el-table-column prop="parentId" label="父标题">
          <template #default="scope">
            <el-tooltip>
              <template #content>
                {{ scope.row.parentId }}
              </template>
              {{ scope.row.parentTitle }}
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="菜单标题"/>
        <el-table-column prop="name" label="菜单名称"/>
        <el-table-column prop="path" label="请求路径"/>
        <el-table-column prop="icon" label="图标" width="50">
          <template #default="scope">
            <el-icon>
              <component :is="scope.row.icon" />
            </el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="component" label="组件路径"/>
        <el-table-column prop="sort" label="排序" width="50"/>
        <el-table-column prop="type" label="类型" width="50">
          <template #default="scope">
            {{ scope.row.type === '1' ? '菜单' : '接口' }}
          </template>
        </el-table-column>
        <el-table-column prop="enable" label="显示" width="50">
          <template #default="scope">
            {{ scope.row.view ? '是' : '否' }}
          </template>
        </el-table-column>
        <el-table-column prop="enable" label="启用" width="50">
          <template #default="scope">
            {{scope.row.enable?'启动':'停用'}}
          </template>
        </el-table-column>
        <el-table-column prop="enable" label="租户权限" width="70">
          <template #default="scope">
            {{scope.row.tenantPermissions?'顶级':'所有'}}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="140"/>
        <el-table-column prop="updateTime" label="修改时间"  width="140"/>
        <el-table-column fixed="right" label="操作" v-if="has(['sysMenu_update','sysMenu_remove_id'])">
          <template #default="scope">
            <el-button link type="primary" size="small" v-has="'sysMenu_update'" @click="edit(scope.row)">编辑</el-button>
            <el-button link type="primary" size="small" v-has="'sysMenu_remove_id'" @click="remove(scope.row)">删除</el-button>
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

const header = ref(null);
const tableDynamicHeight = ref(0)

const syncData = async () => {
  const apiRet = await http.get(`/sysMenu/syncPermission`)
  if(apiRet && apiRet.ok){
    if(apiRet.data){
      ElMessage({type: 'success', message: '同步数据完成!'})
    }
  }
}

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
    const apiRet = await http.get(`/sysMenu/remove/${row.id}`)
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
      pageSize: 50,
    },
    queryEntity: {
      "expandProps":{}
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
    const apiRet = await http.post('/sysMenu/page', tableProps.reqParams)
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
