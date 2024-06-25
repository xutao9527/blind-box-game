<template>
  <el-select v-model="selectValue"
             placeholder=""
             @change="handleChange"
             clearable
             filterable>
    <el-option v-for="tenant in tenants"
               :label="tenant.tenantName"
               :value="tenant.id.toString()">
      <span style="float: left">{{ tenant.tenantName }}</span>
    </el-option>
  </el-select>
</template>
<script setup>
import {http} from "@/core/axios/index.js";

const emit = defineEmits(['update:value'])
const props = defineProps(
    {
      value: {
        type: String,
      }
    }
)
const tenants = ref([])
const selectValue = ref(props.value)

watch(() => props.value, (newVal) => {
  selectValue.value = newVal
})

onMounted(() => {
  fetchData()
})

const fetchData = async () => {
  let apiRet;
    apiRet = await http.get('sysTenant/getSelectTenants')
  if (apiRet.ok) {
    tenants.value = apiRet.data
  }
}

const handleChange = (value) => {
  emit('update:value', value)
}
</script>
