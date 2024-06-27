<template>
  <el-form-item label="租户" v-if="isSuperTenant">
    <el-select v-model="selectValue"
               placeholder="租户"
               @change="handleChange"
               default-first-option
               clearable
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
import {useUserStore} from "@/store/userStore.js";
import JSONbig from "json-bigint";

const emit = defineEmits(['update:value'])

const props = defineProps(
    {
      value: {
        type: Object,
      },
      includeTopTenant: {
        type: Boolean,
        default: false
      }
    }
)

watch(() => props.value, (newVal) => {
  if(newVal){
    selectValue.value = newVal.toString()
  }
})

const store = useUserStore()
const isSuperTenant = ref(false)
const user = ref(null)
const tenants = ref([])
const selectValue = ref(props.value)

onMounted(async () => {
  user.value = await store.getUser
  isSuperTenant.value =  user.value.superTenant
  if (isSuperTenant.value && user.value.tenantMap) {
    if(props.includeTopTenant){
      tenants.value = Object.values(user.value.tenantMap).sort((a, b) => a.id - b.id)
    }else{
      tenants.value = Object.values(user.value.tenantMap).filter(t=>t.parentId!=null).sort((a, b) => a.id - b.id)
    }
  }
})

const handleChange = (value) =>{
    if(value){
      emit('update:value', JSONbig.parse(value))
    }else{
      emit('update:value', value)
    }
}
</script>
