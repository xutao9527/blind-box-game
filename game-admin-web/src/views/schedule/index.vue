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
        <el-table-column prop="jobGroup" label="任务分组"/>
        <el-table-column prop="jobClassName" label="任务类详情"/>
        <el-table-column prop="cronExpression" label="Cron表达式"/>
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

const tableProps = reactive({
  apiRet: {
    data: [],
    totalRow: 0,
  },
  fetchData: async () => {
    const apiRet = await http.get('/schedule/list')
    if (apiRet.ok) {
      tableProps.apiRet = apiRet
    }
  },
});

</script>
