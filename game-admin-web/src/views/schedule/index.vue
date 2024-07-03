<template>
  <el-container class="bbg-table">
    <el-header ref="header" class="bbg-table-header" height="auto">
      <el-row :gutter="10">
        <el-col :span="18">
          <el-row>
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
                size="small"
                :data="tableProps.apiRet.data"
                :height="tableDynamicHeight"
                table-layout="auto"
                border show-overflow-tooltip>
        <el-table-column prop="jobName" label="任务名称"/>
        <el-table-column
            prop="jobGroup"
            label="任务分组"
            :filters="jobGroupFilter"
            :filter-method="(value, row, column) => row.jobGroup === value"
        />
        <template v-if="isSuperTenant">
          <el-table-column
              prop="tenantId"
              label="所属租户"
              width="100"
              :filters="tenantNameFilter"
              :filter-method="(value, row, column) => row.tenantName === value || !row.tenantName"
          >
            <template #default="scope">
              <el-tooltip>
                <template #content>
                  {{ scope.row.tenantId }}
                </template>
                {{ scope.row.tenantName }}
              </el-tooltip>
            </template>
          </el-table-column>
        </template>
        <el-table-column prop="jobClassName" label="任务类详情"/>
        <el-table-column prop="triggerType" label="触发器"/>
        <el-table-column prop="triggerType" label="执行频率">
          <template #default="scope">
            {{ scope.row.triggerType === 'SimpleTrigger' ? scope.row.repeatInterval + '/毫秒' : scope.row.cronExpression }}
          </template>
        </el-table-column>
        <el-table-column prop="nextExecuteTime" label="下次执行时间"/>
      </el-table>
    </el-main>
    <el-footer class="bbg-table-footer">
      <el-row style="padding: 5px">
        <el-col style="display: grid;justify-content: right;">
          <el-pagination style="margin-top: 5px;margin-right: 10px"
                         layout="total"
                         :total="tableProps.apiRet.data.length"
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
import TenantIdColumn from "@/components/tenant/TenantIdColumn.vue";
import {useUserStore} from "@/store/userStore.js";

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

const jobGroupFilter = ref([])
const tenantNameFilter = ref([])
const store = useUserStore()
const isSuperTenant = ref(false)

onMounted( async () => {
  isSuperTenant.value = (await store.getUser).superTenant
  await tableProps.fetchData()
})

const tableProps = reactive({
  apiRet: {
    data: [],
    totalRow: 0,
  },
  fetchData: async () => {
    const apiRet = await http.get('/schedule/list')
    if (apiRet.ok) {
      jobGroupFilter.value = apiRet.data.map(obj => {
        return {
          text: obj.jobGroup,
          value: obj.jobGroup
        }
      }).reduce((acc, cur) => {
        if (acc.findIndex(obj => obj.value === cur.value) === -1) {
          acc.push(cur)
        }
        return acc
      }, [])
      tenantNameFilter.value = apiRet.data.filter(obj=> obj.tenantName).map(obj => {
        return {
          text: obj.tenantName,
          value: obj.tenantName
        }
      }).reduce((acc, cur) => {
        if (acc.findIndex(obj => obj.value === cur.value) === -1) {
          acc.push(cur)
        }
        return acc
      }, [])
      tableProps.apiRet = apiRet
    }
  },
});

</script>
