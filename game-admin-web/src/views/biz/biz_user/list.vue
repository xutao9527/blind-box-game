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
              <el-input v-model="tableProps.reqParams.queryEntity.mobile" placeholder="手机号"/>
            </div>
            <div class="bbg-table-header-input">
              <bbg-dict-select v-model:value="tableProps.reqParams.queryEntity.type" ref="userTypeRef" :tag="'user_type'" placeholder="用户类型"/>
            </div>
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
            <el-button class="bbg-table-header-control" icon="DocumentAdd" @click="addVirtualUser">生成假人</el-button>
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
                  border show-overflow-tooltip>
        <el-table-column prop="id" label="主键"/>
        <el-table-column prop="nickName" label="昵称"/>
        <el-table-column prop="mobile" label="手机号" width="120"/>
        <el-table-column prop="account" label="登录账号"/>
        <el-table-column prop="password" label="密码"/>
        <el-table-column prop="type" label="类型">
          <template #default="scope">
            {{userTypeRef.getLabel(scope.row.type)}}
          </template>
        </el-table-column>
        <el-table-column prop="money" label="金额"/>
<!--        <el-table-column prop="moneySign" label="金额校验"/>-->
<!--        <el-table-column prop="photo" label="头像"/>-->
<!--        <el-table-column prop="email" label="邮箱"/>-->
<!--        <el-table-column prop="name" label="真实姓名"/>-->
<!--        <el-table-column prop="idCard" label="身份证"/>-->
<!--        <el-table-column prop="birthday" label="生日"/>-->
        <el-table-column prop="enable" label="状态">
        <template #default="scope">
            {{scope.row.enable?'启动':'停用'}}
        </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间"/>
        <el-table-column prop="updateTime" label="修改时间"/>
        <el-table-column fixed="right" label="操作" width="160">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="viewSmsCode(scope.row)">验证码</el-button>
            <el-button link type="primary" size="small" @click="openUpdateBizUserMoney(scope.row)">充值</el-button>
            <el-button link type="primary" size="small" @click="edit(scope.row)">详情</el-button>
<!--            <el-button link type="primary" size="small" @click="remove(scope.row)">删除</el-button>-->
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

const userTypeRef = ref(null)
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
  const apiRet = await http.get(`/bizUser/remove/${row.id}`)
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
    const apiRet = await http.post('/bizUser/page', tableProps.reqParams)
    if (apiRet.ok) {
      tableProps.apiRet = apiRet
      tableProps.apiRet.totalRow = apiRet.data.totalRow
    }
  }
});

const addVirtualUser = () => {
  ElMessageBox.prompt('请输入虚拟用户数量', '批量添加假人', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    inputPattern:
        /^(?:[1-9]|[1-9][0-9]|[1-9][0-9]{2}|1000)$/,
    inputErrorMessage: '1~1000数量限制',
  })
      .then(async ({value}) => {
        const apiRet = await http.get(`/bizUser/addVirtualUser?count=${value}`)
        if (apiRet.ok) {
          ElMessage({
            type: 'success',
            message: `生成完成!`,
          })
          await tableProps.fetchData()
        }
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: 'Input canceled',
        })
      })
}

const openUpdateBizUserMoney = (row) => {
  ElMessageBox.prompt('请输入充值金额', '充值', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    inputPattern:
        /^-?\d+(\.\d{1,2})?$/,
    inputErrorMessage: 'Invalid Money',
  })
      .then(async ({value}) => {
        const apiRet = await http.get(`/bizUser/updateBizUserMoney/${row.id}/${value}`)
        if (apiRet.ok) {
          ElMessage({
            type: 'success',
            message: `充值成功!`,
          })
          await tableProps.fetchData()
        }
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: 'Input canceled',
        })
      })
}

const viewSmsCode = async (row) => {
  const apiRet = await http.get(`/bizUser/viewCode/${row.id}`)
  if (apiRet.ok) {
    await ElMessageBox.alert(`code is ${apiRet.data}`, 'sms code', {
      confirmButtonText: 'OK',
      type: 'warning'
    })
  }else{
    ElMessage({
      type: 'error',
      message: apiRet.msg,
    })
  }

}

const emits = defineEmits(['activeRightTabs']);
defineExpose({
  tableProps
})
</script>
