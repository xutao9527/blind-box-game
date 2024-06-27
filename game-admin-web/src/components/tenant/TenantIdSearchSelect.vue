<template>
  <div class="bbg-table-header-input" v-if="isSuperTenant">
    <el-select v-model="selectValue"
               placeholder="租户"
               @change="handleChange"
               default-first-option
               clearable
               :disabled="isDisabled"
               filterable>
      <el-option v-for="tenant in tenants"
                 :label="tenant.tenantName"
                 :value="tenant.id.toString()">
        <span style="float: left">{{ tenant.tenantName }}</span>
      </el-option>
    </el-select>
  </div>
</template>
<script setup>
import {useUserStore} from "@/store/userStore.js";

const emit = defineEmits(['update:value'])
const props = defineProps(
    {
      value: {
        type: String,
      },
      disabled: {
        type: Boolean,
        default: false
      }
    }
)

const store = useUserStore()
const isSuperTenant = ref(false)
const user = ref(null)
const tenants = ref([])

const selectValue = ref(props.value)
const isDisabled = ref(props.disabled);

watchEffect(() => {
  selectValue.value = props.value;
  isDisabled.value = props.disabled;
});




onMounted(async () => {
  user.value = await store.getUser
  isSuperTenant.value = user.value.superTenant
  if (isSuperTenant.value && user.value.tenantMap) {
    // tenants.value = Object.values(user.value.tenantMap).filter(t => t.parentId != null).sort((a, b) => a.id - b.id)
    tenants.value = Object.values(user.value.tenantMap).sort((a, b) => a.id - b.id)
  }
})

const handleChange = (value) => {
  emit('update:value', value)
}
</script>
