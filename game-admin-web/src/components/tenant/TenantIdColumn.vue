<template>
  <template v-if="isSuperTenant">
    <el-table-column prop="tenantId" label="所属租户" width="100">
      <template #default="scope">
        <el-tooltip>
          <template #content>
            {{ scope.row.tenantId }}
          </template>
          {{ getTenantName(scope.row.tenantId) }}
        </el-tooltip>
      </template>
    </el-table-column>
  </template>
</template>
<script setup>
import {useUserStore} from "@/store/userStore.js";

const store = useUserStore()
const isSuperTenant = ref(false)
const user = ref(null)
const tenantMap = ref(null)

const getTenantName = (tenantId) => {
  if(tenantId){
     return tenantMap.value[tenantId].tenantName
  }
  return ""
}

onMounted(async () => {
  user.value = await store.getUser
  isSuperTenant.value = user.value.superTenant
  tenantMap.value = user.value.tenantMap

})

</script>
