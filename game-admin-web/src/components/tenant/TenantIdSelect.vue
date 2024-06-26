<template>
  <el-select v-model="selectValue"
             placeholder="租户"
             @change="handleChange"
             clearable
             default-first-option
             filterable>
    <el-option v-for="tenant in tenants"
               :label="tenant.tenantName"
               :value="tenant.id.toString()">
      <span style="float: left">{{ tenant.tenantName }}</span>
    </el-option>
  </el-select>
</template>
<script setup>
import TenantUtil from "@/core/tenant/index.js";

const emit = defineEmits(['update:value'])
const props = defineProps(
    {
      value: {
        type: String,
      },
      includeTopTenant: {
        type: Boolean,
        default: false
      }
    }
)

const tenants = ref([])
const selectValue = ref(props.value)

onMounted(() => {
  console.log("TenantIdSelect s")
  tenants.value = TenantUtil.getTenants(props.includeTopTenant)
  console.log("TenantIdSelect e",tenants.value )
})

const handleChange = (value) => {
  emit('update:value', value)
}
</script>
