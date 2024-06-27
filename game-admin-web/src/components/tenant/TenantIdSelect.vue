<template>
  <el-form-item label="租户" v-if="isSuperTenant">
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
  </el-form-item>
</template>
<script setup>
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

const isSuperTenant = computed(() => user.value.superTenant)
const user = ref(null)
const tenants = ref([])
const selectValue = ref(props.value)

onMounted(async () => {
  user.value = await store.getUser
  tenants.value = user.value.tenantMap
})

const handleChange = (value) => {
  emit('update:value', value)
}
</script>
