<template>
  <el-select v-model="selectValue"
             placeholder=""
             @change="handleChange"
             clearable
             filterable>
    <el-option v-for="menu in menus"
               :label="menu.title"
               :value="menu.id.toString()">
      <span style="float: left">{{ menu.title }}</span>
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
const menus = ref([])
const selectValue = ref(props.value)

watch(() => props.value, (newVal) => {
  selectValue.value = newVal
})

onMounted(() => {
  fetchData()
})

const fetchData = async () => {
  let apiRet;
    apiRet = await http.get('sysMenu/getSelectMenus')
  if (apiRet.ok) {
    menus.value = apiRet.data
  }
}

const handleChange = (value) => {
  emit('update:value', value)
}
</script>
